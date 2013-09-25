package ch.cromon.YiasMobile.UI.graphics;

import android.opengl.GLES20;
import ch.cromon.YiasMobile.UI.GLSurface;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 13:26
 */
public class Texture {
	private int mTexture;

	Texture(boolean setToDefault) {
		if(setToDefault == true) {
			mTexture = TextureManager.defaultTexture.getID();
		}
	}

	Texture() {
		mTexture = TextureManager.defaultTexture.getID();
	}

	public int getID() {
		return mTexture;
	}

	public void loadMemoryARGB(byte[] data, int width, int height) {
		int[] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);
		mTexture = textures[0];

		fillTexParameters();

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ByteBuffer.wrap(data));
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}

	public void updateMemoryARGB(byte[] data, int width, int height) {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ByteBuffer.wrap(data));
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}

	public void loadFromBlp(BlpParser parser) {
		int[] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);
		mTexture = textures[0];

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture);

		fillTexParameters();

		int format;
		int blockSize = 0;

		switch(parser.getFormat()) {
			case DXT1:
				format = GLSurface.GL_COMPRESSED_RGB_S3TC_DXT1_EXT;
				blockSize = 8;
				break;

			case DXT3:
				format = GLSurface.GL_COMPRESSED_RGBA_S3TC_DXT3_EXT;
				blockSize = 16;
				break;

			case DXT5:
				format = GLSurface.GL_COMPRESSED_RGBA_S3TC_DXT5_EXT;
				blockSize = 16;
				break;

			default:
				format = -1;
		}

		if(format < 0) {
			throw new IllegalArgumentException("Unable to parse texture");
		}

		for(int i = 0; i < parser.getLayerCount(); ++i) {
			BlpParser.LayerInfo layer = parser.getLayer(i);
			int layerSize = ((layer.Width + 3) / 4) * ((layer.Height + 3) / 4) * blockSize;
			GLES20.glCompressedTexImage2D(GLES20.GL_TEXTURE_2D, i, format, layer.Width, layer.Height, 0, layerSize, ByteBuffer.wrap(layer.data));
		}

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}

	public void bind() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture);
	}

	public void unbind() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}

	private void fillTexParameters() {
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);//GLES20.GL_LINEAR_MIPMAP_LINEAR);
		//GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		//GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
	}
}
