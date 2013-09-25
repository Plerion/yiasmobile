package ch.cromon.YiasMobile.models.adt;

import ch.cromon.YiasMobile.io.ByteArrayRandomAccessStream;
import ch.cromon.YiasMobile.io.IffChunk;
import ch.cromon.YiasMobile.io.RandomAccessStream;
import ch.cromon.YiasMobile.io.StreamUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 22:49
 */
public class MapChunk {
	private MapArea mParent;
	private MCIN mChunkInfo;
	private MCNK mHeader = new MCNK();
	private HashMap<Integer, IffChunk> mIffChunks = new HashMap<Integer, IffChunk>();
	private ByteBuffer mVertexData = ByteBuffer.allocate(145 * (12 + 8)).order(ByteOrder.nativeOrder());

	public MapChunk(MapArea parent) {
		mParent = parent;
	}

	public void asyncLoad(RandomAccessStream stream, MCIN chunkInfo) throws IOException {
	 	mChunkInfo = chunkInfo;

		stream.seek(chunkInfo.mcnk);
		byte[] chunkData = new byte[chunkInfo.size];
		stream.readFully(chunkData);

		ByteArrayRandomAccessStream strm = new ByteArrayRandomAccessStream(chunkData);
		strm.seek(8);

	   	loadHeader(strm);

		if(mHeader.ofsHeight != 0) {
			strm.seek(mHeader.ofsHeight);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsNormal != 0) {
			strm.seek(mHeader.ofsNormal);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsLayer != 0) {
			strm.seek(mHeader.ofsLayer);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsRefs != 0) {
			strm.seek(mHeader.ofsRefs);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsAlpha != 0) {
			strm.seek(mHeader.ofsAlpha);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsShadow != 0) {
			strm.seek(mHeader.ofsShadow);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsSndEmitters != 0) {
			strm.seek(mHeader.ofsSndEmitters);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsLiquid != 0) {
			strm.seek(mHeader.ofsLiquid);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsMCCV != 0) {
			strm.seek(mHeader.ofsMCCV);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		if(mHeader.ofsMCLV != 0) {
			strm.seek(mHeader.ofsMCLV);
			IffChunk cnk = StreamUtils.readChunk(strm);
			mIffChunks.put(cnk.Signature, cnk);
		}

		loadVertices();
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

	private void loadVertices() throws IOException {
	 	IffChunk mcvt = getChunk("MCVT");
		ByteArrayRandomAccessStream strm = new ByteArrayRandomAccessStream(mcvt.data);

		int counter = 0;

		for(int i = 0; i < 17; ++i) {
			for(int j = 0; j < (((i % 2) != 0) ? 8 : 9); ++j) {
				float x = mHeader.indexX * Metrics.ChunkSize + j * Metrics.UnitSize;
				float y = mHeader.indexY * Metrics.ChunkSize + i * 0.5f * Metrics.UnitSize;
				float z = mHeader.position.z + strm.readFloat();

				mVertexData.putFloat(counter * (12 + 8), x);
				mVertexData.putFloat(counter * (12 + 8) + 4, y);
				mVertexData.putFloat(counter * (12 + 8) + 8, z);

				++counter;
			}
		}
	}

	private void loadHeader(ByteArrayRandomAccessStream strm) throws IOException {
	  	mHeader.flags = strm.readInt();
		mHeader.indexX = strm.readInt();
		mHeader.indexY = strm.readInt();
		mHeader.nLayers = strm.readInt();
		mHeader.nDoodadRefs = strm.readInt();
		mHeader.ofsHeight = strm.readInt();
		mHeader.ofsNormal = strm.readInt();
		mHeader.ofsLayer = strm.readInt();
		mHeader.ofsRefs = strm.readInt();
		mHeader.ofsAlpha = strm.readInt();
		mHeader.sizeAlpha = strm.readInt();
		mHeader.ofsShadow = strm.readInt();
		mHeader.sizeShadow = strm.readInt();
		mHeader.areaId = strm.readInt();
		mHeader.nMapObjRefs = strm.readInt();
		mHeader.holes = strm.readInt();
		mHeader.lowQuality1 = strm.readLong();
		mHeader.lowQuality2 = strm.readLong();
		mHeader.predTex = strm.readInt();
		mHeader.noEffectDoodad = strm.readInt();
		mHeader.ofsSndEmitters = strm.readInt();
		mHeader.nSndEmitters = strm.readInt();
		mHeader.ofsLiquid = strm.readInt();
		mHeader.sizeLiquid = strm.readInt();
		mHeader.position.x = strm.readFloat();
		mHeader.position.y = strm.readFloat();
		mHeader.position.z = strm.readFloat();
		mHeader.ofsMCCV = strm.readInt();
		mHeader.ofsMCLV = strm.readInt();
		mHeader.unused = strm.readInt();
	}
}
