package ch.cromon.YiasMobile.io;

import android.content.Context;
import android.os.Environment;
import ch.cromon.YiasMobile.UI.MainWindow;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public class ResourceManager {
	private static ResourceManager gInstance = new ResourceManager();

	public static ResourceManager getInstance() {
		return gInstance;
	}

	public InputStream getAsset(String name) {
		Context ctx = MainWindow.getInstance().getApplicationContext();

		try {
			return ctx.getAssets().open(name);
		} catch (IOException e) {
			return null;
		}
	}

	public RandomAccessStream getFile(String file) {
		File sdcard = Environment.getExternalStorageDirectory();

		File assetFile = new File(sdcard, "World Of Warcraft/" + file);

		try {
			return new RandomAccessStream(new RandomAccessFile(assetFile, "r"));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
