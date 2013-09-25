package ch.cromon.YiasMobile.scene;

import android.opengl.GLSurfaceView;
import ch.cromon.YiasMobile.UI.UIManager;
import ch.cromon.YiasMobile.UI.elements.Button;
import ch.cromon.YiasMobile.UI.elements.Frame;
import ch.cromon.YiasMobile.UI.elements.UIQuad;
import ch.cromon.YiasMobile.UI.graphics.*;
import ch.cromon.YiasMobile.io.InputManager;
import ch.cromon.YiasMobile.math.Matrix;
import ch.cromon.YiasMobile.math.Vector2;
import ch.cromon.YiasMobile.models.adt.MapArea;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class WorldFrame implements Camera.MatrixChangedEventListener {
	private static WorldFrame gInstance = new WorldFrame();

	public static WorldFrame getInstance() {
		return gInstance;
	}

	private Camera mActiveCamera = null;
	private PerspectiveCamera mPerspCamera = null;
	private OrthoCamera mOrthoCamera = null;

	private ArrayList<ProgramUpdateHolder> mUpdateHolders = new ArrayList<ProgramUpdateHolder>();

	public void init() {
		TextureManager.getInstance().loadDefaultTexture();

		for(Program prog : ProgramCollection.getInstance().getAllPrograms()) {
			mUpdateHolders.add(new ProgramUpdateHolder(prog));
		}

		mPerspCamera = new PerspectiveCamera();
		mOrthoCamera = new OrthoCamera();

		setActiveCamera(mOrthoCamera);

		updateUniform(UniformType.MatUI, new Matrix());

		UIQuad.init();

		MapArea area = new MapArea("World/Maps/Kalimdor/Kalimdor_40_29.adt", 40, 29);
		try {
			area.asyncLoad();
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void invokeOnRenderThread(Runnable task) {
		GLSurfaceView view = UIManager.getInstance().getSurface().getView();
		view.queueEvent(task);
	}

	public void setActiveCamera(Camera camera) {
		mActiveCamera = camera;

		for(ProgramUpdateHolder holder : mUpdateHolders) {
			holder.updateUniform(UniformType.MatProj, camera.getProjection());
			holder.updateUniform(UniformType.MatView, camera.getView());
		}
	}

	public OrthoCamera getOrthoCamera() {
		return mOrthoCamera;
	}

	public void onFrame() {

		setActiveCamera(mOrthoCamera);
		UIManager.getInstance().renderUI();
	}

	@Override
	public void matrixChanged(Camera camera, boolean view, Matrix matrix) {
	   	if(camera != mActiveCamera) {
			return;
		}

		for(ProgramUpdateHolder holder : mUpdateHolders) {
			holder.updateUniform(view ? UniformType.MatView : UniformType.MatProj, matrix);
		}
	}

	public void updateUniform(UniformType type, Matrix mat) {
		for(ProgramUpdateHolder holder : mUpdateHolders) {
			holder.updateUniform(type, mat);
		}
	}
}
