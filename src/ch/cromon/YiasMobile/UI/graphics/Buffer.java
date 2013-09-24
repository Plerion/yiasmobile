package ch.cromon.YiasMobile.UI.graphics;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public class Buffer {
	int mBuffer;
	boolean mIsIndex = false;
	int mTarget = GLES20.GL_ARRAY_BUFFER;

	public Buffer(boolean index) {
		mIsIndex = index;
		if(index == true) {
			mTarget = GLES20.GL_ELEMENT_ARRAY_BUFFER;
		}

		int[] buffers = new int[1];

		GLES20.glGenBuffers(1, buffers, 0);

		mBuffer = buffers[0];
	}

	public Buffer() {
		mIsIndex = false;

		int[] buffers = new int[1];

		GLES20.glGenBuffers(1, buffers, 0);

		mBuffer = buffers[0];
	}

	public void bind() {
		GLES20.glBindBuffer(mTarget, mBuffer);
	}

	public void unbind() {
		GLES20.glBindBuffer(mTarget, 0);
	}

	public void setData(IntBuffer data) {
		GLES20.glBindBuffer(mTarget, mBuffer);
		GLES20.glBufferData(mTarget, data.limit() * 4, data, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(mTarget, 0);
	}

	public void setData(FloatBuffer data) {
		GLES20.glBindBuffer(mTarget, mBuffer);
		GLES20.glBufferData(mTarget, data.limit() * 4, data, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(mTarget, 0);
	}
}
