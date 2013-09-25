package ch.cromon.YiasMobile.UI.elements;

import android.util.Log;
import ch.cromon.YiasMobile.UI.graphics.Texture;
import ch.cromon.YiasMobile.UI.graphics.TextureManager;
import ch.cromon.YiasMobile.UI.messaging.MessageKind;
import ch.cromon.YiasMobile.UI.messaging.UIMessage;
import ch.cromon.YiasMobile.UI.messaging.UITouchMessage;
import ch.cromon.YiasMobile.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 18:49
 */
public class Button extends UIElement {
	private UIQuad mQuad;
	private Texture mTexUp, mTexDown, mTexHover, mTexDisabled;
	private boolean mIsHovered = false, mIsPressed = false;

	static float[][] texCoords = {
		{ 0, 0 },
		{ 78.0f / 128.0f, 0 },
		{ 78.0f / 128.0f, 21.0f / 32.0f },
		{ 0, 21.0f / 32.0f }
	};

	static float[][] texCoordsLarge = {
		{ 0, 0 },
		{ 1, 0 },
		{ 1, 22.0f / 32.0f },
		{ 0, 22.0f / 32.0f }
	};

	public Button() {
		this(new Vector2(0, 0), false);
	}

	public Button(Vector2 position) {
	 	this(position, false);
	}

	public Button(Vector2 position, boolean large) {
		mPosition = position;

		mQuad = new UIQuad(position, large ? new Vector2(128, 22) : new Vector2(78, 21));
		mSize = large ? new Vector2(128, 22) : new Vector2(78, 21);

		mQuad.setTexCoords(large ? texCoordsLarge : texCoords);

		if(large == false) {
			mTexUp = TextureManager.getInstance().getTexture("Interface/Buttons/UI-Panel-Button-Up.blp");
			mTexDown = TextureManager.getInstance().getTexture("Interface/Buttons/UI-Panel-Button-Down.blp");
			mTexHover = TextureManager.getInstance().getTexture("Interface/Buttons/UI-Panel-Button-Highlight.blp");
			mTexDisabled = TextureManager.getInstance().getTexture("Interface/Buttons/UI-Panel-Button-Disabled.blp");
		} else {
			mTexUp = TextureManager.getInstance().getTexture("Interface/Buttons/UI-DialogBox-Button-Up.blp");
			mTexDown = TextureManager.getInstance().getTexture("Interface/Buttons/UI-DialogBox-Button-Down.blp");
			mTexHover = TextureManager.getInstance().getTexture("Interface/Buttons/UI-DialogBox-Button-Highlight.blp");
			mTexDisabled = TextureManager.getInstance().getTexture("Interface/Buttons/UI-DialogBox-Button-Disabled.blp");
		}

		mQuad.setTexture(mTexUp);
	}

	@Override
	public void draw() {
		mQuad.draw();
	}

	@Override
	public void handleMessage(UIMessage message) {
		if(message.getMessageType() != MessageKind.Touch) {
			return;
		}

		if(message.isHandled() == true) {
			mIsHovered = false;
			mIsPressed = false;
			return;
		}

		UITouchMessage msg = (UITouchMessage)message;
		switch(msg.getType()) {
			case TouchDown: {
				onMouseDown(msg);
			}
			break;

			case TouchUp: {
				onMouseUp(msg);
			}
			break;

			case TouchMove: {
				onMouseMove(msg);
			}
			break;

			case NCTouchDown:
			case NCTouchUp:
			case NCTouchMove: {
				mIsHovered = false;
				mIsPressed = false;
			}
			break;
		}
	}

	private void onMouseMove(UITouchMessage msg) {
		if(msg.getCursorCount() > 1) {
			return;
		}

		Vector2 pos = msg.getPosition(msg.getActivePointerIndex());

		mIsHovered = (pos.x >= mPosition.x && pos.y >= mPosition.y && pos.x <= mPosition.x + mSize.x && pos.y <= mPosition.y + mSize.y);
	}

	private void onMouseUp(UITouchMessage msg) {
		Vector2 pos = msg.getPosition(msg.getActivePointerIndex());

		boolean isOver = (pos.x >= mPosition.x && pos.y >= mPosition.y && pos.x <= mPosition.x + mSize.x && pos.y <= mPosition.y + mSize.y);
		if(isOver && mIsPressed) {
			Log.d("TOUCH", "Button clicked");
		}

		mIsPressed = false;
		mQuad.setTexture(mTexUp);
	}

	private void onMouseDown(UITouchMessage msg) {
		Vector2 pos = msg.getPosition(msg.getActivePointerIndex());

		Log.d("TOUCH", "Pos: " + pos.x + "/" + pos.y + " MPOS: " + mPosition.x + "/" + mPosition.y);

		mIsPressed = (pos.x >= mPosition.x && pos.y >= mPosition.y && pos.x <= mPosition.x + mSize.x && pos.y <= mPosition.y + mSize.y);
		mQuad.setTexture(mIsPressed ? mTexDown : mTexUp);
	}
}
