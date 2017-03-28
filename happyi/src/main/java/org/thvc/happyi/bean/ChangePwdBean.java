package org.thvc.happyi.bean;

/**
 * 项目名称：klandroid
 * 类描述：
 * 创建人：谢庆华.
 * 创建时间：2015/11/21 16:24
 * 修改人：Administrator
 * 修改时间：2015/11/21 16:24
 * 修改备注：
 */
public class ChangePwdBean {

    /**
     * data : 3
     * status : 1
     * info : 修改密码成功
     */
    private String data;
    private int status;
    private String info;

    public void setData(String data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }
}
