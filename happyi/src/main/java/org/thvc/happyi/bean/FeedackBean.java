package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2015/11/30.
 */
public class FeedackBean {


    /**
     * status : 1
     * data :
     * info :
     */

    private int status;
    private String data;
    private String info;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }
}
