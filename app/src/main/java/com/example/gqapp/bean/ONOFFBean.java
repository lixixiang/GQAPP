package com.example.gqapp.bean;

/**
 * @ProjectName: GQAPP
 * @CreateDate: 2020/5/27 23:01
 * @Author: 李熙祥
 * @Description: java类作用描述
 */
public class ONOFFBean {
    private boolean isOpen; //km
    private boolean isOpen2; //dis
    private boolean isOpen3; //ac

    public ONOFFBean(boolean isOpen, boolean isOpen2, boolean isOpen3) {
        this.isOpen = isOpen;
        this.isOpen2 = isOpen2;
        this.isOpen3 = isOpen3;
    }

    public ONOFFBean() {
    }

    public boolean isOpen2() {
        return isOpen2;
    }

    public void setOpen2(boolean open2) {
        isOpen2 = open2;
    }

    public boolean isOpen3() {
        return isOpen3;
    }

    public void setOpen3(boolean open3) {
        isOpen3 = open3;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

}
