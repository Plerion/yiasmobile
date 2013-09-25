package ch.cromon.YiasMobile.UI.elements;

import android.opengl.GLES20;
import ch.cromon.YiasMobile.UI.graphics.*;
import ch.cromon.YiasMobile.math.Vector2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 18:00
 */
public class UIQuad {
	private Vector2 mPosition = new Vector2(0, 0);
	private Vector2 mSize = new Vector2(0, 0);
	private int mColor = 0xFFFFFFFF;
	private ByteBuffer mDataBuffer = ByteBuffer.allocate(4 * (8 + 4 + 8)).order(ByteOrder.nativeOrder());
	private Buffer mVertexBuffer = new Buffer();
	private Texture mTexture;
	private float[][] mTexCoords = {
		{ 0, 0 },
		{ 1, 0 },
		{ 1, 1 },
		{ 0, 1 }
	};

	private BlendMode mBlendMode = BlendMode.Alpha;

	private static InputGeometry gInputGeometry = new InputGeometry();
	private static TextureInput gTexInput;
	private static Buffer gIndexBuffer = new Buffer(true);

	public UIQuad(Vector2 position, Vector2 size) {
		mTexture = TextureManager.getInstance().getTexture("Interface/Buttons/BLUEGRAD64.blp");
		mPosition = position;
		mSize = size;

		updateVertexBuffer();
	}

	public UIQuad() {
		mTexture = TextureManager.getInstance().getTexture("Interface/Buttons/BLUEGRAD64.blp");
	 	updateVertexBuffer();
	}

	public void setTexture(String texture) {
		mTexture = TextureManager.getInstance().getTexture(texture);
	}

	public void setTexture(Texture tex) {
		mTexture = tex;
	}

	public void setBlendMode(BlendMode mode) {
		mBlendMode = mode;
	}

	public static void init() {
		gInputGeometry.setVertexCount(4);
		gInputGeometry.addElement(Semantic.Position, 0, 2);
		gInputGeometry.addElement(Semantic.Color, 0, 4, Component.Byte, true);
		gInputGeometry.addElement(Semantic.TexCoord, 0, 2);
		gInputGeometry.setLayout(InputLayout.TriangleFan);
		gInputGeometry.setTriangleCount(2);
		gInputGeometry.setProgram(ProgramCollection.getInstance().getProgram("UIQuad"));

		gInputGeometry.create();

		gTexInput = new TextureInput(gInputGeometry.getProgram());
		gTexInput.defineTexture("baseSampler");

		ByteBuffer ibuff = ByteBuffer.allocate(4 * 4).order(ByteOrder.nativeOrder());
		ibuff.putInt(0).putInt(1).putInt(2).putInt(3);

		gIndexBuffer.setData(ibuff);

		gInputGeometry.setIndexBuffer(gIndexBuffer);
	}

	public void setTexCoords(float[][] texCoords) {
		assert(texCoords.length >= 4 && texCoords[0].length >= 2);

		mTexCoords = texCoords;
		updateVertexBuffer();
	}

	public void setMetrics(Vector2 pos, Vector2 size) {
		mPosition = pos;
		mSize = size;

		updateVertexBuffer();
	}

	public void draw() {
		gTexInput.setTexture(0, mTexture);
		gInputGeometry.setVertexBuffer(mVertexBuffer);

		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable(GLES20.GL_BLEND);

		GLES20.glBlendFunc(mBlendMode.getSrcBlend(), mBlendMode.getDestBlend());

		Pipeline.getInstance().setInputs(gInputGeometry, gTexInput);
		Pipeline.getInstance().render();
		Pipeline.getInstance().clearInputs();

		GLES20.glDisable(GLES20.GL_BLEND);
	}

	private void updateVertexBuffer() {
		mDataBuffer.position(0);
		ByteBuffer b = mDataBuffer;

		b.putFloat(mPosition.x).putFloat(mPosition.y).putInt(mColor).putFloat(mTexCoords[0][0]).putFloat(mTexCoords[0][1]);
		b.putFloat(mPosition.x + mSize.x).putFloat(mPosition.y).putInt(mColor).putFloat(mTexCoords[1][0]).putFloat(mTexCoords[1][1]);
		b.putFloat(mPosition.x + mSize.x).putFloat(mPosition.y + mSize.y).putInt(mColor).putFloat(mTexCoords[2][0]).putFloat(mTexCoords[2][1]);
		b.putFloat(mPosition.x).putFloat(mPosition.y + mSize.y).putInt(mColor).putFloat(mTexCoords[3][0]).putFloat(mTexCoords[3][1]);

		mVertexBuffer.setData(b);
	}


}
