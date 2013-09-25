package ch.cromon.YiasMobile.scene;

import android.opengl.GLSurfaceView;
import ch.cromon.YiasMobile.UI.MainWindow;
import ch.cromon.YiasMobile.UI.UIManager;
import ch.cromon.YiasMobile.UI.graphics.*;
import ch.cromon.YiasMobile.math.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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

	private InputGeometry geom = new InputGeometry();
	private TextureInput mTexInput;

	private ArrayList<ProgramUpdateHolder> mUpdateHolders = new ArrayList<ProgramUpdateHolder>();

	public void init() {
		TextureManager.getInstance().loadDefaultTexture();

		for(Program prog : ProgramCollection.getInstance().getAllPrograms()) {
			mUpdateHolders.add(new ProgramUpdateHolder(prog));
		}

		mPerspCamera = new PerspectiveCamera();
		mOrthoCamera = new OrthoCamera();

		setActiveCamera(mOrthoCamera);

		geom.setVertexCount(4);
		geom.addElement(Semantic.Position, 0, 2);
		geom.addElement(Semantic.Color, 0, 4, Component.Byte, true);
		geom.addElement(Semantic.TexCoord, 0, 2);
		geom.setLayout(InputLayout.TriangleFan);
		geom.setTriangleCount(2);
		geom.setProgram(ProgramCollection.getInstance().getProgram("UIQuad"));

		Buffer vbuffer = new Buffer();
		ByteBuffer bbuffer = ByteBuffer.allocateDirect(4 * (8 + 4 + 8)).order(ByteOrder.nativeOrder());
		float[][] vertices = new float[4][2];
		vertices[0][0] = 0;
		vertices[0][1] = 0;
		vertices[1][0] = 50;
		vertices[1][1] = 0;
		vertices[2][0] = 50;
		vertices[2][1] = 50;
		vertices[3][0] = 0;
		vertices[3][1] = 50;

		int[] colors = {
			0xFFFF0000,
			0xFF00FF00,
			0xFF0000FF,
			0xFFFF7F3F
		};

		float[][] texCoords = {
			{ 0, 0},
			{ 1, 0 },
			{ 1, 1 },
			{ 0, 1 }
		};

		for(int i = 0; i < 4; ++i) {
			bbuffer.putFloat(vertices[i][0]).putFloat(vertices[i][1]).putInt(colors[i]).putFloat(texCoords[i][0]).putFloat(texCoords[i][1]);
		}

		vbuffer.setData(bbuffer);
		geom.setVertexBuffer(vbuffer);

		Buffer indexBuffer = new Buffer(true);
		ByteBuffer index = ByteBuffer.allocate(4 * 4).order(ByteOrder.nativeOrder());
		index.putInt(0).putInt(1).putInt(2).putInt(3);
		indexBuffer.setData(index);

		geom.setIndexBuffer(indexBuffer);

		geom.create();

		mTexInput = new TextureInput(geom.getProgram());
		mTexInput.defineTexture("baseSampler");

		mTexInput.setTexture(0, TextureManager.getInstance().getTexture("Interface/Buttons/BLUEGRAD64.blp"));
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

	public void onFrame() {
		Pipeline.getInstance().setInputs(geom, mTexInput);
		Pipeline.getInstance().render();
		Pipeline.getInstance().clearInputs();
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
}
