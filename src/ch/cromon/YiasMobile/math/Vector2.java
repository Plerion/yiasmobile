package ch.cromon.YiasMobile.math;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 25.09.13
 * Time: 18:15
 */
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;

public class Vector2 {
	public Vector2() {
		x = y = 0;
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2 clone() {
		return new Vector2(x, y);
	}

	public void takeBigger(Vector2 v) {
		x = Math.max(x, v.x);
		y = Math.max(y, v.y);
	}

	public static Vector2 FromMotionEvent(MotionEvent event, int pointer) {
		PointerCoords pc = new PointerCoords();
		event.getPointerCoords(pointer, pc);

		return new Vector2(pc.x, pc.y);
	}

	public static Vector2 add(Vector2 v1, Vector2 v2) {
		Vector2 ret = new Vector2();
		ret.x = v1.x + v2.x;
		ret.y = v1.y + v2.y;

		return ret;
	}

	public static Vector2 sub(Vector2 v1, Vector2 v2) {
		Vector2 ret = new Vector2();
		ret.x = v1.x - v2.x;
		ret.y = v1.y - v2.y;

		return ret;
	}

	public static Vector2 mul(Vector2 v1, Vector2 v2) {
		Vector2 ret = new Vector2();
		ret.x = v1.x * v2.x;
		ret.y = v1.y * v2.y;

		return ret;
	}

	public static Vector2 mul(Vector2 v1, float val) {
		Vector2 ret = new Vector2();
		ret.x = v1.x * val;
		ret.y = v1.y * val;

		return ret;
	}

	public static Vector2 div(Vector2 v1, float val) {
		Vector2 ret = new Vector2();
		ret.x = v1.x / val;
		ret.y = v1.y / val;

		return ret;
	}

	public float x, y;
}
