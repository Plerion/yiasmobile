package ch.cromon.YiasMobile.UI.elements;

import ch.cromon.YiasMobile.UI.graphics.Texture;
import ch.cromon.YiasMobile.UI.graphics.TextureManager;
import ch.cromon.YiasMobile.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 18:49
 */
public class Button {
	private UIQuad mQuad;
	private Texture mTexUp, mTexDown, mTexHover, mTexDisabled;

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
		mQuad = new UIQuad(position, large ? new Vector2(128, 22) : new Vector2(78, 21));

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
	}

	public void draw() {
		mQuad.setTexture(mTexUp);
		mQuad.draw();
	}
}
