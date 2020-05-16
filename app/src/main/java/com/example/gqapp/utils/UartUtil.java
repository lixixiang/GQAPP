package com.example.gqapp.utils;

import android.content.Context;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/16 9:57
 * @Author: 李熙祥
 * @Description: java类作用描述 串口读写工具
 */
public class UartUtil {
    // NanoPC-T4 UART4
    static String devName = "/dev/ttyS4";
    private int speed = 38400;
    private int dataBits = 8;
    private int stopBits = 1;
    static int devfd = -1;
    private final int BUFSIZE = 512;
    private Context context;


}
