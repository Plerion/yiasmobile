package ch.cromon.YiasMobile.io;

import android.view.MotionEvent;
import android.view.View;
import ch.cromon.YiasMobile.UI.UIManager;
import ch.cromon.YiasMobile.UI.messaging.UITouchMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 19:08
 */
public class InputManager {
 	private static InputManager gInstance = new InputManager();

	public static InputManager getInstance() {
		return gInstance;
	}

	public InputManager() {

	}

	public boolean onTouch(MotionEvent motionEvent) {

		UITouchMessage msg = new UITouchMessage(motionEvent);

		UIManager.getInstance().dispatchMessage(msg);
		return false;
	}
}
