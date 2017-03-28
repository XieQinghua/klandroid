package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2015/12/9.
 */
public class PartyClaimBean {

    /**
     * status : 1
     * data : 19
     * info :
     */

    private int status;
    private int data;
    private String info;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public int getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }
}
