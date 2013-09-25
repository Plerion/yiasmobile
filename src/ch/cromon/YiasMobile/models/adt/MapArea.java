package ch.cromon.YiasMobile.models.adt;

import ch.cromon.YiasMobile.io.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 22:48
 */
public class MapArea {
	private String mFileName;
	private int mIndexX;
	private int mIndexY;

	private ArrayList<MCIN> mChunkOffsets = new ArrayList<MCIN>();
	private List<String> mTextures = new ArrayList<String>();
	private ArrayList<MapChunk> mChunks = new ArrayList<MapChunk>();

	private HashMap<Integer, IffChunk> mIffChunks = new HashMap<Integer, IffChunk>();

	public MapArea(String fileName, int indexX, int indexY) {
	 	mFileName = fileName;
		mIndexX = indexX;
		mIndexY = indexY;
	}

	public void asyncLoad() throws IOException {
		RandomAccessStream input = ResourceManager.getInstance().getFile(mFileName);

		while(true) {
			try {
				IffChunk chunk = StreamUtils.readChunk(input);
				mIffChunks.put(chunk.Signature, chunk);
			} catch (IOException e) {
				break;
			}
		}

		loadMCIN();
		loadMTEX();

		for(int i = 0; i < 256; ++i) {
			MCIN mcin = mChunkOffsets.get(i);
			MapChunk mapChunk = new MapChunk(this);

			mapChunk.asyncLoad(input, mcin);
			mChunks.add(mapChunk);
		}
	}

	private IffChunk getChunk(String signature) {
		int sig = 0;
		for(int i = 0; i < signature.length(); ++i) {
			sig |= signature.codePointAt(i) << ((3 - i) * 8);
		}

		if(mIffChunks.containsKey(sig) == false) {
			throw new IllegalArgumentException("Missing chunk: " + signature);
		}

		return mIffChunks.get(sig);
	}

	private void loadMCIN() throws IOException {
		IffChunk cnk = getChunk("MCIN");
		ByteArrayRandomAccessStream strm = new ByteArrayRandomAccessStream(cnk.data);

		for(int i = 0; i < 256; ++i) {
			MCIN mcin = new MCIN();
			mcin.mcnk = strm.readInt();
			mcin.size = strm.readInt();
			mcin.flags = strm.readInt();
			mcin.pad = strm.readInt();

			mChunkOffsets.add(mcin);
		}
	}

	private void loadMTEX() {
		IffChunk cnk = getChunk("MTEX");

		String str = new String(cnk.data);
		String[] strings = str.split("\0");

		mTextures = Arrays.asList(strings);
	}
}
