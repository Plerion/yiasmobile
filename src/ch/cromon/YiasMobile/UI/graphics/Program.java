package ch.cromon.YiasMobile.UI.graphics;

import android.opengl.GLES20;
import ch.cromon.YiasMobile.io.ResourceManager;
import ch.cromon.YiasMobile.io.StreamUtils;
import ch.cromon.YiasMobile.math.Matrix;

import java.io.InputStream;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class Program {
	private static Stack<Integer> gProgramStack = new Stack<Integer>();

	private int mVertexShader;
	private int mFragmentShader;
	private int mProgram;

	public Program() {

	}

	public Program(String assetVertex, String assetFragment) {
		InputStream vinput = ResourceManager.getInstance().getAsset(assetVertex);
		InputStream finput = ResourceManager.getInstance().getAsset(assetFragment);

		if(vinput == null || finput == null) {
			throw new IllegalArgumentException("Vertex shader or fragment shader not found.");
		}

		String vshader = StreamUtils.toString(vinput);
		String fshader = StreamUtils.toString(finput);

		mProgram = GLES20.glCreateProgram();

		compileShaders(vshader, fshader);
		linkProgram();
	}

	public void bind() {
		GLES20.glUseProgram(mProgram);
		gProgramStack.push(mProgram);
	}

	public void unbind() {
		if(gProgramStack.empty() == true) {
			GLES20.glUseProgram(0);
		} else {
			GLES20.glUseProgram(gProgramStack.pop());
		}
	}

	public int getID() {
		return mProgram;
	}

	public int getUniform(String name) {
		return GLES20.glGetUniformLocation(mProgram, name);
	}

	public void setMatrix(int index, Matrix mat) {
		bind();
		GLES20.glUniformMatrix4fv(index, 1, false, mat.getFloats(), 0);
		unbind();
	}

	public void setSampler(int index, int textureNumber) {
		bind();
		GLES20.glUniform1i(index, textureNumber);
		unbind();
	}

	private void compileShaders(String vertex, String fragment) {
		mVertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		mFragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

		GLES20.glShaderSource(mVertexShader, vertex);
		GLES20.glCompileShader(mVertexShader);

		int[] status = new int[1];
		GLES20.glGetShaderiv(mVertexShader, GLES20.GL_COMPILE_STATUS, status, 0);

		if(status[0] != GLES20.GL_TRUE) {
			String message = GLES20.glGetShaderInfoLog(mVertexShader);
			throw new IllegalArgumentException("Invalid shader, errors:\n" + message);
		}

		GLES20.glShaderSource(mFragmentShader, fragment);
		GLES20.glCompileShader(mFragmentShader);

		GLES20.glGetShaderiv(mFragmentShader, GLES20.GL_COMPILE_STATUS, status, 0);

		if(status[0] != GLES20.GL_TRUE) {
			String message = GLES20.glGetShaderInfoLog(mFragmentShader);
			throw new IllegalArgumentException("Invalid shader, errors:\n" + message);
		}

		GLES20.glAttachShader(mProgram, mVertexShader);
		GLES20.glAttachShader(mProgram, mFragmentShader);
	}

	private void linkProgram() {
		GLES20.glLinkProgram(mProgram);

		int[] status = new int[1];
		GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, status, 0);

		if(status[0] != GLES20.GL_TRUE) {
			String message = GLES20.glGetProgramInfoLog(mProgram);
			throw new IllegalArgumentException("Unable to link shaders to a program, errors:\n" + message);
		}
	}
}
