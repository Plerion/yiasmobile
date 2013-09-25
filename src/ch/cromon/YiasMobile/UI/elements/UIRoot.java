package ch.cromon.YiasMobile.UI.elements;

import ch.cromon.YiasMobile.UI.messaging.UIMessage;
import ch.cromon.YiasMobile.math.Vector2;

import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 21:41
 */
public class UIRoot extends ItemsControl {
	public UIRoot() {

	}

	@Override
	public void draw() {
	 	renderChildren();
	}

	@Override
	public void addChild(String name, UIElement elem) {
		int oldState = elem.getStateMask();
		super.addChild(name, elem);
		elem.setStateMask(oldState);
	}

	@Override
	public void addChild(UIElement elem) {
		int oldState = elem.getStateMask();
		super.addChild(elem);
		elem.setStateMask(oldState);
	}

	public void dispatchMessage(UIMessage message) {
		for(ListIterator<UIElement> itr = mChildList.listIterator(mChildList.size()); itr.hasPrevious(); ) {
			UIElement elem = itr.previous();
			elem.handleMessage(message);
		}
	}

	@Override
	public boolean isUnder(Vector2 pos) {
		for(UIElement elem : mChildList) {
			if(elem.isUnder(pos) == true) {
				return true;
			}
		}

		return false;
	}
}
