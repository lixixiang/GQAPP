package com.example.gqapp.utils.uart;

import android.content.Context;

import com.example.gqapp.app.Constance;
import com.example.gqapp.bean.EventBean;
import com.example.gqapp.utils.EventBusUtil;
import com.friendlyarm.FriendlyThings.HardwareControler;

public class UartComUtil {

    // NanoPC-T4 UART4
    static String devName = "/dev/ttyS4";
    private int speed = 38400;
    private int dataBits = 8;
    private int stopBits = 1;
    static int devfd = -1;
    private final int BUFSIZE = 8;
    private int RX_BUFSIZE = 14;
    private Context context;

    private int m_FrameHead = 0x55;
    private int m_LeftID = 0x02;
    private int PAD_ACCBtnSt = 0;
    private int PAD_ICM_ViewSt = 1;
    private int m_Alivecount = 0;
    private int m_Checksum = 0;
    private int m_FrameTail = 0xFF;

    private byte[] m_LeftFrame = new byte[9];

    private int m_TriggerEventID = 0;

    // 定周期接收的变量

    // 触发式接收的变量


    public UartComUtil(Context context) {
        this.context = context;
//        devfd = HardwareControler.openSerialPortEx((String) devName, (long) speed, (int) dataBits, (int) stopBits,"N","N");
    }

    public UartComUtil() {
//        devfd = HardwareControler.openSerialPortEx((String) devName, (long) speed, (int) dataBits, (int) stopBits,"N","N");
    }

    public void setPAD_ACCBtnSt( int data )
    {
        PAD_ACCBtnSt = (PAD_ACCBtnSt == 1) ? 0 : 1;
        return;
    }

    public static int write(byte[] data) {
        /* 串口写数据 */
        int WriteNum = -1;
        if (devfd >= 0) {
            WriteNum = HardwareControler.write(devfd, data);
        }

        return WriteNum;
    }

    public String read(byte[] buf) {
        String str_ = "failed";
        String restr_ = "";
        int checksum = 0;
        if (devfd >= 0) {
            if (HardwareControler.select(devfd, 0, 0) == 1) {
                /* 串口读数据 */
                int ret = HardwareControler.read(devfd, buf, RX_BUFSIZE);

                if (ret > 0) {
                    String t_str = new String(buf, 0, ret);
                    str_ = t_str;

                } else {
                    str_ = "failed";
                }
            }
            //Toast.makeText(context, "str_"+str_+"如果fd有数据可读，返回1, 如果没有数据可读，返回0，出错时返回-1。\n"+HardwareControler.select(devfd, 0, 0)+"", Toast.LENGTH_LONG).show();
        }
        /* 将收到的数据写入buf，并转成string */
        return str_;
    }

}




