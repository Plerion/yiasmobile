package ch.cromon.YiasMobile.UI.graphics;

import ch.cromon.YiasMobile.io.RandomAccessStream;
import ch.cromon.YiasMobile.io.ResourceManager;
import ch.cromon.YiasMobile.scene.WorldFrame;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 13:26
 */
public class TextureFetchThread extends Thread {
	private volatile boolean mIsRunning = true;

	public class WorkItem {
		private String mTextureName;
		private Texture mTexture;

		public WorkItem(String texName, Texture texPtr) {
			mTexture = texPtr;
			mTextureName = texName;
		}

		public String getTextureName() {
			return mTextureName;
		}

		public Texture getTexture() {
			return mTexture;
		}
	}

	private Queue<WorkItem> mWorkItems = new ArrayDeque<WorkItem>();

	public void pushTask(String textureFile, Texture ptr) {
		WorkItem wi = new WorkItem(textureFile, ptr);
		synchronized (mWorkItems) {
			mWorkItems.add(wi);
		}
	}

	public int getWorkLoad() {
		return mWorkItems.size();
	}

	@Override
	public void run() {
		while(mIsRunning) {
			WorkItem curItem;
			synchronized(mWorkItems) {
				curItem = mWorkItems.peek();
				if(curItem != null) {
					mWorkItems.remove();
				}
			}

			if(curItem != null) {
				loadBlpTexture(curItem);
			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}

	private void loadBlpTexture(WorkItem item) {
		RandomAccessStream strm = ResourceManager.getInstance().getFile(item.getTextureName());
		if(strm == null) {
			return;
		}

		try {
			final BlpParser parser = new BlpParser(strm);
			final WorkItem workItem = item;

			Runnable loader = new Runnable() {
				@Override
				public void run() {
					 workItem.getTexture().loadFromBlp(parser);
				}
			};

			WorldFrame.getInstance().invokeOnRenderThread(loader);
		} catch (IOException e) {

		}

	}
}
