package com.flyzend.baseproject.net;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * 功能描述： 订阅请求携带TAG返回，多次请求将interfaceTag返回，协助客户端判断请求是否有效。
 * *******  客户端对interfaceTag进行有效性的判断，以确定是否需要这条请求的返回结果
 * *******  TAG原则上保留原有TAG功能，增加一个验证的字符串，如TAG1234,或者TAG1235
 * *******  客户端如果短时间发出上面两个请求，最终确认只需要TAG1235 ，则在返回中先进行TAG的验证，如果TAG是1235则填充数据。
 * 作者信息： Flyzend 王灿
 * 开源地址： https://github.com/flyzend
 * 创建时间： 2018/7/20 17:04
 */
public abstract class TagSubsucriber extends AbstractBaseNetSubscriber<ResponseBody, String> {
    public TagSubsucriber(@NonNull Context context, @NonNull String interfaceTag, @NonNull NetConfig netConfig) {
        super(context, interfaceTag, netConfig);
    }

    public TagSubsucriber(@NonNull Context context, @NonNull String interfaceTag) {
        super(context, interfaceTag);
    }

    @Override
    protected void onSuccess(String s) {
        onSuccess(s, interfaceTag);
    }

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

    protected abstract void onSuccess(String json, String interfaceTag);
}
