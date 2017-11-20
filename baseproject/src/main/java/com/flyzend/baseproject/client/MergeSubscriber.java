package com.flyzend.baseproject.client;

import android.content.Context;

/**
 * Created by 王灿 on 2017/11/20.
 */

public abstract class MergeSubscriber extends BaseSubscriber<Object>{
    public MergeSubscriber(Context context, String testTag, String loadText, boolean isShowDialog) {
        super(context, testTag,loadText, isShowDialog);
    }

    public MergeSubscriber(Context context, String testTag, boolean isShowDialog) {
        super(context, testTag,isShowDialog);
    }

    public MergeSubscriber(Context context, String testTag) {
        super(context, testTag);
    }
}
