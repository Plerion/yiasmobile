package ch.cromon.YiasMobile.UI.graphics;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 13:43
 */
public enum PixelFormat {
	DXT1,
	DXT3,
	ARGB888,
	ARGB1555,
	ARGB4444,
	RGB565,
	A8,
	DXT5;

	public static final PixelFormat[] formats = PixelFormat.values();
}
