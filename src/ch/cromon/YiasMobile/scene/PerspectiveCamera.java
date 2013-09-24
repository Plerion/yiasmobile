package ch.cromon.YiasMobile.scene;

import ch.cromon.YiasMobile.UI.UIManager;
import ch.cromon.YiasMobile.math.Matrix;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public class PerspectiveCamera extends Camera {
	private float mFov;
	private float zNear;
	private float zFar;

	public PerspectiveCamera() {
	 	mFov = 55.0f;
		zNear = 2.0f;
		zFar = 600.0f;

		mProjection = Matrix.Perspective(mFov, UIManager.getInstance().getDevice().getWidth(), UIManager.getInstance().getDevice().getHeight(), zNear, zFar);
	}

	public void setFarClip(float clip) {
		zFar = clip;
		mProjection = Matrix.Perspective(mFov, UIManager.getInstance().getDevice().getWidth(), UIManager.getInstance().getDevice().getHeight(), zNear, zFar);
		projChanged();
	}

	public void setNearClip(float clip) {
		zNear = clip;
		mProjection = Matrix.Perspective(mFov, UIManager.getInstance().getDevice().getWidth(), UIManager.getInstance().getDevice().getHeight(), zNear, zFar);
		projChanged();
	}

	public void setClip(float nearClip, float farClip) {
		zNear = nearClip;
		zFar = farClip;
		mProjection = Matrix.Perspective(mFov, UIManager.getInstance().getDevice().getWidth(), UIManager.getInstance().getDevice().getHeight(), zNear, zFar);
		projChanged();
	}

	public void setFieldOfView(float fov) {
		mFov = fov;
		mProjection = Matrix.Perspective(mFov, UIManager.getInstance().getDevice().getWidth(), UIManager.getInstance().getDevice().getHeight(), zNear, zFar);
		projChanged();
	}
}
