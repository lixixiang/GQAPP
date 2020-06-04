package com.example.gqapp.bean;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/23 11:25
 * @Author: 李熙祥
 * @Description: java类作用描述 对话框触发类
 */
public class DialogBean {
    private  int type; //对话框弹出类型
    private int exit_phone; // 退出对话框



    public DialogBean(int type, int exit_phone) {
        this.type = type;
        this.exit_phone = exit_phone;
    }

    public DialogBean() {
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getExitPhone() {
        return exit_phone;
    }

    public void setExitPhone(int exit_phone) {
        this.exit_phone = exit_phone;
    }

}
