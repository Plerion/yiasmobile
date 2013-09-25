package ch.cromon.YiasMobile.UI.elements;

import ch.cromon.YiasMobile.UI.UIManager;
import ch.cromon.YiasMobile.UI.messaging.UIMessage;
import ch.cromon.YiasMobile.math.Matrix;
import ch.cromon.YiasMobile.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 20:14
 */
public abstract class ItemsControl extends UIElement {
	protected HashMap<String, UIElement> mNamedChildMap = new HashMap<String, UIElement>();
	protected ArrayList<UIElement> mChildList = new ArrayList<UIElement>();

	protected void renderChildren() {
		Vector2 childOfs = Vector2.add(getPosition(), getChildOffset());

		Matrix trans = Matrix.Translation(childOfs.x, childOfs.y, 0);
		UIManager.getInstance().getMatrixStack().push(trans);

		for(UIElement elem : mChildList) {
			if(elem.isVisible() == false) {
				continue;
			}

			elem.draw();
		}

		UIManager.getInstance().getMatrixStack().pop();
	}

	protected Vector2 getChildOffset() {
		return new Vector2(0, 0);
	}

	protected Vector2 getMessageOffset() {
		return new Vector2(0, 0);
	}

	protected void onItemsChanged() {

	}

	public void addChild(String name, UIElement elem) {
		elem.setStateMask(mStateMask);
		mNamedChildMap.put(name, elem);
		mChildList.add(elem);

		elem.setParent(this);
		onItemsChanged();
	}

	public void addChild(UIElement elem) {
	 	elem.setStateMask(mStateMask);
		mChildList.add(elem);
		elem.setParent(this);
		onItemsChanged();
	}

	public void removeChild(UIElement elem) {
	 	if(mChildList.contains(elem)) {
			mChildList.remove(elem);
			elem.setParent(null);
			onItemsChanged();
		}
	}

	public void removeChild(String name, UIElement elem) {
		if(mNamedChildMap.containsKey(name)) {
			mNamedChildMap.remove(name);
		}

		removeChild(elem);
	}

	public ArrayList<UIElement> getChildren() {
		return mChildList;
	}

	public void forwardMessage(UIMessage msg) {
		UIMessage newMsg = msg.forwardMessage(Vector2.add(Vector2.add(mPosition, getChildOffset()), getMessageOffset()));

		for(ListIterator itr = mChildList.listIterator(mChildList.size()); itr.hasPrevious(); ) {
			final UIElement elem = (UIElement)itr.previous();
			elem.handleMessage(newMsg);
		}
	}

	public abstract void draw();

	public void bringToFront(UIElement child) {
		if(mChildList.contains(child) == false) {
			return;
		}

		mChildList.remove(child);
		mChildList.add(child);
	}
}
