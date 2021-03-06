package ch.cromon.YiasMobile.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import ch.cromon.YiasMobile.io.InputManager;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public class MainWindow extends Activity {
	private static MainWindow gInstance = null;

	public static MainWindow getInstance() {
		return gInstance;
	}

	public MainWindow() {
		gInstance = this;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		UIManager.getInstance().onMainViewCreated(savedInstanceState);
    }

	@Override
	public boolean onTouchEvent(android.view.MotionEvent event) {
		return InputManager.getInstance().onTouch(event);
	}
}