package com.flyzend.baseproject.client;


import android.content.Context;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * 订阅者的基类，封装对话框等操作
 * Created by 王灿 on 2016/12/20.
 */

public abstract class BaseSubscriber extends SimpleSubscriber<ResponseBody> {

    public BaseSubscriber(Context context, String testTag, boolean isShowDialog) {
        super(context, testTag, isShowDialog);
    }

    public BaseSubscriber(Context context, String testTag) {
        super(context, testTag);
    }

    public BaseSubscriber(String testTag, boolean isShowDialog) {
        super(testTag, isShowDialog);
    }

    public BaseSubscriber(String testTag, String loadText) {
        super(testTag, loadText);
    }

    public BaseSubscriber(String testTag) {
        super(testTag);
    }

    @Override
    public void doOnNext(ResponseBody result) {
        try {
            String s = result.string();
            //执行具体的解析数据逻辑
            doOnNext(s);
        } catch (IOException e) {
            e.printStackTrace();
            onError(e);
        }
    }

    //子类去实现具体的解析逻辑
    public abstract void doOnNext(String result);
}
