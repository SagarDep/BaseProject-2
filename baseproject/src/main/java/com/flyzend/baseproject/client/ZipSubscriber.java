package com.flyzend.baseproject.client;

import android.content.Context;

/**
 * Created by 王灿 on 2017/8/5.
 */

public abstract class ZipSubscriber extends BaseSubscriber<BaseCommentZipBean>{
    public ZipSubscriber(Context context, String testTag, String loadText, boolean isShowDialog) {
        super(context, testTag,loadText, isShowDialog);
    }

    public ZipSubscriber(Context context, String testTag, boolean isShowDialog) {
        super(context, testTag,isShowDialog);
    }

    public ZipSubscriber(Context context, String testTag) {
        super(context, testTag);
    }
}
