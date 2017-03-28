package org.thvc.happyi.bean;

/**
 * Created by huangxinqi on 2016/4/8.
 * 家庭报名
 */
public class FamilyEnrollBean {

    /**
     * status : 1
     * data : 6079
     * info : 参与活动成功
     * version : 1.3
     */

    private int status;
    private int data;
    private String info;
    private String version;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getVersion() {
        return version;
    }
}
