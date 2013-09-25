package ch.cromon.YiasMobile.UI.messaging;

import ch.cromon.YiasMobile.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 19:12
 */
public class UIMessage {
	protected MessageType mType;
	protected MessageKind mMessageKind;
	boolean mIsHandled;

	public UIMessage() {
		mType = MessageType.TouchDown;
		mMessageKind = MessageKind.Other;
		mIsHandled = false;
	}

	public MessageType getType() {
		return mType;
	}

	public MessageKind getMessageType() {
		return mMessageKind;
	}

	public void setHandled(boolean handled) {
		mIsHandled = handled;
	}

	public boolean isHandled() {
		return mIsHandled;
	}

	public UIMessage forwardMessage(Vector2 parentPos) {
		return this;
	}
}
