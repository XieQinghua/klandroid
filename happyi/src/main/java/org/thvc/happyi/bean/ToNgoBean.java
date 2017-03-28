package org.thvc.happyi.bean;

/**
 * 项目名称：klandroid
 * 类描述：查看Ngo的Bean文件
 * 创建人：谢庆华.
 * 创建时间：2015/12/22 18:16
 * 修改人：Administrator
 * 修改时间：2015/12/22 18:16
 * 修改备注：
 */
public class ToNgoBean {

    /**
     * data : {"nickname":"长沙市芒果公益服务中心","solevar":"37bf4def170e3c31"}
     * status : 1
     * info : 查看NGO
     */
    private DataEntity data;
    private String status;
    private String info;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataEntity getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public static class DataEntity {
        /**
         * nickname : 长沙市芒果公益服务中心
         * solevar : 37bf4def170e3c31
         */
        private String nickname;
        private String solevar;

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setSolevar(String solevar) {
            this.solevar = solevar;
        }

        public String getNickname() {
            return nickname;
        }

        public String getSolevar() {
            return solevar;
        }
    }
}
