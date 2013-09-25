package ch.cromon.YiasMobile.UI.elements;

import android.opengl.GLES20;
import android.util.Log;
import ch.cromon.YiasMobile.UI.UIManager;
import ch.cromon.YiasMobile.UI.graphics.Texture;
import ch.cromon.YiasMobile.UI.graphics.TextureManager;
import ch.cromon.YiasMobile.UI.messaging.MessageKind;
import ch.cromon.YiasMobile.UI.messaging.MessageType;
import ch.cromon.YiasMobile.UI.messaging.UIMessage;
import ch.cromon.YiasMobile.UI.messaging.UITouchMessage;
import ch.cromon.YiasMobile.math.Matrix;
import ch.cromon.YiasMobile.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 20:35
 */
public class Frame extends ItemsControl {
	Texture mBorderTexture;
	Texture mBackgTexture;
	Texture mCaptionTexture;

	Texture mCloseTex, mCloseTexDown, mCloseHighlight;

	boolean mIsCloseDown, mIsCloseHovered, mIsHeaderDown;

	Vector2 mClickOffset;
	float mScrollOffset;

	UIQuad mQuadBackground = new UIQuad();
	UIQuad mQuadTopLeft = new UIQuad();
	UIQuad mQuadTopRight = new UIQuad();
	UIQuad mQuadBottomLeft = new UIQuad();
	UIQuad mQuadBottomRight = new UIQuad();
	UIQuad mQuadLeft = new UIQuad();
	UIQuad mQuadTop = new UIQuad();
	UIQuad mQuadRight = new UIQuad();
	UIQuad mQuadBottom = new UIQuad();
	UIQuad mCloseQuad = new UIQuad();
	UIQuad mCaptionQuad = new UIQuad();
	UIQuad mStencilQuad = new UIQuad();

	static float[][] texCoordTopLeft = {
		{ 94.0f / 128.0f, 0.0f },
		{ 94.0f / 128.0f, 1.0f },
		{ 79.0f / 128.0f, 1.0f },
		{ 79.0f / 128.0f, 0.0f }
	};

	static float[][] texCoordTopRight = {
		{ 126.0f / 128.0f, 0.0f },
		{ 126.0f / 128.0f, 1.0f },
		{ 111.0f / 128.0f, 1.0f },
		{ 111.0f / 128.0f, 0.0f }
	};

	static float[][] texCoordBottomLeft = {
		{ 80.0f / 128.0f, 0.0f },
		{ 80.0f / 128.0f, 1.0f },
		{ 65.0f / 128.0f, 1.0f },
		{ 65.0f / 128.0f, 0.0f }
	};

	static float[][] texCoordBottomRight = {
		{ 112.0f / 128.0f, 0.0f },
		{ 112.0f / 128.0f, 1.0f },
		{ 97.0f / 128.0f, 1.0f },
		{ 97.0f / 128.0f, 0.0f }
	};

	static float[][] texCoordLeft = {
		{ 33.0f / 128.0f, 0.0f },
		{ 48.0f / 128.0f, 0.0f },
		{ 48.0f / 128.0f, 1.0f },
		{ 33.0f / 128.0f, 1.0f }
	};

	static float[][] texCoordTop = {
		{ 30.0f / 128.0f, 0.0f },
		{ 30.0f / 128.0f, 1.0f },
		{ 15.0f / 128.0f, 1.0f },
		{ 15.0f / 128.0f, 0.0f }
	};

	static float[][] texCoordRight = {
		{ 47.0f / 128.0f, 0.0f },
		{ 62.0f / 128.0f, 0.0f },
		{ 62.0f / 128.0f, 1.0f },
		{ 47.0f / 128.0f, 1.0f }
	};

	static float[][] texCoordBottom = {
		{ 16.0f / 128.0f, 0.0f },
		{ 16.0f / 128.0f, 1.0f },
		{ 1.0f / 128.0f, 1.0f },
		{ 1.0f / 128.0f, 0.0f }
	};

	static float[][] texCoordsCaption = {
		{ 0, 0 },
		{ 136.0f / 256.0f, 0 },
		{ 136.0f / 256.0f, 20.0f / 32.0f },
		{ 0, 20.0f / 32.0f }
	};

	static float[][] texCoordClose = {
		{ 6.0f / 32.0f, 7.0f / 32.0f },
		{ 24.0f / 32.0f, 7.0f / 32.0f },
		{ 24.0f / 32.0f, 25.0f / 32.0f },
		{ 6.0f / 32.0f, 25.0f / 32.0f }
	};

	void setMetrics(Vector2 pos, Vector2 size) {
	 	mPosition = pos;
		mSize.x = Math.max(size.x, 32.0f);
		mSize.y = Math.max(size.y, 30.0f);

	}

	void initTexture() {
		mBorderTexture = TextureManager.getInstance().getTexture("Interface/Tooltips/UI-Tooltip-Border.blp");
		mBackgTexture = TextureManager.getInstance().getTexture("Interface/Tooltips/UI-Tooltip-Background.blp");
		mCaptionTexture = TextureManager.getInstance().getTexture("Interface/AuctionFrame/UI-AuctionFrame-FilterBg.blp");
		mCloseTex = TextureManager.getInstance().getTexture("Interface/Buttons/UI-Panel-MinimizeButton-Up.blp");
		mCloseTexDown = TextureManager.getInstance().getTexture("Interface/Buttons/UI-Panel-MinimizeButton-Down.blp");
		mCloseHighlight = TextureManager.getInstance().getTexture("Interface/Buttons/UI-Panel-MinimizeButton-Highlight.blp");

		mQuadBackground.setTexture(mBackgTexture);
		mQuadTopLeft.setTexture(mBorderTexture);
		mQuadTopLeft.setTexCoords(texCoordTopLeft);
		mQuadTopRight.setTexture(mBorderTexture);
		mQuadTopRight.setTexCoords(texCoordTopRight);
		mQuadBottomLeft.setTexture(mBorderTexture);
		mQuadBottomLeft.setTexCoords(texCoordBottomLeft);
		mQuadBottomRight.setTexture(mBorderTexture);
		mQuadBottomRight.setTexCoords(texCoordBottomRight);
		mQuadLeft.setTexture(mBorderTexture);
		mQuadLeft.setTexCoords(texCoordLeft);
		mQuadTop.setTexture(mBorderTexture);
		mQuadTop.setTexCoords(texCoordTop);
		mQuadRight.setTexture(mBorderTexture);
		mQuadRight.setTexCoords(texCoordRight);
		mQuadBottom.setTexture(mBorderTexture);
		mQuadBottom.setTexCoords(texCoordBottom);
		mCaptionQuad.setTexture(mCaptionTexture);
		mCaptionQuad.setTexCoords(texCoordsCaption);
		mCloseQuad.setTexture(mCloseTex);
		mCloseQuad.setTexCoords(texCoordClose);

		mQuadBackground.setMetrics(new Vector2(3, 3), new Vector2(mSize.x - 6, mSize.y - 6));
		mQuadTopLeft.setMetrics(new Vector2(0, 0), new Vector2(16, 15));
		mQuadTopRight.setMetrics(new Vector2(mSize.x - 16, 0), new Vector2(16, 15));
		mQuadBottomLeft.setMetrics(new Vector2(0, mSize.y - 15), new Vector2(16, 15));
		mQuadBottomRight.setMetrics(new Vector2(mSize.x - 16, mSize.y - 15), new Vector2(16, 15));
		mQuadLeft.setMetrics(new Vector2(1, 15), new Vector2(15, mSize.y - 30));
		mQuadTop.setMetrics(new Vector2(16, 0), new Vector2(mSize.x - 32, 15));
		mQuadRight.setMetrics(new Vector2(mSize.x - 17, 15), new Vector2(15, mSize.y - 30));
		mQuadBottom.setMetrics(new Vector2(16, mSize.y - 15), new Vector2(mSize.x - 32, 15));
		mCaptionQuad.setMetrics(new Vector2(mSize.x / 2.0f - 68.0f, -5.0f), new Vector2(136.0f, 20.0f));
		mCloseQuad.setMetrics(new Vector2(mSize.x - 25.0f, 4), new Vector2(18.0f, 18.0f));

		mStencilQuad.setMetrics(getChildOffset(), Vector2.sub(mSize, new Vector2(16, 28)));
	}

	void handleMouseDown(UITouchMessage msg) {
	  	Vector2 pos = msg.getPosition(msg.getActivePointerIndex());

		Vector2 capPos = new Vector2(mPosition.x + mSize.x / 2.0f - 68.0f, mPosition.y - 5.0f);
		Vector2 capSize = new Vector2(136.0f, 20.0f);

		if(pos.x >= mPosition.x && pos.y >= mPosition.y && pos.x <= mPosition.x + mSize.x && pos.y <= mPosition.y + mSize.y) {
			//msg.setHandled(true);
			if(getParent() != null) {
				getParent().bringToFront(this);
			}
		}

		if(pos.x >= capPos.x && pos.x <= capPos.x + capSize.x &&
				pos.y >= capPos.y && pos.y <= capPos.y + capSize.y) {
			mIsHeaderDown = true;
			mClickOffset = Vector2.sub(pos, mPosition);

			if(getParent() != null) {
				getParent().bringToFront(this);
			}

			//msg.setHandled(true);
		} else {
			mIsHeaderDown = false;
		}

		Vector2 posClose = new Vector2(mPosition.x + mSize.x - 25.0f, mPosition.y + 4.0f);
		mIsCloseDown = pos.x >= posClose.x && pos.x <= posClose.x + 18.0f && pos.y >= posClose.y && pos.y <= posClose.y + 18.0f;
		mIsCloseHovered = mIsCloseDown;
	}

	void handleMouseMove(UITouchMessage msg) {
	 	if(msg.getCursorCount() > 1) {
			return;
		}

		Vector2 pos = msg.getPosition(msg.getActivePointerIndex());

		if(mIsHeaderDown == true) {
			setMetrics(Vector2.sub(pos, mClickOffset), mSize);
		}

		Vector2 posClose = new Vector2(mPosition.x + mSize.x - 25.0f, mPosition.y + 4.0f);
		mIsCloseHovered = pos.x >= posClose.x && pos.x <= posClose.x + 18.0f && pos.y >= posClose.y && pos.y <= posClose.y + 18.0f;
	}

	void handleMouseUp(UITouchMessage msg) {
	  	mIsHeaderDown = false;
		if(mIsCloseHovered && mIsCloseDown) {
			//TODO: onClose
		}

		mIsCloseDown = false;
		mIsCloseHovered = false;

	}

	@Override
	protected void onItemsChanged() {

	}

	Vector2 getChildSize() {
		Vector2 curSize = new Vector2(0, 0);
		for(UIElement child : mChildList) {
			curSize.takeBigger(Vector2.add(child.getPosition(), child.getSize()));
		}

		return curSize;
	}

	@Override
	protected Vector2 getMessageOffset() {
		return new Vector2(0, 0);
	}

	@Override
	protected Vector2 getChildOffset() {
		return new Vector2(8, 20);
	}

	public Frame() {
	 	mIsCloseHovered = mIsCloseDown = false;
		mIsHeaderDown = false;
		mStateMask = 0xFFFFFFFF;
		setMetrics(new Vector2(0, 0), new Vector2(80, 78));
		initTexture();
		mScrollOffset = 0;
	}

	public Frame(Vector2 size) {
		mIsCloseHovered = mIsCloseDown = false;
		mIsHeaderDown = false;
		mStateMask = 0xFFFFFFFF;
		setMetrics(new Vector2(0, 0), size);
		initTexture();
		mScrollOffset = 0;
	}

	public Frame(Vector2 position, Vector2 size) {
		mIsCloseHovered = mIsCloseDown = false;
		mIsHeaderDown = false;
		mStateMask = 0xFFFFFFFF;
		setMetrics(position, size);
		initTexture();
		mScrollOffset = 0;
	}

	public void setPosition(Vector2 pos) {
		setMetrics(pos, mSize);
	}

	public void setSize(Vector2 size) {
		setMetrics(mPosition, size);
	}

	@Override
	public void draw() {
		UIManager.getInstance().getMatrixStack().push(Matrix.Translation(mPosition.x, mPosition.y, 0.0f));

		if(mIsCloseDown == true) {
			mCloseQuad.setTexture(mCloseTexDown);
		} else {
			mCloseQuad.setTexture(mCloseTex);
		}

		mQuadBackground.draw();
		mQuadTopLeft.draw();
		mQuadTopRight.draw();
		mQuadBottomLeft.draw();
		mQuadBottomRight.draw();
		mQuadTop.draw();
		mQuadLeft.draw();
		mQuadBottom.draw();
		mQuadRight.draw();
		mCaptionQuad.draw();
		mCloseQuad.draw();

		GLES20.glClear(GLES20.GL_STENCIL_BUFFER_BIT);
		GLES20.glEnable(GLES20.GL_STENCIL_TEST);
		GLES20.glColorMask(false, false, false, false);
		GLES20.glStencilFunc(GLES20.GL_ALWAYS, 1, 1);
		GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_REPLACE);

		mStencilQuad.draw();

		GLES20.glColorMask(true, true, true, true);
		GLES20.glStencilFunc(GLES20.GL_EQUAL, 1, 1);
		GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_KEEP);

		UIManager.getInstance().getMatrixStack().pop();

		renderChildren();

		GLES20.glDisable(GLES20.GL_STENCIL_TEST);
	}

	@Override
	public void handleMessage(UIMessage message) {
		if(message.isHandled() == true) {
			if(message.getMessageType() == MessageKind.Touch) {
				UITouchMessage uiMsg = (UITouchMessage)message.forwardMessage(getChildOffset());
				switch(uiMsg.getType()) {
					case TouchDown:
						uiMsg.setType(MessageType.NCTouchDown);
						break;

					case TouchUp:
						uiMsg.setType(MessageType.NCTouchUp);
						break;

					case TouchMove:
						uiMsg.setType(MessageType.NCTouchMove);
						break;
				}

				forwardMessage(uiMsg);
			}

			return;
		}

		if(message.getMessageType() == MessageKind.Touch) {
			UITouchMessage msg = (UITouchMessage)message;
			switch(msg.getType()) {
				case TouchMove:
					handleMouseMove(msg);
					break;

				case TouchDown:
					handleMouseDown(msg);
					break;

				case TouchUp:
					handleMouseUp(msg);
					break;
			}
		}

		if(message.getMessageType() == MessageKind.Touch) {
			/*switch(uiMsg.getType()) {
				case TouchDown:
					uiMsg.setType(MessageType.NCTouchDown);
					break;

				case TouchUp:
					uiMsg.setType(MessageType.NCTouchUp);
					break;

				case TouchMove:
					uiMsg.setType(MessageType.NCTouchMove);
					break;
			}*/

			forwardMessage(message);
		}
	}

	@Override
	public boolean isUnder(Vector2 pos) {
		if(pos.x >= mPosition.x && pos.x <= mPosition.x + mSize.x && pos.y >= mPosition.y && pos.y <= mPosition.y + mSize.y)
			return true;

		if(pos.x > mPosition.x + mSize.x / 2.0f - 68.0f && pos.x <= mPosition.x + mSize.x / 2.0f - 68.0f + 136.0f) {
			if(pos.y >= mPosition.y - 5.0f && pos.y <= mPosition.y + 15.0f)
				return true;
		}

		return false;
	}
}
