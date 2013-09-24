package ch.cromon.YiasMobile.scene;

import ch.cromon.YiasMobile.UI.graphics.Program;
import ch.cromon.YiasMobile.UI.graphics.ProgramCollection;
import ch.cromon.YiasMobile.UI.graphics.ProgramUpdateHolder;
import ch.cromon.YiasMobile.UI.graphics.UniformType;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class WorldFrame {
	private static WorldFrame gInstance = new WorldFrame();

	public static WorldFrame getInstance() {
		return gInstance;
	}

	private Camera mActiveCamera = null;
	private PerspectiveCamera mPerspCamera = null;
	private OrthoCamera mOrthoCamera = null;

	private ArrayList<ProgramUpdateHolder> mUpdateHolders = new ArrayList<ProgramUpdateHolder>();

	public void init() {
		for(Program prog : ProgramCollection.getInstance().getAllPrograms()) {
			mUpdateHolders.add(new ProgramUpdateHolder(prog));
		}

		mPerspCamera = new PerspectiveCamera();
		mOrthoCamera = new OrthoCamera();

		setActiveCamera(mPerspCamera);
	}

	public void setActiveCamera(Camera camera) {
		mActiveCamera = camera;

		for(ProgramUpdateHolder holder : mUpdateHolders) {
			holder.updateUniform(UniformType.MatProj, camera.getProjection());
			holder.updateUniform(UniformType.MatView, camera.getView());
		}
	}

	public void onFrame() {

	}
}
