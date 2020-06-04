package com.example.gqapp.bean;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/27 23:01
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class ACACCean {
    private int isLeftRight; //区分左和右界面 0 左  1 右
    private int type;       //  1 2 3 4 共四个界面
    private boolean isOpen; //km


    public ACACCean(int isLeftRight, int type, boolean isOpen, boolean isOpen2) {
        this.isLeftRight = isLeftRight;
        this.type = type;
        this.isOpen = isOpen;
    }

    public ACACCean() {
    }

    public int getIsLeftRight() {
        return isLeftRight;
    }

    public void setIsLeftRight(int isLeftRight) {
        this.isLeftRight = isLeftRight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

}
