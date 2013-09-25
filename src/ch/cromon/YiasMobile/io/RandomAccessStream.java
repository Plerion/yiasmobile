package ch.cromon.YiasMobile.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 15:20
 */
public class RandomAccessStream {
	RandomAccessFile mFile;

	public RandomAccessStream(RandomAccessFile file) {
		mFile = file;
	}

	public RandomAccessStream() {

	}

	public int read() throws IOException {
		return mFile.read();
	}

	public void seek(long position) throws IOException {
		mFile.seek(position);
	}

	public int readInt() throws IOException {
		int ret = 0;
		ret |= read() & 0xFF;
		ret |= (read() & 0xFF) << 8;
		ret |= (read() & 0xFF) << 16;
		ret |= (read() & 0xFF) << 24;

		return ret;
	}

	public float readFloat() throws IOException {
		int ival = readInt();
		return Float.intBitsToFloat(ival);
	}

	public long readLong() throws IOException {
		long v1 = readInt() & 0xFFFFFFFF;
		long v2 = readInt() & 0xFFFFFFFF;

		return (v1 | (v2 << 32));
	}

	public byte readByte() throws IOException {
		return (byte)(read() & 0xFF);
	}

	public void readFully(byte[] data) throws IOException {
		mFile.read(data);
	}
}
