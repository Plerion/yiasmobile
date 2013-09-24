package ch.cromon.YiasMobile.UI;

import android.app.Activity;
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

	}

	public GLSurfaceView getView() {
		return mSurfaceView;
	}

	private GLSurfaceView mSurfaceView;
}
