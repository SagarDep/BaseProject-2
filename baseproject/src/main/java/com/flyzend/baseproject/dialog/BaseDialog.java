package com.flyzend.baseproject.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by 王灿 on 2017/2/27.
 */

public class BaseDialog {
    private Context mContext;

    public BaseDialog(Context context) {
        mContext = context;
    }

    public void showDialog(String title, String message, DialogInterface.OnClickListener listener) {
        showDialog(title, message, "确定", "取消", listener, null);
    }

    public void showDialog(String title, String message, String positiveBtnText, String negativeBtnText
            , DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtnText, positiveListener)
                .setNegativeButton(negativeBtnText, negativeListener)
                .create().show();
    }

    public void showOneBtnDialog(String title, String message, DialogInterface.OnClickListener listener) {
        showOneBtnDialog(title, message, "确定", listener);
    }

    public void showOneBtnDialog(String title, String message, String btnText
            , DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(btnText, listener)
                .create().show();
    }
}
