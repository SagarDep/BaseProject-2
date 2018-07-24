package com.flyzend.baseproject.net;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述： 请求管理
 * 作者信息： Flyzend 王灿
 * 开源地址： https://github.com/flyzend
 * 创建时间： 2018/7/23 17:06
 */
public enum RequestCheckUtil {
    I;
    private Map<String, String> requestMap = new HashMap<>();

    public String getRequest(String activityName) {
        return requestMap.get(activityName);
    }

    public void setRequest(String activityName, String requestTag) {
        requestMap.put(activityName, requestTag);
    }
}
