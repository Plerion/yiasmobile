package ch.cromon.YiasMobile.UI.graphics;

import ch.cromon.YiasMobile.math.Matrix;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 00:17
 * To change this template use File | Settings | File Templates.
 */
public class ProgramUpdateHolder {
	private Program mProgram;
	private HashMap<UniformType, Integer> mUniformMap = new HashMap<UniformType, Integer>();

	public ProgramUpdateHolder(Program program) {
		mProgram = program;

		checkUniforms();
	}

	private void checkUniforms() {
		for(UniformType type : UniformType.values) {
			int uniform = mProgram.getUniform(type.getUniformName());
			if(uniform < 0) {
				continue;
			}

			mUniformMap.put(type, uniform);
		}
	}

	public void updateUniform(UniformType type, Matrix value) {
		if(mUniformMap.containsKey(type) == false) {
			return;
		}

		int index = mUniformMap.get(type);

		mProgram.setMatrix(index, value);
	}
}
