package ch.cromon.YiasMobile.UI;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public class GLSurface {
	public GLSurface(Activity context) {
		mSurfaceView = new GLSurfaceView(context);
	}

	public void initRendering() {
		int[] params = new int[1];

		GLES20.glGetIntegerv(GLES20.GL_NUM_COMPRESSED_TEXTURE_FORMATS, params, 0);

		params = new int[params[0]];

		GLES20.glGetIntegerv(GLES20.GL_COMPRESSED_TEXTURE_FORMATS, params, 0);

		boolean dxt1 = false, dxt3 = false, dxt5 = false;

		for(int i = 0; i < params.length; ++i) {
			if(params[i] == GL_COMPRESSED_RGB_S3TC_DXT1_EXT) {
				dxt1 = true;
			} else if(params[i] == GL_COMPRESSED_RGBA_S3TC_DXT3_EXT) {
				dxt3 = true;
			} else if(params[i] == GL_COMPRESSED_RGBA_S3TC_DXT5_EXT) {
				dxt5 = true;
			}
		}

		if(dxt1 == false || dxt3 == false || dxt5 == false) {
			throw new IllegalStateException("Your device does not support S3 texture compression. You have to wait for a further update of the software " +
											"that will support software decompression of those formats.");
		}
	}

	public GLSurfaceView getView() {
		return mSurfaceView;
	}

	private GLSurfaceView mSurfaceView;

	public static final int GL_COMPRESSED_RGB_S3TC_DXT1_EXT = 0x83F1;
	public static final int GL_COMPRESSED_RGBA_S3TC_DXT3_EXT = 0x83F2;
	public static final int GL_COMPRESSED_RGBA_S3TC_DXT5_EXT = 0x83F3;
}
