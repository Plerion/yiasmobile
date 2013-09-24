package ch.cromon.YiasMobile.UI.graphics;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
public class InputGeometry {
	private ArrayList<VertexElement> mElements = new ArrayList<VertexElement>();
	private Buffer mVertexBuffer = null;
	private Buffer mIndexBuffer = null;
	private int mVertexCount = 0;
	private int mTriangleCount = 0;
	private Program mProgram = null;
	private int mStride = 0;
	private InputLayout mLayout = InputLayout.Triangles;

	public InputGeometry() {

	}

	public void addElement(VertexElement elem) {
		mElements.add(elem);
	}

	public void addElement(Semantic semantic, int index, int componentCount, Component type, boolean normalized) {
		mElements.add(new VertexElement(semantic, index, componentCount, type, normalized));
	}

	public void addElement(Semantic semantic, int index, int componentCount) {
		mElements.add(new VertexElement(semantic, index, componentCount));
	}

	public void setLayout(InputLayout layout) {
		mLayout = layout;
	}

	public void setVertexBuffer(Buffer buffer) {
		mVertexBuffer = buffer;
	}

	public void setIndexBuffer(Buffer buffer) {
		mIndexBuffer = buffer;
	}

	public void setVertexCount(int vertices) {
		mVertexCount = vertices;
	}

	public void setTriangleCount(int triangles) {
		mTriangleCount = triangles;
	}

	public void setProgram(Program program) {
		mProgram = program;
	}

	public Program getProgram() {
		return mProgram;
	}

	public InputLayout getLayout() {
		return mLayout;
	}

	public int getVertexCount() {
		return mVertexCount;
	}

	public int getTriangleCount() {
		return mTriangleCount;
	}

	public Buffer getVertexBuffer() {
		return mVertexBuffer;
	}

	public Buffer getIndexBuffer() {
		return mIndexBuffer;
	}

	public void create() {
		if(mElements.size() == 0) {
			throw new IllegalStateException("Cannot create input geometry with no vertex elements.");
		}

		if(mProgram == null) {
			throw new IllegalStateException("Cannot create input geometry with no program attached.");
		}

		mStride = 0;

		for(VertexElement elem : mElements) {
			elem.fillAttribInfo(mProgram.getID());
			mStride += elem.getElementSize();
		}
	}

	public void applyElements() {
		if(mElements.size() == 0) {
			throw new IllegalStateException("Cannot create input geometry with no vertex elements.");
		}

		if(mProgram == null) {
			throw new IllegalStateException("Cannot create input geometry with no program attached.");
		}

		int curOffset = 0;

		for(VertexElement elem : mElements) {
			elem.setAttribPointer(mStride, curOffset);
			curOffset += elem.getElementSize();
		}
	}
}
