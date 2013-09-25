package ch.cromon.YiasMobile.scene;

import ch.cromon.YiasMobile.UI.graphics.*;
import ch.cromon.YiasMobile.math.Matrix;
import ch.cromon.YiasMobile.math.Vector3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
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

	private ArrayList<ProgramUpdateHolder> mUpdateHolders = new ArrayList<ProgramUpdateHolder>();

	public void init() {
		for(Program prog : ProgramCollection.getInstance().getAllPrograms()) {
			mUpdateHolders.add(new ProgramUpdateHolder(prog));
		}

		mPerspCamera = new PerspectiveCamera();
		mOrthoCamera = new OrthoCamera();

		setActiveCamera(mOrthoCamera);

		geom.setVertexCount(4);
		geom.addElement(Semantic.Position, 0, 2);
		geom.addElement(Semantic.Color, 0, 4, Component.Byte, true);
		geom.setLayout(InputLayout.TriangleFan);
		geom.setTriangleCount(2);
		geom.setProgram(ProgramCollection.getInstance().getProgram("UIQuad"));

		Buffer vbuffer = new Buffer();
		ByteBuffer bbuffer = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder());
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

		for(int i = 0; i < 4; ++i) {
			bbuffer.putFloat(vertices[i][0]).putFloat(vertices[i][1]).putInt(colors[i]);
		}

		vbuffer.setData(bbuffer);
		geom.setVertexBuffer(vbuffer);

		Buffer indexBuffer = new Buffer(true);
		ByteBuffer index = ByteBuffer.allocate(4 * 4).order(ByteOrder.nativeOrder());
		index.putInt(0).putInt(1).putInt(2).putInt(3);
		indexBuffer.setData(index);

		geom.setIndexBuffer(indexBuffer);

		geom.create();
	}

	public void setActiveCamera(Camera camera) {
		mActiveCamera = camera;

		for(ProgramUpdateHolder holder : mUpdateHolders) {
			holder.updateUniform(UniformType.MatProj, camera.getProjection());
			holder.updateUniform(UniformType.MatView, camera.getView());
		}
	}

	public void onFrame() {
		Pipeline.getInstance().setInputGeometry(geom);
		Pipeline.getInstance().render();
		Pipeline.getInstance().setInputGeometry(null);
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
