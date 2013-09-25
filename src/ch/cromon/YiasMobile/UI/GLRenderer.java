package ch.cromon.YiasMobile.UI;

import android.opengl.GLSurfaceView;
import ch.cromon.YiasMobile.scene.WorldFrame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class GLRenderer implements GLSurfaceView.Renderer {
	private int mWidth, mHeight;

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		mWidth = width;
		mHeight = height;

		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0, 0, 0, 0);

		mWidth = UIManager.getInstance().getWidth();
		mHeight = UIManager.getInstance().getHeight();

		UIManager.getInstance().onDeviceReady();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_DEPTH_BUFFER_BIT | GL10.GL_COLOR_BUFFER_BIT);

		WorldFrame.getInstance().onFrame();
	}
}
