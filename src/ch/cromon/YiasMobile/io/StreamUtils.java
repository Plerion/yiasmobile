package ch.cromon.YiasMobile.io;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class StreamUtils {
	public static String toString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
