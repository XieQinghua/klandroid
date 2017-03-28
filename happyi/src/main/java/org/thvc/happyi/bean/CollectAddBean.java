package org.thvc.happyi.bean;

/**
 * Created by huangxinqi
 * on 2015/12/2-16:43.
 */
public class CollectAddBean {
    /**
     * status : 1
     * data : 您关注了，活动：探望敬老院
     * info : 您关注了，活动：探望敬老院
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
