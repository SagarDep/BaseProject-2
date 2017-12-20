package com.flyzend.baseproject.net;

/**
 * Created by 王灿 on 2017/12/20.
 */

public class NetConfig {
    //========网络加载框========
    //点击空白区域是否消失网络加载对话框
    boolean isProgressBarCancelOutSide;
    //是否显示网络加载框
    boolean isShowProgressBar;
    //是否显示网络异常toast
    boolean isShowErrorToast;
    //网络加载框文字内容
    String progressBarText;

    public NetConfig() {
        //==========默认值============
        this.isProgressBarCancelOutSide = false;
        this.isShowProgressBar = true;
        this.isShowErrorToast = true;
        this.progressBarText = "正在努力加载中...";
    }

    public NetConfig progressBarCancelOutSide(boolean progressBarCancelOutSide) {
        isProgressBarCancelOutSide = progressBarCancelOutSide;
        return this;
    }

    public NetConfig showProgressBar(boolean showProgressBar) {
        isShowProgressBar = showProgressBar;
        return this;
    }

    public NetConfig showErrorToast(boolean showErrorToast) {
        isShowErrorToast = showErrorToast;
        return this;
    }

    public NetConfig progressBarText(String progressBarText) {
        this.progressBarText = progressBarText;
        return this;
    }
}
