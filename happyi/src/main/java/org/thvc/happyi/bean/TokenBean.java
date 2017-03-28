package org.thvc.happyi.bean;

/**
 * 项目名称：klandroid
 * 类描述：获取token
 * 创建人：谢庆华.
 * 创建时间：2015/11/30 14:26
 * 修改人：Administrator
 * 修改时间：2015/11/30 14:26
 * 修改备注：
 */
public class TokenBean {

    /**
     * data : {"upToken":"-nwoMptZb8lsoRi3HomOUdzBGSH5Ec8OjPBUhAd5:bdmSO84HasWhMSsb6ra8rRZ1oxw=:eyJzY29wZSI6ImhhcHBpeWk6MTIzIiwiZGVhZGxpbmUiOjE0NDg4NjgyMjN9"}
     * status : 1
     * info :
     */
    private DataEntity data;
    private int status;
    private String info;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataEntity getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public static class DataEntity {
        /**
         * upToken : -nwoMptZb8lsoRi3HomOUdzBGSH5Ec8OjPBUhAd5:bdmSO84HasWhMSsb6ra8rRZ1oxw=:eyJzY29wZSI6ImhhcHBpeWk6MTIzIiwiZGVhZGxpbmUiOjE0NDg4NjgyMjN9
         */
        private String upToken;

        public void setUpToken(String upToken) {
            this.upToken = upToken;
        }

        public String getUpToken() {
            return upToken;
        }
    }
}
