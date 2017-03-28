package org.thvc.happyi.bean;

/**
 * Created by huangxinqi
 * on 2015/12/4-19:22.
 */
public class OAuthBindBean {
    private Object data;
    private String info;
    private int status;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
