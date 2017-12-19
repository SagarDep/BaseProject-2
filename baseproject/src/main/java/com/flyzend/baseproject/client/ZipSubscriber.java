package com.flyzend.baseproject.client;

import android.content.Context;

/**
 * Created by 王灿 on 2017/8/5.
 */

public abstract class ZipSubscriber extends BaseSubscriber<BaseZipBean> {
    public ZipSubscriber(Context context, String testTag, String loadText, boolean isShowDialog, boolean isShowErrorToast) {
        super(context, testTag, loadText, isShowDialog, isShowErrorToast);
    }

    public ZipSubscriber(Context context, String testTag, boolean isShowDialog) {
        super(context, testTag, isShowDialog);
    }

    public ZipSubscriber(Context context, String testTag, boolean isShowDialog, boolean isShowErrorToast) {
        super(context, testTag, isShowDialog, isShowErrorToast);
    }

    public ZipSubscriber(Context context, String testTag) {
        super(context, testTag);
    }
}
