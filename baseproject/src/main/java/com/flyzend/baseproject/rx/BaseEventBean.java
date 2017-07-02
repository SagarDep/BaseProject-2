package com.flyzend.baseproject.rx;

/**
 * Created by 王灿 on 2017/6/28.
 * RxBus 事件Bean
 */

public class BaseEventBean {
    private int type;
    private Object event;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getEvent() {
        return event;
    }

    public BaseEventBean setEvent(Object event) {
        this.event = event;
        return this;
    }
}
