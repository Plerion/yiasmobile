package ch.cromon.YiasMobile.UI.graphics;

import android.opengl.GLES20;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 16:32
 */
public class TextureInput {
	Program mProgram;
	ArrayList<Integer> mTextureIndices = new ArrayList<Integer>();
	ArrayList<Texture> mActiveTextures = new ArrayList<Texture>();

	public TextureInput(Program program) {
		mProgram = program;
	}

	public void defineTexture(String name) {
	 	int index = mProgram.getUniform(name);
		if(index < 0) {
			throw new IllegalArgumentException("Texture sampler not found in program");
		}

		mTextureIndices.add(index);
		mActiveTextures.add(null);
	}

	public void setTexture(int index, Texture texture) {
	 	if(index >= mActiveTextures.size()) {
			throw new IllegalArgumentException("Index not supported by this program");
		}

		mActiveTextures.set(index, texture);
	}

	public void apply() {
		int curIndex = 0;

		int arrayIndex = 0;

		for(Texture tex : mActiveTextures) {
			++arrayIndex;

			if(tex == null) {
				continue;
			}

			GLES20.glActiveTexture(curIndex);
			GLES20.glEnable(GLES20.GL_TEXTURE_2D);
			mProgram.setSampler(mTextureIndices.get(arrayIndex - 1), curIndex);

			tex.bind();
		}
	}

	public void remove() {
		int curIndex = 0;


		for(Texture tex : mActiveTextures) {
			if(tex == null) {
				continue;
			}

			GLES20.glActiveTexture(curIndex);
			tex.unbind();

			GLES20.glDisable(GLES20.GL_TEXTURE_2D);
			++curIndex;
		}
	}
}
