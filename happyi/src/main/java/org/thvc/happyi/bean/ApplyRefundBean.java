package org.thvc.happyi.bean;

/**
 * Created by huangxinqi
 * on 2015/12/14-16:27.
 * 申请退款的bean
 */
public class ApplyRefundBean {
    private int data;
    private String info;
    private int status;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
