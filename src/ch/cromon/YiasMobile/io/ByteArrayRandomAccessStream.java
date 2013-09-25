package ch.cromon.YiasMobile.io;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 23:05
 */
public class ByteArrayRandomAccessStream extends RandomAccessStream {
	byte[] mContent;
	int mCurPosition = 0;

	public ByteArrayRandomAccessStream(byte[] data) {
		mContent = data;
	}

	@Override
	public int read() {
		return mContent[mCurPosition++];
	}

	@Override
	public void seek(long position) {
		mCurPosition = (int)position;
		if(mCurPosition >= mContent.length) {
			throw new IllegalArgumentException("Position out of range");
		}
	}

	@Override
	public void readFully(byte[] data) {
		System.arraycopy(mContent, mCurPosition, data, 0, data.length);
		mCurPosition += data.length;
	}
}
