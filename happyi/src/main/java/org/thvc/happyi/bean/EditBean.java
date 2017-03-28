package org.thvc.happyi.bean;

import java.util.List;

/**
 * 项目名称：klandroid
 * 类描述：
 * 创建人：谢庆华.
 * 创建时间：2015/11/23 19:06
 * 修改人：Administrator
 * 修改时间：2015/11/23 19:06
 * 修改备注：
 */
public class EditBean {

    /**
     * data : {"birthday":"2000-01-01","moditime":1448275564,"sex":"1","edits":5,"nickname":"嘿嘿","job":"医生","_URL_":["BDC","Account","edit"]}
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
         * birthday : 2000-01-01
         * moditime : 1448275564
         * sex : 1
         * edits : 5
         * nickname : 嘿嘿
         * job : 医生
         * _URL_ : ["BDC","Account","edit"]
         */
        private String birthday;
        private int moditime;
        private String sex;
        private int edits;
        private String nickname;
        private String job;
        private List<String> _URL_;

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setModitime(int moditime) {
            this.moditime = moditime;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setEdits(int edits) {
            this.edits = edits;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public void set_URL_(List<String> _URL_) {
            this._URL_ = _URL_;
        }

        public String getBirthday() {
            return birthday;
        }

        public int getModitime() {
            return moditime;
        }

        public String getSex() {
            return sex;
        }

        public int getEdits() {
            return edits;
        }

        public String getNickname() {
            return nickname;
        }

        public String getJob() {
            return job;
        }

        public List<String> get_URL_() {
            return _URL_;
        }
    }
}
