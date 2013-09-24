package ch.cromon.YiasMobile.UI.graphics;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public class ProgramCollection {
	private static ProgramCollection gInstance = new ProgramCollection();

	public static ProgramCollection getInstance() {
		return gInstance;
	}

	private HashMap<String, Program> mPrograms = new HashMap<String, Program>();

	public void init() {
		loadProgram("UIQuad");
	}

	public Program getProgram(String name) {
		return mPrograms.get(name);
	}

	public Collection<Program> getAllPrograms() {
		return mPrograms.values();
	}

	private void loadProgram(String program) {
		String vertex = "shaders/" + program + ".vs";
		String fragment = "shaders/" + program + ".fs";

		Program prog = new Program(vertex, fragment);

		mPrograms.put(program, prog);
	}
}
