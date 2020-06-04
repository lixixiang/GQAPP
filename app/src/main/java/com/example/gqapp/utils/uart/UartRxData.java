package com.example.gqapp.utils.uart;


/**
 * 读取数据
 */
public class UartRxData {

    public static byte[] m_UartRxData = new byte[14];
    private byte m_FrameHead = (byte)0x55;
    private byte m_FrameTail = (byte)0xFF;
    private int m_CycleFrameID = 0x03;
    private int m_TriggerFrameID = 0x04;

    private byte[] m_ReturnResult = new byte[7];//byte[0] : 0x00:周期帧，0x01 - 0x09:触发帧
                                                //byte[1] : 触发帧的数据
    private UartComUtil m_Uart;


    /* 收信的内容均为车体端发送的实时数据，pad端需要对此做出相应动作 */
    //定周期接收的变量，设定为收信一秒后，对pad里内容改变
    public static int HVAC_WindExitSpd = 0x00; //风量等级
                                        // 0，1，2，3，4，5，6，7
    public static int HVAC_DriverTempSelect = 0x00;//主驾温度
                                            //0x00:18℃，0x01:18.5℃，0x02:18℃ ...... 0x1C:32℃
    public static int ACU_CurrentVol = 0x00;//音量状态
                                     // 0x00(代表0%，之后每增加1%，数值增加0x01)
                                     // 0x01(代表音量调节到1%)，0x64（代表音量调节到100%）
    public static int ACU_CurrentSourceNo = 0x00; //音源
                                           //0，1，2，3 ...... 50
    public static int ACU_HeadrestCurrentValue = 0x00; //头枕音效状态
                                                // 0x00(代表0%，之后每增加1%，数值增加0x01)
                                                // 0x01(代表音量调节到1%)，0x64（代表音量调节到100%）
    public static int ACU_SounedSurroundCurrentValue = 0x00;//环绕效果音效状态
                                                     // 0x00(代表0%，之后每增加1%，数值增加0x01)
                                                     // 0x01(代表音量调节到1%)，0x64（代表音量调节到100%）
    public static int HVAC_ACSt = 0x00; //空调开启关闭状态
                                 //0，1
    public static int ACU_CurrentplaySt  = 0x00; //播放暂停状态
                                          //0，1

    //触发式接收的变量
    public static byte TriggerEventID = 0x00;
    public static int TriggerValue = 0x00; //0x00: 无触发事件，为定周期接收;
                                      //0x01: 钥匙状态信号 BCM_KeySt              发生改变
                                      //0x02: 场景模式信号 ACU_CockpitMode        发生改变
                                      //0x03: 文字传输类型 ACU_CharacterTransPCI  发生改变
                                      //0x04: 文字数据     ACU_CharacterTransData 发生改变
                                      //0x05: 电话状态信号 ACU_PhoneSt            发生改变
                                      //0x06: 电话类型     ACU_PhoneType          发生改变
                                      //0x07: 左前门状态   FL_Door_Status         发生改变
                                      //0x08: 仪表报警信号 ICM_PAD_PopupSt        发生改变
                                      //0x09: 消息推送信号 ICM_PAD_PromptSt       发生改变
    public static int FL_Door_Status = 0x00; //左前电吸门状态
                                      //0，1
    public static int ACU_PhoneType = 0x00;//电话类型
                                    //0: not active
                                    //1: BT phone
                                    //2: E-Call
                                    //3: B-Call
    public static int ACU_PhoneSt = 0x00;//电话状态信号
                                  //0：not active
                                  //1: Incoming telegram
                                  //2: Connecting
                                  //3: On the phone
                                  //4: call over
                                  //5: Miss call
    public static int ICM_PAD_PopupSt = 0x00;//仪表报警信号
                                      //0：not active
                                      //1: active
    public static int ICM_PAD_PromptSt = 0x00;//消息推送信号
                                       //0：not active
                                       //1: active
    public static int ACU_CockpitMode = 0x00; //场景模式信号
                                       //0：normal
                                       //1: Health mode
                                       //2: Karaoke mode
                                       //3: Rear row mode
                                       //4: Cinema mode
                                       //5: Nap mode
    public static int ACU_CharacterTransPCI_0 = 0x00;//文字传输类型 ！！！暂定不用
    public static int ACU_CharacterTransPCI_1 = 0x00;
    public static int ACU_CharacterTransData_0 = 0x00;//文字数据（包含电话号码） ！！！暂定不用
    public static int ACU_CharacterTransData_1 = 0x00;
    public static int ACU_CharacterTransData_2 = 0x00;
    public static int ACU_CharacterTransData_3 = 0x00;
    public static int ACU_CharacterTransData_4 = 0x00;
    public static int ACU_CharacterTransData_5 = 0x00;
    public static int BCM_KeySt = 0x00;//钥匙状态信号
                                //0：off
                                //1: Acc
                                //2: On

    public UartRxData(){
        m_Uart = new UartComUtil();
    }

    private void UartRxDataParse(){

        int CycleChecksum = 0;
        int TriggerChecksum = 0;

        if( m_Uart.read(m_UartRxData) != "failed" ) {
            //定周期的帧的判断
            if ((m_UartRxData[1] >> 4) == m_CycleFrameID) {
                if ((m_UartRxData[0] == m_FrameHead) && (m_UartRxData[13] == m_FrameTail)) {
                    CycleChecksum = (int) ((0x0F) & ((m_UartRxData[1] >> 4) ^ (m_UartRxData[1] >> 0)
                            ^ (m_UartRxData[2] >> 4) ^ (m_UartRxData[2] >> 0)
                            ^ (m_UartRxData[3] >> 4) ^ (m_UartRxData[3] >> 0)
                            ^ (m_UartRxData[4] >> 4) ^ (m_UartRxData[4] >> 0)
                            ^ (m_UartRxData[5] >> 4) ^ (m_UartRxData[5] >> 0)
                            ^ (m_UartRxData[6] >> 4) ^ (m_UartRxData[6] >> 0)
                            ^ (m_UartRxData[7] >> 4) ^ (m_UartRxData[7] >> 0)
                            ^ (m_UartRxData[8] >> 4) ^ (m_UartRxData[8] >> 0)
                            ^ (m_UartRxData[9] >> 4) ^ (m_UartRxData[9] >> 0)
                            ^ (m_UartRxData[10] >> 4) ^ (m_UartRxData[10] >> 0)
                            ^ (m_UartRxData[11] >> 4) ^ (m_UartRxData[11] >> 0)
                            ^ (m_UartRxData[12] >> 4)));
                    if (CycleChecksum == (m_UartRxData[12] & 0x0F)) {
                        TriggerEventID = 0x00;
                        HVAC_WindExitSpd = m_UartRxData[1] & 0x0F;
                        HVAC_DriverTempSelect = (m_UartRxData[2] & 0xF8) >> 3;
                        ACU_CurrentVol = ((m_UartRxData[2] & 0x07) << 4) | ((m_UartRxData[3] & 0xF0) >> 4);
                        ACU_CurrentSourceNo = ((m_UartRxData[3] & 0x0F) << 2) | ((m_UartRxData[4] & 0xC0) >> 6);
                        ACU_HeadrestCurrentValue = ((m_UartRxData[4] & 0x3F) << 1) | ((m_UartRxData[5] & 0x80) >> 7);
                        ACU_SounedSurroundCurrentValue = m_UartRxData[5] & 0x7F;
                        HVAC_ACSt = (m_UartRxData[6] & 0x80) >> 7;
                        UartTxData.TemSetIfAcIsOpen = HVAC_ACSt;
                        /*
                        if( (HVAC_ACSt == 0x01) ){
                            TriggerEventID = 0x16; //空调功能打开，画面变彩
                        }
                        if( (HVAC_ACSt == 0x00) ){
                            TriggerEventID = 0x17; //空调功能关闭，画面变灰
                        }
                        */
                        ACU_CurrentplaySt = (m_UartRxData[6] & 0x40) >> 6;
                    }

                }
            }

            //触发式的帧的判断
            if ((m_UartRxData[1] >> 4) == m_TriggerFrameID) {
                if ((m_UartRxData[0] == m_FrameHead) && (m_UartRxData[13] == m_FrameTail)) {

                    TriggerChecksum = (byte) ((0x0F) & ((m_UartRxData[1] >> 4) ^ (m_UartRxData[1] >> 0)
                            ^ (m_UartRxData[2] >> 4) ^ (m_UartRxData[2] >> 0)
                            ^ (m_UartRxData[3] >> 4) ^ (m_UartRxData[3] >> 0)
                            ^ (m_UartRxData[4] >> 4) ^ (m_UartRxData[4] >> 0)
                            ^ (m_UartRxData[5] >> 4) ^ (m_UartRxData[5] >> 0)
                            ^ (m_UartRxData[6] >> 4) ^ (m_UartRxData[6] >> 0)
                            ^ (m_UartRxData[7] >> 4) ^ (m_UartRxData[7] >> 0)
                            ^ (m_UartRxData[8] >> 4) ^ (m_UartRxData[8] >> 0)
                            ^ (m_UartRxData[9] >> 4) ^ (m_UartRxData[9] >> 0)
                            ^ (m_UartRxData[10] >> 4) ^ (m_UartRxData[10] >> 0)
                            ^ (m_UartRxData[11] >> 4) ^ (m_UartRxData[11] >> 0)
                            ^ (m_UartRxData[12] >> 4)));
                    if ((byte)TriggerChecksum == (byte)(m_UartRxData[12] & 0x0F)) {

                        TriggerValue = (int)(m_UartRxData[1] & 0x0F);
                        FL_Door_Status = (m_UartRxData[2] & 0x80) >> 7;
                        if( (FL_Door_Status == 0x01) ){
                            if(TriggerValue == 0x07) {
                                TriggerEventID = 0x1A; //弹出车门报警弹窗
                            }
                        }
                        if( (FL_Door_Status == 0x00) ){
                            if(TriggerValue == 0x07) {
                                TriggerEventID = 0x1B; //退出车门报警弹窗
                            }
                        }

                        ACU_PhoneType = (m_UartRxData[2] & 0x70) >> 4;
                        ACU_PhoneSt = m_UartRxData[2] & 0x0F;

                        if((ACU_PhoneType == 1) && (ACU_PhoneSt == 1)){
                            if((TriggerValue == 0x05) || (TriggerValue == 0x06)) {
                                TriggerEventID = (byte) 0x18; //弹出来电窗口]
                            }
                        }
                        if((ACU_PhoneType == 1) && ((ACU_PhoneSt == 4) || (ACU_PhoneSt == 5))){
                            if((TriggerValue == 0x05) || (TriggerValue == 0x06)) {
                                TriggerEventID = (byte) 0x19; //退出通话界面，显示来电之前的界面
                            }
                        }
                        ICM_PAD_PopupSt = (byte)((m_UartRxData[3] & 0x80) >> 7);
                        if( ICM_PAD_PopupSt == (0x01) ){
                            if(TriggerValue == 0x08) {
                                TriggerEventID = (byte) 0x1C; //弹出仪表报警弹窗
                            }
                        }
                        if( (ICM_PAD_PopupSt == 0x00) ){
                            if(TriggerValue == 0x08) {
                                TriggerEventID = (byte) 0x1D; //退出仪表报警弹窗
                            }
                        }

                        ICM_PAD_PromptSt = (m_UartRxData[3] & 0x40) >> 6;
                        if( (ICM_PAD_PromptSt == 0x01) ){
                            if(TriggerValue == 0x09) {
                                TriggerEventID = (byte) 0x1E; //弹出推送选择弹窗
                            }
                        }
                        if( (ICM_PAD_PromptSt == 0x00) ){
                            if(TriggerValue == 0x09) {
                                TriggerEventID = (byte) 0x1F; //退出推送选择弹窗
                            }
                        }

                        if((((m_UartRxData[3] & 0x0F) == 4) || ((m_UartRxData[3] & 0x0F) == 5)) && ((ACU_CockpitMode != 4) || (ACU_CockpitMode != 5))){
                            if(TriggerValue == 0x02) {
                                TriggerEventID = (byte) 0x14; //画面逐渐变暗，1s后完全变黑；
                            }
                        }
                        if((((m_UartRxData[3] & 0x0F) != 4) || ((m_UartRxData[3] & 0x0F) != 5)) && ((ACU_CockpitMode == 4) || (ACU_CockpitMode == 5))){
                            if(TriggerValue == 0x02) {
                                TriggerEventID = (byte) 0x15; //屏幕逐渐点亮，1s后完全点亮
                            }
                        }
                        ACU_CockpitMode = m_UartRxData[3] & 0x0F;
                        ACU_CharacterTransPCI_1 = m_UartRxData[4];
                        ACU_CharacterTransPCI_0 = m_UartRxData[5];
                        ACU_CharacterTransData_5 = m_UartRxData[6];
                        ACU_CharacterTransData_4 = m_UartRxData[7];
                        ACU_CharacterTransData_3 = m_UartRxData[8];
                        ACU_CharacterTransData_2 = m_UartRxData[9];
                        ACU_CharacterTransData_1 = m_UartRxData[10];
                        ACU_CharacterTransData_0 = m_UartRxData[11];

                        if( (((m_UartRxData[12] & 0xC0) >> 6) == 2) && (BCM_KeySt != 0x02) ){
                            if(TriggerValue == 0x01) {
                                TriggerEventID = (byte) 0x11; //有两个动作需要根据启动或关机两个状态判断
                                // 动作1：启动开机，播放开机动画
                                // 动作2：关机息屏中重新点亮
                            }
                        }
                        if( (((m_UartRxData[12] & 0xC0) >> 6) == 0x00) ){
                            if(TriggerValue == 0x01) {
                                TriggerEventID = (byte) 0x12; //启动开机中立刻黑屏 ；在启动中判断
                            }
                        }
                        if( (((m_UartRxData[12] & 0xC0) >> 6) != 2) && (BCM_KeySt == 0x02) ){
                            if(TriggerValue == 0x01) {
                                TriggerEventID = (byte) 0x13; //画面逐渐变暗，1s后完全变黑；
                            }
                        }
                        if( (((m_UartRxData[12] & 0xC0) >> 6) == 1) && (BCM_KeySt == 0) ) {
                            if(TriggerValue == 0x01) {
                                UartTxData.ViewSetIfNormal = 1;
                            }
                        }

                        BCM_KeySt = (m_UartRxData[12] & 0xC0) >> 6;
                    }
                }
            }
        }
    }


    //
    public void ReturnResult(byte[] buf){ // byte[0]     : 触发事件ID
                                          // byte[1]     : 0x00:周期帧，0x01 - 0x09:触发帧
                                          // byte[2] ... : 触发帧的数据
        UartRxDataParse();
        buf[0] = TriggerEventID;
        buf[1] = (byte)TriggerValue;
        switch(buf[1]){
            case 0x00:
                buf[2] = 0x00;
                break;

            case 0x01:
                buf[2] = (byte)BCM_KeySt;
                break;

            case 0x02:
                buf[2] = (byte)ACU_CockpitMode;
                break;

            case 0x03:
                buf[2] = (byte)ACU_CharacterTransPCI_1;
                buf[3] = (byte)ACU_CharacterTransPCI_0;
                break;

            case 0x04:
                buf[2] = (byte)ACU_CharacterTransData_5;
                buf[3] = (byte)ACU_CharacterTransData_4;
                buf[4] = (byte)ACU_CharacterTransData_3;
                buf[5] = (byte)ACU_CharacterTransData_2;
                buf[6] = (byte)ACU_CharacterTransData_1;
                buf[7] = (byte)ACU_CharacterTransData_0;
                break;

            case 0x05:
                buf[2] = (byte)ACU_PhoneSt;
                break;

            case 0x06:
                buf[2] = (byte)ACU_PhoneType;
                break;

            case 0x07:
                buf[2] = (byte)FL_Door_Status;
                break;

            case 0x08:
                buf[2] = (byte)ICM_PAD_PopupSt;
                break;

            case 0x09:
                buf[2] = (byte)ICM_PAD_PromptSt;
                break;

            default:
                break;
        }

        TriggerValue = 0x00;
        TriggerEventID = 0x00;
    }

}
