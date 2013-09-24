package ch.cromon.YiasMobile.io;

import android.content.Context;
import ch.cromon.YiasMobile.UI.MainWindow;

import java.io.IOException;
import java.io.InputStream;

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
}
