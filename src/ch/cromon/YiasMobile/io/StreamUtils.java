package ch.cromon.YiasMobile.io;

import java.io.IOException;

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

	public static IffChunk readChunk(RandomAccessStream strm) throws IOException {
		int signature = strm.readInt();
		int size = strm.readInt();
		if(size < 0) {
			throw new IOException("End of chunk stream reached");
		}

		byte[] data = new byte[size];

		strm.readFully(data);

		IffChunk ic = new IffChunk();
		ic.data = data;
		ic.Size = size;
		ic.Signature = signature;

		return ic;
	}
}
