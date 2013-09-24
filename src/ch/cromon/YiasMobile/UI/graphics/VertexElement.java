package ch.cromon.YiasMobile.UI.graphics;

import android.opengl.GLES20;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public class VertexElement {
	public VertexElement(Semantic semantic, int index, int numComponents, Component component, boolean normalized) {
		mSemantic = semantic;
		mIndex = index;
		mComponentCount = numComponents;
		mType = component;
		mNormalized = normalized;
	}

	public VertexElement(Semantic semantic, int index, int numComponents) {
		mSemantic = semantic;
		mIndex = index;
		mComponentCount = numComponents;
		mType = Component.Float;
		mNormalized = false;
	}

	public void fillAttribInfo(int program) {
		if(mAttribIndex >= 0) {
			throw new IllegalStateException("Vertex element already initialized!");
		}

		String attribName = "";
		switch(mSemantic) {
			case Position:
				attribName = "position";
				break;

			case Normal:
				attribName = "normal";
				break;

			case TexCoord:
				attribName = "texcoord";
				break;

			case Color:
				attribName = "color";
				break;

			default:
				throw new IllegalStateException("Vertex element has a illegal semantic");
		}

		attribName += Integer.toString(mAttribIndex);
		mAttribIndex = GLES20.glGetAttribLocation(program, attribName);

		mElemSize = (mType == Component.Float ? 4 : 1) * mComponentCount;
	}

	public void setAttribPointer(int stride, int offset) {
		GLES20.glVertexAttribPointer(mAttribIndex, mComponentCount, mType == Component.Float ? GLES20.GL_FLOAT : GLES20.GL_UNSIGNED_BYTE, mNormalized, stride, offset);
	}

	public int getElementSize() {
		return mElemSize;
	}

	int mAttribIndex = -1;
	int mElemSize = 0;

	Semantic mSemantic;
	int mIndex;
	int mComponentCount;
	Component mType;
	boolean mNormalized;
}
