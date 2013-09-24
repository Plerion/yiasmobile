package ch.cromon.YiasMobile.UI.graphics;

import android.opengl.GLES20;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */
public enum InputLayout {
	Triangles(GLES20.GL_TRIANGLES),
	TriangleStrip(GLES20.GL_TRIANGLE_STRIP),
	TriangleFan(GLES20.GL_TRIANGLE_FAN);

	private final int mGlValue;

	InputLayout(int value) {
		mGlValue = value;
	}

	public int getGLValue() {
		return mGlValue;
	}
}
