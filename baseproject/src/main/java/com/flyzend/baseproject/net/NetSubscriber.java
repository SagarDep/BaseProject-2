package com.flyzend.baseproject.net;

import android.content.Context;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by 王灿 on 2017/12/20.
 */

public abstract class NetSubscriber extends AbstractBaseNetSubscriber<ResponseBody, String> {
    public NetSubscriber(Context context, String interfaceTag, NetConfig netConfig) {
        super(context, interfaceTag, netConfig);
    }

    public NetSubscriber(Context context, String interfaceTag) {
        super(context, interfaceTag);
    }

    @Override
    protected abstract void onSuccess(String s);

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String s = responseBody.string();
            //执行具体的解析数据逻辑
            onSuccess(s);
        } catch (IOException e) {
            e.printStackTrace();
            onError(e);
        }
    }
}
