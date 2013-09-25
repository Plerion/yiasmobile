package ch.cromon.YiasMobile.UI.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.ref.WeakReference;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 16:24
 */
public class TextureManager {
	public static final Texture defaultTexture = new Texture(false);

	private static TextureManager gInstance = new TextureManager();

	public static TextureManager getInstance() {
		return gInstance;
	}

	public void loadDefaultTexture() {
		byte[] data = {
			0x00, (byte)0xFF, 0x00, (byte)0xFF
		};

		defaultTexture.loadMemoryARGB(data, 1, 1);
	}

	private HashMap<String, WeakReference<Texture>> mCache = new HashMap<String, WeakReference<Texture>>();
	private ArrayList<TextureFetchThread> mFetchers = new ArrayList<TextureFetchThread>();

	public TextureManager() {
		for(int i = 0; i < 3; ++i) {
			mFetchers.add(new TextureFetchThread());
			mFetchers.get(i).start();
		}
	}

	public Texture getTexture(String fileName) {
		synchronized(mCache) {
	   		if(mCache.containsKey(fileName.toLowerCase())) {
				WeakReference<Texture> ref = mCache.get(fileName.toLowerCase());
				Texture obj = ref.get();
				if(obj != null) {
					return obj;
				}
			}

			Texture ret = new Texture();
			TextureFetchThread curMin = mFetchers.get(0);
			int minWork = curMin.getWorkLoad();

			for(int i = 1; i < mFetchers.size(); ++i) {
				TextureFetchThread tft = mFetchers.get(i);
				if(tft.getWorkLoad() < minWork) {
					curMin = tft;
					minWork = curMin.getWorkLoad();
				}
			}

			curMin.pushTask(fileName, ret);
			mCache.put(fileName.toLowerCase(), new WeakReference<Texture>(ret));
			return ret;
		}
	}
}
