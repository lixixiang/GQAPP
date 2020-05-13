package com.example.gqapp.utils;


import com.example.gqapp.bean.EventBean;

import org.greenrobot.eventbus.EventBus;

/**
 * created by lxx at 2019/7/11 20:40
 * 描述:
 */
public class EventBusUtil {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(EventBean event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(EventBean event) {
        EventBus.getDefault().postSticky(event);
    }
}

