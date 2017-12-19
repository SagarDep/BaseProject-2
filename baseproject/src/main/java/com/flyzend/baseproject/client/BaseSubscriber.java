package com.flyzend.baseproject.client;

import android.app.ProgressDialog;
import android.content.Context;

import com.flyzend.baseproject.utils.BaseProjectConfig;
import com.flyzend.baseproject.utils.LogUtil;
import com.flyzend.baseproject.utils.ToastUtil;
import com.flyzend.baseproject.utils.Util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by Wangleiyong on 2017/7/25.
 */

abstract public class BaseSubscriber<T> implements Subscriber<T> {
    protected ToastUtil mToastUtil;
    private static final String TAG = "BaseSubscriber";
    //加载对话框
    protected ProgressDialog mLoadingDialog;
    private boolean mLoadDialogCanceledOutSide = false;
    //是否显示加载对话框
    protected boolean mIsShowDialog;
    protected Context mContext;
    protected String mLoadText;
    protected String mTestTag;
    protected boolean mIsShowErrorToast = true;

    public BaseSubscriber(Context context, String testTag, String loadText, boolean isShowDialog,boolean isShowErrorToast) {
        mContext = context;
        mTestTag = testTag;
        mLoadText = loadText;
        mIsShowDialog = isShowDialog;
        mIsShowErrorToast = isShowErrorToast;
        mToastUtil = new ToastUtil(mContext);
    }

    public BaseSubscriber(Context context, String testTag, boolean isShowDialog) {
        this(context,testTag,"",isShowDialog,true);
    }

    public BaseSubscriber(Context context, String testTag, boolean isShowDialog,boolean isShowErrorToast){
        this(context,testTag,"",isShowDialog,isShowErrorToast);
    }

    public BaseSubscriber(Context context, String testTag) {
        this(context,testTag,"",true,true);
    }

    protected void setLoadDialogCanceledOutSide(boolean canceledOutSide){
        mLoadDialogCanceledOutSide = canceledOutSide;
    }

    protected void showDialog() {
        if (mLoadingDialog == null) {
            if (Util.isEmpty(mLoadText)) {
                mLoadText = "正在努力加载中...";
            }
            mLoadingDialog = ProgressDialog.show(mContext, null, mLoadText,
                    true, true);
            mLoadingDialog.setCanceledOnTouchOutside(mLoadDialogCanceledOutSide);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    protected void disMissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSubscribe(Subscription s) {
        //示对话框
        if (mIsShowDialog) {
            showDialog();
        }
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        doOnNext(t);
    }


    @Override
    public void onError(Throwable t) {
        //显示错误
        if (mIsShowDialog) {
            disMissDialog();
        }

        if (BaseProjectConfig.SHOW_NETWORK_ERROR_TOAST && mIsShowErrorToast) {
            new ToastUtil(mContext).showToast(BaseProjectConfig.getErrorToastString());
        }

        if (t != null) {
            t.printStackTrace();
        }
        LogUtil.e(TAG, mTestTag + ":onError--->>" + ((t == null || Util.isEmpty(t.getMessage())) ? "未知错误" : t.getMessage()));
    }

    @Override
    public void onComplete() {
        //消失对话框
        if (mIsShowDialog) {
            disMissDialog();
        }
    }

    //子类去实现具体的解析逻辑
    public abstract void doOnNext(T result);
}
