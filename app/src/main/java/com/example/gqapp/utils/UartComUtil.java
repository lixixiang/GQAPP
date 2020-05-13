package com.example.gqapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.friendlyarm.FriendlyThings.HardwareControler;

public class UartComUtil {

    // NanoPC-T4 UART4
    static String devName = "/dev/ttyS4";
    private int speed = 38400;
    private int dataBits = 8;
    private int stopBits = 1;
    static int devfd = -1;
    private final int BUFSIZE = 512;
    private Context context;

    public UartComUtil(Context context) {
        this.context = context;
        devfd = HardwareControler.openSerialPortEx((String) devName, (long) speed, (int) dataBits, (int) stopBits,"N","N");
    }

    public int UartComWrite(byte[] data) {
        /* 串口写数据 */
        int WriteNum = -1;
        if (devfd >= 0) {
            WriteNum = HardwareControler.write(devfd, data);
        }

        return WriteNum;
    }

    public String UartComRead(byte[] buf) {
        String str_ = "failed";
        if (devfd >= 0) {
            if (HardwareControler.select(devfd, 0, 0) == 1) {
                /* 串口读数据 */
                int ret = HardwareControler.read(devfd, buf, BUFSIZE);
                if (ret > 0) {
                    String t_str = new String(buf, 0, ret);
                    str_ = t_str;
                } else {
                    str_ = "read failed";
                }
            }
            Toast.makeText(context, "str_"+str_+"如果fd有数据可读，返回1, 如果没有数据可读，返回0，出错时返回-1。\n"+HardwareControler.select(devfd, 0, 0)+"", Toast.LENGTH_LONG).show();
        }
        /* 将收到的数据写入buf，并转成string */
        return str_;
    }
}




