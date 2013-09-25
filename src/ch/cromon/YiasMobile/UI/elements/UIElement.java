package ch.cromon.YiasMobile.UI.elements;

import ch.cromon.YiasMobile.UI.messaging.UIMessage;
import ch.cromon.YiasMobile.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 20:09
 */
public abstract class UIElement {
	protected int mStateMask;
	protected Vector2 mPosition = new Vector2(0, 0);
	protected Vector2 mSize = new Vector2(0, 0);
	protected ItemsControl mParent;

	public UIElement() {
		mStateMask = 0xFFFFFFFF;
	}

	public boolean isVisible() {
		return true;
	}

	public abstract void draw();

	public void handleMessage(UIMessage message) {

	}

	public Vector2 getPosition() {
		return mPosition;
	}

	public Vector2 getSize() {
		return mSize;
	}

	public void setStateMask(int mask) {
		mStateMask = mask;
	}

	public int getStateMask() {
		return mStateMask;
	}

	public boolean isInState(int state) {
		return (mStateMask & state) != 0;
	}

	public boolean isUnder(Vector2 pos) {
		return false;
	}

	public void setParent(ItemsControl parent) {
		mParent = parent;
	}

	public ItemsControl getParent() {
		return mParent;
	}

	public Vector2 getScreenPosition() {
		ItemsControl parent = getParent();

		Vector2 pos = getPosition();
		while(parent != null) {
			pos = Vector2.add(pos, parent.getPosition());
			parent = parent.getParent();
		}

		return pos;
	}
}
