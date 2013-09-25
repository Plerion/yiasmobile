package ch.cromon.YiasMobile.scene;

import ch.cromon.YiasMobile.math.Matrix;
import ch.cromon.YiasMobile.math.Vector3;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Cromon
 * Date: 24.09.13
 * Time: 23:38
 * To change this template use File | Settings | File Templates.
 */
public class Camera {
	public interface MatrixChangedEventListener
	{
		public void matrixChanged(Camera camera, boolean view, Matrix matrix);
	}

	protected Matrix mView, mProjection;
	protected Vector3 mPosition, mUp, mForward, mRight;

	private ArrayList<MatrixChangedEventListener> mEventListeners = new ArrayList<MatrixChangedEventListener>();

	protected Camera()
	{
		mPosition = new Vector3(0, 0, 0);
		mForward = new Vector3(1, 0, 0);
		mUp = new Vector3(0, 0, 1);
		mRight = new Vector3(0, -1, 0);

		mView = Matrix.LookAt(mPosition, Vector3.add(mPosition, mForward), mUp);
		viewChanged();
	}

	protected void viewChanged() {
		for(MatrixChangedEventListener e : mEventListeners) {
			e.matrixChanged(this, true, mView);
		}
	}

	protected void projChanged() {
		for(MatrixChangedEventListener e : mEventListeners) {
			e.matrixChanged(this, false, mProjection);
		}
	}

	public void addEventListener(MatrixChangedEventListener listener) {
		mEventListeners.add(listener);
	}

	public void removeEventListener(MatrixChangedEventListener listener) {
		mEventListeners.remove(listener);
	}

	void setPosition(Vector3 pos) {
		mPosition = pos;

		mView = Matrix.LookAt(mPosition, Vector3.add(mPosition, mForward), mUp);
		viewChanged();
	}

	void setTarget(Vector3 tar) {
		Vector3 dir = Vector3.sub(tar, mPosition);
		dir.normalize();
		mForward = dir;

		mView = Matrix.LookAt(mPosition, Vector3.add(mPosition, mForward), mUp);
		viewChanged();
	}

	void moveUp(float amount) {
		move(new Vector3(0, 0, amount));
	}

	void moveForward(float amount) {
		move(Vector3.mul(mForward, amount));
	}

	void moveSide(float amount) {
		move(Vector3.mul(mRight, amount));
	}

	void move(Vector3 direction) {
		mPosition = Vector3.add(mPosition, direction);

		mView = Matrix.LookAt(mPosition, Vector3.add(mPosition, mForward), mUp);
		viewChanged();
	}

	void pitch(float angle) {
		Matrix matRot = Matrix.Rotation(angle, mRight.x, mRight.y, mRight.z);
		mForward = Vector3.transformCoordinate(mForward, matRot);
		mForward.normalize();
		mUp = Vector3.transformCoordinate(mUp, matRot);
		mUp.normalize();

		mView = Matrix.LookAt(mPosition, Vector3.add(mPosition, mForward), mUp);
		viewChanged();
	}

	void yaw(float angle) {
		Matrix matRot = Matrix.Rotation(angle, 0, 0, 1);
		mForward = Vector3.transformCoordinate(mForward, matRot);
		mForward.normalize();
		mUp = Vector3.transformCoordinate(mUp, matRot);
		mUp.normalize();
		mRight = Vector3.transformCoordinate(mRight, matRot);
		mRight.normalize();

		mView = Matrix.LookAt(mPosition, Vector3.add(mPosition, mForward), mUp);
		viewChanged();
	}

	void roll(float angle) {
		Matrix matRot = Matrix.Rotation(angle, mForward.x, mForward.y, mForward.z);
		mUp = Vector3.transformCoordinate(mUp, matRot);
		mUp.normalize();
		mRight = Vector3.transformCoordinate(mRight, matRot);
		mRight.normalize();

		mView = Matrix.LookAt(mPosition, Vector3.add(mPosition, mForward), mUp);
		viewChanged();
	}

	Matrix getProjection() {
		return mProjection;
	}

	Matrix getView() {
		return mView;
	}
}
