package com.flyzend.baseproject.utils;

import android.content.Context;
import android.widget.Toast;

import static com.flyzend.baseproject.utils.Config.TOAST_GRAVITY;

public final class ToastUtil {
	private Context mContext;
	private Toast mToast;
	private int mGravity = 0;

	public int getGravity() {
		return mGravity;
	}

	public void setGravity(int gravity) {
		mGravity = gravity;
	}

	public ToastUtil(final Context context) {
		mContext = context;
	}

	public void showToast(final int stringId) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, mContext.getResources()
					.getString(stringId), Toast.LENGTH_SHORT);
			if (mGravity != 0){
				mToast.setGravity(mGravity,0,0);
			}
			else if (TOAST_GRAVITY != 0){
				mToast.setGravity(TOAST_GRAVITY,0,0);
			}
		} else {
			mToast.setText(stringId);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public void showToast(final String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
			if (mGravity != 0){
				mToast.setGravity(mGravity,0,0);
			}
			else if (TOAST_GRAVITY != 0){
				mToast.setGravity(TOAST_GRAVITY,0,0);
			}
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public void showToastLong(final String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
			if (mGravity != 0){
				mToast.setGravity(mGravity,0,0);
			}
			else if (TOAST_GRAVITY != 0){
				mToast.setGravity(TOAST_GRAVITY,0,0);
			}
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		mToast.show();
	}

	public void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}
}
