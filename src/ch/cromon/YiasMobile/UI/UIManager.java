package ch.cromon.YiasMobile.UI;

import android.os.Bundle;
import ch.cromon.YiasMobile.UI.graphics.ProgramCollection;
import ch.cromon.YiasMobile.scene.WorldFrame;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */
public class UIManager {
    private static UIManager gInstance = new UIManager();

	public static UIManager getInstance() {
		return gInstance;
	}

    private boolean mIsLoaded = false;
	private GLSurface mSurface;
	private GLRenderer mRenderer;

    public UIManager() {

    }

	public GLRenderer getDevice() {
		return mRenderer;
	}

    public void onMainViewCreated(Bundle savedInstance) {
		MainWindow wnd = MainWindow.getInstance();

		mSurface = new GLSurface(MainWindow.getInstance());
		mRenderer = new GLRenderer();

		mSurface.getView().setEGLContextClientVersion(2);
		mSurface.getView().setRenderer(mRenderer);

		wnd.setContentView(mSurface.getView());

		if(mIsLoaded == false) {
			this.onStartup(savedInstance);
		}
    }

	public int getWidth() {
		return mSurface.getView().getWidth();
	}

	public int getHeight() {
		return mSurface.getView().getHeight();
	}

	public void onDeviceReady() {
		mSurface.initRendering();

		ProgramCollection.getInstance().init();

		WorldFrame.getInstance().init();
	}

	public GLSurface getSurface() {
		return mSurface;
	}

    private void onStartup(Bundle savedInstance) {
		mIsLoaded = true;


	}
}
