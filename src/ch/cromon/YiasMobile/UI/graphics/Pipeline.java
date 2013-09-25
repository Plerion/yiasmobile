package ch.cromon.YiasMobile.UI.graphics;

import android.opengl.GLES20;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
public class Pipeline {
	private static Pipeline gInstance = new Pipeline();

	public static Pipeline getInstance() {
		return gInstance;
	}

	private InputGeometry mCurInput;
	private TextureInput mTexInput;

	public void setInputGeometry(InputGeometry geom) {
		mCurInput = geom;
	}

	public void setTextureInput(TextureInput texInput) {
		mTexInput = texInput;
	}

	public void setInputs(InputGeometry geom, TextureInput tex) {
		setInputGeometry(geom);
		setTextureInput(tex);
	}

	public void clearInputs() {
		setInputGeometry(null);
		setTextureInput(null);
	}

	public void render() {
		InputGeometry geom = mCurInput;

		if(geom.getVertexBuffer() == null) {
			throw new IllegalArgumentException("No vertex buffer attached to geometry");
		}

		geom.getProgram().bind();
		geom.getVertexBuffer().bind();

		geom.applyElements();

		int indexCount = 0;
		if(geom.getLayout() == InputLayout.Triangles) {
			indexCount = geom.getTriangleCount() * 3;
		} else {
			indexCount = geom.getTriangleCount() + 2;
		}

		if(geom.getIndexBuffer() != null) {
			geom.getIndexBuffer().bind();
		}

		if(mTexInput != null) {
			mTexInput.apply();
		}

		GLES20.glDrawElements(geom.getLayout().getGLValue(), indexCount, GLES20.GL_UNSIGNED_INT, 0);

		if(mTexInput != null) {
			mTexInput.remove();
		}

		if(geom.getIndexBuffer() != null) {
			geom.getIndexBuffer().unbind();
		}

		geom.getProgram().unbind();
		geom.getVertexBuffer().unbind();

	}
}
