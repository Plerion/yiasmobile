package ch.cromon.YiasMobile.UI.messaging;

import android.util.Log;
import android.view.MotionEvent;
import ch.cromon.YiasMobile.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 19:16
 */
public class UITouchMessage extends UIMessage {
	private MotionEvent mEvent;
	private Vector2 mMessageOffset = new Vector2(0, 0);

	public UITouchMessage(MotionEvent args) {
		super();

		mMessageKind = MessageKind.Touch;

		switch(args.getActionMasked()) {
			case MotionEvent.ACTION_DOWN: {
				mType = MessageType.TouchDown;
			}
			break;

			case MotionEvent.ACTION_UP: {
				mType = MessageType.TouchUp;
			}
			break;

			case MotionEvent.ACTION_MOVE: {
				mType = MessageType.TouchMove;
			}
			break;
		}

		mEvent = args;
	}

	public UITouchMessage(MessageType type, MotionEvent args) {
		mEvent = args;
		mType = type;
	}

	public void setType(MessageType type) {
		mType = type;
	}

	public Vector2 getPosition(int pointer) {
		MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();

		if(pointer >= mEvent.getPointerCount()) {
			throw new IllegalArgumentException("Invalid pointer index (out of range)");
		}

		mEvent.getPointerCoords(pointer, coords);
		return Vector2.sub(new Vector2(coords.x, coords.y), mMessageOffset);
	}

	public int getCursorCount() {
		return mEvent.getPointerCount();
	}

	public int getActivePointerIndex() {
		return mEvent.getActionIndex();
	}

	public int getActivePointer() {
		return mEvent.getPointerId(mEvent.getActionIndex());
	}

	@Override
	public UIMessage forwardMessage(Vector2 parentPos) {
		UITouchMessage newMsg = new UITouchMessage(mEvent);
		newMsg.mMessageOffset = Vector2.add(mMessageOffset, parentPos);
		newMsg.setHandled(this.isHandled());

		return newMsg;
	}
}
