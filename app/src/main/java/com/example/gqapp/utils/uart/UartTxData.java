package com.example.gqapp.utils.uart;



//该类为app的UART送信，在其他类中修改此类的变量数值，
//在定时器送信中，调用此类的送信方法（有两个送信，右pad送信，左pad送信），暂定两个送信交替进行，使用一个定时器

import static com.example.gqapp.utils.uart.UartComUtil.write;

/**
 *  发送数据
 */
public class UartTxData {

    private byte[] m_RightPadFrame = new byte[9];
    private byte[] m_LeftPadFrame = new byte[9];

    private int m_FrameHead = 0x55;
    private int m_FrameTail = 0xFF;

    private boolean m_SendFlag = false;

    //no command代表没有用户操作时回复到的默认值
    //右pad变量，变量注释中的数值代表该变量只能是这些数值中的某个，第一个数值为初始值
    public int m_RightPadTxID = 0x01;

    public static int HCP_HVACF_ACBtnSt = 0x00; //空调开关按钮


    // 0 关，1 开
    public static int HCP_HVACF_ACBtnStVD = 0x00; //空调开关按钮
                                           // 0，1
    public static int PAD_HVACFR_DrTempSettingVD = 0x01; // 温度调节
                                                 // 0，1
    public static int PAD_HVACFR_WindSpeedSettingVD = 0x00;// 风量调节
                                                    // 0，1
    public static int PAD_AnswertheBulephoneBtSt = 0x00;// 出现来电话弹窗时，点击接听
                                                 // 0，1
    public static int PAD_ACUPlayBtnSt = 0x00;// 音乐播放按钮
                                       // 0，1
    public static int PAD_HVACFR_DrTempSetting = 0x1D;//温度调节
                                               // no command:0x1D
                                               //0x00(代表18℃，之后每增加0.5℃，数值增加0x01)
                                               //0x01(代表18.5℃),0x1C(代表32℃)
    public static int PAD_RefusetheBulephoneBtSt = 0x00;//出现来电话弹窗时，点击拒绝
                                                 // 0，1
    public static int PAD_HeadrestSetting = 0x65; // 头枕音量调节
                                           // no command:0x65
                                           // 0x00(代表0%，之后每增加1%，数值增加0x01)
                                           // 0x01(代表音量调节到1%)，0x64（代表音量调节到100%）
    public static int PAD_ACUVolSetting = 0x65; // 音乐音量调节
                                         // no command:0x65
                                         // 0x00(代表0%，之后每增加1%，数值增加0x01)
                                         // 0x01(代表音量调节到1%)，0x64（代表音量调节到100%）
    public static int PAD_HVACFR_WindSpeedSetting = 0x00;//风速调节
                                                  //0,1,2,3,4,5,6,7
    public static int PAD_SourceSetting = 0x00;// 播放的音乐的音源选择
                                        // 0，1，2，3 ...... 50
    public static int TemSetIfAcIsOpen = 0; // 在更改温度时，有 HVACF_ACSt= 0或1两种情况；
                                            //HVACF_ACSt=0时，更改温度，此变量为0；
                                            //HVACF_ACSt=1时，更改温度，此变量为1；
    public static int PAD_SoundSurroundSetting = 0x65; // 环绕音量调节
                                                // no command:0x65
                                                // 0x00(代表0%，之后每增加1%，数值增加0x01)
                                                // 0x01(代表音量调节到1%)，0x64（代表音量调节到100%）

    private int m_RightAliveCount = 0x00;
    private int m_RightPadChecksum = 0x00;

    //左pad变量
    private int m_LeftPadTxID = 0x02;

    public static int PAD_ACCBtnSt = 0x00;// ACC按钮
                                   // 0,1
    public static int PAD_ICM_ViewSt = 0x00; //驾驶模式选择
                                      //0：no command
                                      //1: 纯驾模式
                                      //2：导航模式
                                      //4：驾驶模式
    public static int PAD_FCWSt = 0x00; // FCW按钮
                                 //0,1
    public static int PAD_FLPDUCloseSt = 0x00;// 关门提示的弹窗上的按钮
                                       // 0,1
    public static int PAD_FCWLevelSetting = 0x00;// 时距调节 no command:0x00
                                          // 0,1,2,3
    public static int PAD_ACCspeedSetting = 0x0A;// 巡航速度调节
                                          // no command：0x0A，0x00(代表30km/h,之后每增加10km/h，数值加0x01),
                                          // 0x01(代表40km/h) ...... 0x09(代表120km/h)
    public static int PAD_ICM_PopupConBtnSt = 0x00;// 仪表报警弹窗按钮
                                            // 0,1
    public static int PAD_ICM_PromptConfirmBtnSt = 0x00; // 推送弹窗确认按钮
                                                  // 0,1
    public static int PAD_ICM_PromptCancelBtnSt = 0x00;// 推送弹窗取消按钮
                                                // 0,1
    public static int ViewSetIfNormal = 0x00; //调节view（驾驶模式）时，有两种情况：上电初始化和正常调节
                                              //上电初始化：当BCM_KeySt从OFF->ACC时，该数值为1
                                              //正常调节：该值为0

    private int m_LeftAliveCount = 0x00;
    private int m_LeftPadChecksum = 0x00;


    public UartTxData(){
    }

    //右pad送信
    private void BuildRightPadSendFrame(){
//        Log.d("右pad送信", "PAD_HVACFR_DrTempSetting" + "     " + PAD_HVACFR_DrTempSetting
//                + "  PAD_HVACFR_WindSpeedSetting  " + "    " + PAD_HVACFR_WindSpeedSetting);

//        Log.d("右pad送信MVM", "播放/暂停   " + PAD_ACUPlayBtnSt + "  音量设置 " + PAD_ACUVolSetting + "  音源切换  " + PAD_SourceSetting + "  环绕  " + PAD_SoundSurroundSetting + "  头枕  " + PAD_HeadrestSetting);

        m_RightPadFrame[0] = (byte)m_FrameHead;
        m_RightPadFrame[1] = (byte)((m_RightPadTxID << 4) | (HCP_HVACF_ACBtnSt << 3) | (HCP_HVACF_ACBtnStVD << 2)
                                  | (PAD_HVACFR_DrTempSettingVD << 1) | (PAD_HVACFR_WindSpeedSettingVD << 0));
        m_RightPadFrame[2] = (byte)((PAD_AnswertheBulephoneBtSt << 7) | (PAD_ACUPlayBtnSt << 6) | (PAD_HVACFR_DrTempSetting << 0));
        m_RightPadFrame[3] = (byte)((PAD_RefusetheBulephoneBtSt << 7) | (PAD_HeadrestSetting << 0));
        m_RightPadFrame[4] = (byte)((PAD_ACUVolSetting << 1) | (PAD_HVACFR_WindSpeedSetting >> 3));
        m_RightPadFrame[5] = (byte)((PAD_HVACFR_WindSpeedSetting  << 5) | (PAD_SourceSetting << 0));
        m_RightPadFrame[6] = (byte)((PAD_SoundSurroundSetting << 0) | (TemSetIfAcIsOpen << 7));
        m_RightPadFrame[7] = (byte)((m_RightAliveCount << 4) & 0xF0);
        m_RightPadChecksum = (int) (( 0x0F ) & ((m_RightPadFrame[1] >> 4) ^ (m_RightPadFrame[1] >> 0) ^ (m_RightPadFrame[2] >> 4) ^ (m_RightPadFrame[2] >> 0)
                                  ^ (m_RightPadFrame[3] >> 4) ^ (m_RightPadFrame[3] >> 0) ^ (m_RightPadFrame[4] >> 4) ^ (m_RightPadFrame[4] >> 0)
                                  ^ (m_RightPadFrame[5] >> 4) ^ (m_RightPadFrame[5] >> 0) ^ (m_RightPadFrame[6] >> 4) ^ (m_RightPadFrame[6] >> 0) ^ (m_RightPadFrame[7] >> 4)));
        m_RightPadFrame[7] = (byte)(m_RightPadFrame[7] | m_RightPadChecksum);
        m_RightPadFrame[8] = (byte)m_FrameTail;

    }

    //左pad送信
    private void BuildLeftPadSendFrame(){
//        Log.d("左pad送信", "PAD_ACCspeedSetting" + "     " + PAD_ACCspeedSetting
//                + "  PAD_FCWLevelSetting  " + "    " + PAD_FCWLevelSetting+"    "+"PAD_ACCBtnSt===="+PAD_ACCBtnSt+"===PAD_FCWSt==="+PAD_FCWSt);
//        Log.d("左pad送信模式","  PAD_ICM_ViewSt   "+PAD_ICM_ViewSt);

        m_LeftPadFrame[0] = (byte)m_FrameHead;
        m_LeftPadFrame[1] = (byte)((m_LeftPadTxID << 4) | (PAD_ACCBtnSt << 3) | ( PAD_ICM_ViewSt << 0));
        m_LeftPadFrame[2] = (byte)((PAD_FCWSt << 7) | (PAD_FLPDUCloseSt << 6) | (PAD_FCWLevelSetting  << 4) | (PAD_ACCspeedSetting << 0));
        m_LeftPadFrame[3] = (byte)((PAD_ICM_PopupConBtnSt << 7) | (PAD_ICM_PromptConfirmBtnSt << 6) | (PAD_ICM_PromptCancelBtnSt << 5) | (ViewSetIfNormal << 4));
        m_LeftPadFrame[4] = (byte)(0x00);
        m_LeftPadFrame[5] = (byte)(0x00);
        m_LeftPadFrame[6] = (byte)(0x00);
        m_LeftPadFrame[7] = (byte)((m_LeftAliveCount << 4) & 0xF0);
        m_LeftPadChecksum = (int) (( 0x0F ) & ((m_LeftPadFrame[1] >> 4) ^ (m_LeftPadFrame[1] >> 0) ^ (m_LeftPadFrame[2] >> 4) ^ (m_LeftPadFrame[2] >> 0)
                ^ (m_LeftPadFrame[3] >> 4) ^ (m_LeftPadFrame[3] >> 0) ^ (m_LeftPadFrame[4] >> 4) ^ (m_LeftPadFrame[4] >> 0)
                ^ (m_LeftPadFrame[5] >> 4) ^ (m_LeftPadFrame[5] >> 0) ^ (m_LeftPadFrame[6] >> 4) ^ (m_LeftPadFrame[6] >> 0) ^ (m_LeftPadFrame[7] >> 4)));
        m_LeftPadFrame[7] = (byte)(m_LeftPadFrame[7] | m_LeftPadChecksum);
        m_LeftPadFrame[8] = (byte)m_FrameTail;

    }

    public void UartSendFrame(){
        if(m_SendFlag == false){
            BuildRightPadSendFrame();
           write(m_RightPadFrame); //调用通信中的送信方法，右pad送信
            m_RightAliveCount ++;// 每送信一次，计数加一，（0，1，2，3，0，1，2，3......）
            if(m_RightAliveCount >= 4){m_RightAliveCount = 0;}
            m_SendFlag = true;

            //if(PAD_ACUPlayBtnSt == 1){PAD_ACUPlayBtnSt = 0;}
            //if(PAD_AnswertheBulephoneBtSt == 1){PAD_AnswertheBulephoneBtSt = 0;}
            //if(PAD_RefusetheBulephoneBtSt == 1){PAD_RefusetheBulephoneBtSt = 0;}
        }
        else {
            BuildLeftPadSendFrame();
            write(m_LeftPadFrame);//调用通信中的送信方法，左pad送信
            m_LeftAliveCount ++; // 每送信一次，计数加一，（0，1，2，3，0，1，2，3......）
            if(m_LeftAliveCount >= 4){m_LeftAliveCount = 0;}
            m_SendFlag = false;

            //if(PAD_ACCBtnSt == 1){PAD_ACCBtnSt = 0;}
            //if(PAD_FCWSt == 1){PAD_FCWSt = 0;}
            //if(PAD_FLPDUCloseSt == 1){PAD_FLPDUCloseSt = 0;}
            //if(PAD_ICM_PopupConBtnSt == 1){PAD_ICM_PopupConBtnSt = 0;}
            //if(PAD_ICM_PromptConfirmBtnSt == 1){PAD_ICM_PromptConfirmBtnSt = 0;}
            //if(PAD_ICM_PromptCancelBtnSt == 1){PAD_ICM_PromptCancelBtnSt = 0;}
            if(ViewSetIfNormal == 1){ViewSetIfNormal = 0;}
        }
    }


}
