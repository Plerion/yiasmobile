package ch.cromon.YiasMobile.io;

import android.view.MotionEvent;
import android.view.View;
import ch.cromon.YiasMobile.UI.UIManager;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 19:08
 */
public class InputManager implements View.OnTouchListener {
 	private static InputManager gInstance = new InputManager();

	public static InputManager getInstance() {
		return gInstance;
	}

	public InputManager() {

	}

	public void init() {
		UIManager.getInstance().getSurface().getView().setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return false;
	}
}
