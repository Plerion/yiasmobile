package ch.cromon.YiasMobile.UI.messaging;

import android.view.MotionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 19:16
 */
public class UITouchMessage extends UIMessage {
	public UITouchMessage(MotionEvent args) {
		mMessageKind = MessageKind.Touch;

		switch(args.getActionMasked()) {
			case MotionEvent.ACTION_DOWN: {

			}
			break;
		}
	}
}
