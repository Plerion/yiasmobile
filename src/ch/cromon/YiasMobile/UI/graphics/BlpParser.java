package ch.cromon.YiasMobile.UI.graphics;

import ch.cromon.YiasMobile.io.RandomAccessStream;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 13:32
 */
public class BlpParser {
	RandomAccessStream mInput;

	public class BlpHeader
	{
		public int magic;
		public int formatVersion;
		public byte colorEncoding;
		public byte alphaSize;
		public byte preferredFormat;
		public byte hasMips;
		public int width;
		public int height;
		public int[] mipOffsets = new int[16];
		public int[] mipSizes = new int[16];
	}

	public class LayerInfo
	{
		public int Width;
		public int Height;
		public byte[] data;
	}

	BlpHeader mHeader = new BlpHeader();
	PixelFormat mFormat;
	int mLayerCount = 0;
	ArrayList<LayerInfo> mLayers = new ArrayList<LayerInfo>();

	final byte[] alphaHalfData = { 0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte)0x88, (byte)0x99, (byte)0xAA, (byte)0xBB, (byte)0xCC, (byte)0xDD, (byte)0xEE, (byte)0xFF };

	public BlpParser(RandomAccessStream stream) throws IOException {
		mInput = stream;

		parseFile();
	}

	public PixelFormat getFormat() {
		return mFormat;
	}

	public int getLayerCount() {
		return mLayerCount;
	}

	public LayerInfo getLayer(int index) {
		return mLayers.get(index);
	}

	private void parseFile() throws IOException {
		mHeader.magic = mInput.readInt();
		mHeader.formatVersion = mInput.readInt();
		mHeader.colorEncoding = mInput.readByte();
		mHeader.alphaSize = mInput.readByte();
		mHeader.preferredFormat = mInput.readByte();
		mHeader.hasMips = mInput.readByte();
		mHeader.width = mInput.readInt();
		mHeader.height = mInput.readInt();

		for(int i = 0; i < 16; ++i) {
			mHeader.mipOffsets[i] = mInput.readInt();
		}

		for(int i = 0; i < 16; ++i) {
			mHeader.mipSizes[i] = mInput.readInt();
		}

		mFormat = PixelFormat.formats[mHeader.preferredFormat];

		for(int i = 0; i < 16; ++i) {
			if(mHeader.mipSizes[i] == 0 || mHeader.mipOffsets[i] == 0) {
				break;
			}

			++mLayerCount;
		}

		if(mHeader.colorEncoding == 1) {
			loadFilePalette();
		} else {
			loadFileDXT();
		}
	}

	private void loadFileDXT() throws IOException {
		for(int i = 0; i < mLayerCount; ++i) {
			mInput.seek(mHeader.mipOffsets[i]);

			byte[] data = new byte[mHeader.mipSizes[i]];
			mInput.readFully(data);

			LayerInfo layer = new LayerInfo();
			layer.data = data;
			layer.Width = Math.max(1, mHeader.width >> i);
			layer.Height = Math.max(1, mHeader.height >> i);
			mLayers.add(layer);
		}
	}

	private void loadFilePalette() throws IOException {
		int[] colors = new int[256];
		for(int i = 0; i < 256; ++i) {
			colors[i] = mInput.readInt();
		}

		for(int i = 0; i < mLayerCount; ++i) {
			mInput.seek(mHeader.mipOffsets[i]);

			byte[] data = new byte[mHeader.mipSizes[i]];
			mInput.readFully(data);

			LayerInfo layer = new LayerInfo();
			layer.Width = Math.max(1, mHeader.width >> i);
			layer.Height = Math.max(1, mHeader.height >> i);

			byte[] outData = new byte[layer.Width * layer.Height * 4];
			if(mHeader.alphaSize == 8) {
				decompAlphaFastPath(colors, data, outData);
			} else {
				decompAlphaARGB8888(colors, data, outData);
			}

			layer.data = outData;
			mLayers.add(layer);
		}
	}

	private void decompAlphaFastPath(int[] palette, byte[] input, byte[] output) {
		int numPixels = output.length / 4;
		for(int i = 0; i < numPixels; ++i) {
			byte index = input[i];
			byte alpha = input[i + numPixels];
			int color = palette[index];

			output[i * 4] = (byte)(color & 0xFF);
			output[i * 4 + 1] = (byte)((color >> 4) & 0xFF);
			output[i * 4 + 2] = (byte)((color >> 8) & 0xFF);
			output[i * 4 + 3] = alpha;
		}
	}

	private void decompAlphaARGB8888(int[] palette, byte[] input, byte[] output) {
		switch(mHeader.alphaSize) {
			case 1: {
				int numPixels = output.length / 4;
				for(int i = 0; i < numPixels; ++i) {
					byte index = input[i];
					int color = palette[index];

					output[i * 4] = (byte)(color & 0xFF);
					output[i * 4 + 1] = (byte)((color >> 4) & 0xFF);
					output[i * 4 + 2] = (byte)((color >> 8) & 0xFF);
				}

				for(int i = 0; i < (numPixels / 8); ++i) {
					byte alpha = input[numPixels + i];
					for(int j = 0; j < 8; ++j) {
						byte realAlpha = (((alpha >> j) & 1) != 0) ? (byte)0xFF : (byte)0x00;
						output[(i * 8 + j) * 4 + 3] = realAlpha;
					}
				}
			}
			break;

			case 4: {
				int numPixels = output.length / 4;
				for(int i = 0; i < numPixels; ++i) {
					byte index = input[i];
					int color = palette[index];

					output[i * 4] = (byte)(color & 0xFF);
					output[i * 4 + 1] = (byte)((color >> 4) & 0xFF);
					output[i * 4 + 2] = (byte)((color >> 8) & 0xFF);
				}

				for(int i = 0; i < (numPixels / 2); ++i) {
					byte alpha = input[numPixels + i];
					byte alpha1 = (byte)(alpha & 0xF);
					byte alpha2 = (byte)(alpha >> 4);

					output[i * 2 * 4 + 3] = alpha1;
					output[(i * 2 + 1) * 4 + 3] = alpha2;
				}
			}

			default:
				throw new IllegalArgumentException("Invalid texture type (alphaSize wrong)");
		}
	}
}
