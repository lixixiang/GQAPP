package com.example.gqapp.bean;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/4/27 10:20
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class CommonBean {
        private int isPage;
        private float offset;
        private boolean isOpen;
        private int currentPos;

    public CommonBean(int isPage, float offset, boolean isOpen,int currentPos) {
        this.isPage = isPage;
        this.offset = offset;
        this.isOpen = isOpen;
        this.currentPos = currentPos;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getIsPage() {
        return isPage;
    }

    public void setIsPage(int isPage) {
        this.isPage = isPage;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }
}
