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
	private long mCurPosition = 0;
	RandomAccessFile mFile;

	public RandomAccessStream(RandomAccessFile file) {
		mFile = file;
	}

	public int read() throws IOException {
		++mCurPosition;
		return mFile.read();
	}

	public void seek(long position) throws IOException {
		mCurPosition = position;
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

	public byte readByte() throws IOException {
		return (byte)(read() & 0xFF);
	}

	public void readFully(byte[] data) throws IOException {
		mFile.read(data);
		mCurPosition += data.length;
	}
}
