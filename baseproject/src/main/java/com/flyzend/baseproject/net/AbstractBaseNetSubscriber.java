package com.flyzend.baseproject.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.flyzend.baseproject.utils.BaseProjectConfig;
import com.flyzend.baseproject.utils.LogUtil;
import com.flyzend.baseproject.utils.ToastUtil;
import com.flyzend.baseproject.utils.Util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by 王灿 on 2017/12/20.
 */

public abstract class AbstractBaseNetSubscriber<T, R> implements Subscriber<T> {
    protected final String TAG = getClass().getSimpleName();

    //==============网络加载框==============
    final boolean isProgressBarCancelOutSide;
    final boolean isShowProgressBar;
    final boolean isShowErrorToast;
    final String progressBarText;
    final String interfaceTag;

    //==============UI相关==============
    Context context;
    ProgressDialog progressDialog;
    ToastUtil toast;

    public AbstractBaseNetSubscriber(@NonNull Context context, @NonNull String interfaceTag, @NonNull NetConfig netConfig) {
        //==============init config==============
        this.isProgressBarCancelOutSide = netConfig.isProgressBarCancelOutSide;
        this.isShowProgressBar = netConfig.isShowProgressBar;
        this.isShowErrorToast = netConfig.isShowErrorToast;
        this.progressBarText = netConfig.progressBarText;

        this.interfaceTag = interfaceTag;

        //==============init UI==============
        this.context = context;
        this.toast = new ToastUtil(context);
    }

    public AbstractBaseNetSubscriber(@NonNull Context context, @NonNull String interfaceTag) {
        this(context, interfaceTag, new NetConfig());
    }

    @Override
    public void onError(Throwable t) {
        if (isShowProgressBar) {
            progressDialog.dismiss();
        }

        if (BaseProjectConfig.SHOW_NETWORK_ERROR_TOAST && isShowErrorToast) {
            toast.showToast(BaseProjectConfig.getErrorToastString());
        }

        if (t != null) {
            t.printStackTrace();
        }
        LogUtil.e(TAG, interfaceTag + ":onError--->>" + ((t == null || Util.isEmpty(t.getMessage())) ? "未知错误" : t.getMessage()));
        releaseUI();
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (isShowProgressBar) {
            progressDialog = ProgressDialog.show(context, null, progressBarText,
                    true, true);
            progressDialog.setCanceledOnTouchOutside(isProgressBarCancelOutSide);
        }
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onComplete() {
        if (isShowProgressBar) {
            progressDialog.dismiss();
        }
        releaseUI();
    }

    //请求回调
    protected abstract void onSuccess(R r);

    //==========释放UI资源，方便GC在适当的时候回收垃圾===========
    protected void releaseUI() {
        progressDialog = null;
        toast = null;
        context = null;
    }
}
