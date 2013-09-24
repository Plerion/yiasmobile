package ch.cromon.YiasMobile.scene;

import ch.cromon.YiasMobile.UI.UIManager;
import ch.cromon.YiasMobile.math.Matrix;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 00:14
 * To change this template use File | Settings | File Templates.
 */
public class OrthoCamera extends Camera {
	public OrthoCamera() {
		mProjection = Matrix.Ortho(UIManager.getInstance().getDevice().getWidth(), UIManager.getInstance().getDevice().getHeight());

		projChanged();
	}

	public void updateMatrix() {
		mProjection = Matrix.Ortho(UIManager.getInstance().getDevice().getWidth(), UIManager.getInstance().getDevice().getHeight());

		projChanged();
	}
}
