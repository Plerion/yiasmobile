package ch.cromon.YiasMobile.UI.elements;

import ch.cromon.YiasMobile.UI.graphics.UniformType;
import ch.cromon.YiasMobile.math.Matrix;
import ch.cromon.YiasMobile.scene.WorldFrame;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 20:18
 */
public class MatrixStack {
	Stack<Matrix> mStack = new Stack<Matrix>();

	public MatrixStack() {
		mStack.add(new Matrix());
	}

	public void push(Matrix mat) {
		Matrix top = mStack.peek();
		Matrix newTop = Matrix.multiply(top, mat);
		mStack.add(newTop);

		WorldFrame.getInstance().updateUniform(UniformType.MatUI, newTop);
	}

	public void pop() {
		if(mStack.size() <= 1) {
			throw new IllegalStateException("Cannot pop from matrix stack with no matrices");
		}

		mStack.pop();
		Matrix newTop = mStack.peek();

		WorldFrame.getInstance().updateUniform(UniformType.MatUI, newTop);
	}

	public void load() {
		mStack.clear();
		mStack.add(new Matrix());

		WorldFrame.getInstance().updateUniform(UniformType.MatUI, mStack.peek());
	}
}
