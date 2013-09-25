package ch.cromon.YiasMobile.UI.graphics;

import android.opengl.GLES20;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 19:00
 */
public enum BlendMode {
	Alpha (GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA),
	Color (GLES20.GL_ONE, GLES20.GL_ONE);

	final int srcBlend;
	final int dstBlend;

	BlendMode(int srcBlend, int dstBlend) {
		this.srcBlend = srcBlend;
		this.dstBlend = dstBlend;
	}

	public int getSrcBlend() {
		return srcBlend;
	}

	public int getDestBlend() {
		return dstBlend;
	}
}
