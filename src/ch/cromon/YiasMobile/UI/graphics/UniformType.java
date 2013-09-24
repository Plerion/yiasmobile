package ch.cromon.YiasMobile.UI.graphics;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */
public enum UniformType {
	MatView ("matView"),
	MatProj ("matProj"),
	MatWorld ("matWorld");

	private final String uniformName;

	UniformType(String name) {
		uniformName = name;
	}

	public String getUniformName() {
		return uniformName;
	}

	public static UniformType[] values = UniformType.values();
}
