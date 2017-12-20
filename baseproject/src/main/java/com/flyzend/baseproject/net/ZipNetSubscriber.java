package com.flyzend.baseproject.net;

import android.content.Context;
import android.support.annotation.NonNull;

import com.flyzend.baseproject.client.BaseZipBean;

/**
 * Created by 王灿 on 2017/12/20.
 */

public abstract class ZipNetSubscriber extends AbstractBaseNetSubscriber<BaseZipBean,BaseZipBean> {
    public ZipNetSubscriber(@NonNull Context context, @NonNull String interfaceTag, @NonNull NetConfig netConfig) {
        super(context, interfaceTag, netConfig);
    }

    public ZipNetSubscriber(@NonNull Context context, @NonNull String interfaceTag) {
        super(context, interfaceTag);
    }

    @Override
    public void onNext(BaseZipBean bean) {
        onSuccess(bean);
    }
}
