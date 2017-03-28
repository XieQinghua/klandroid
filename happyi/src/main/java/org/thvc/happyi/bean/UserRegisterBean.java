package org.thvc.happyi.bean;

import java.util.List;

/**
 * 项目名称：klandroid
 * 类描述：注册账号bean
 * 创建人：谢庆华.
 * 创建时间：2015/12/28 12:37
 * 修改人：Administrator
 * 修改时间：2015/12/28 12:37
 * 修改备注：
 */
public class UserRegisterBean {

    /**
     * data : {"_system":"2","code":"123456","mobile":"15200917588","active":1,"solevar":"4bb9d7a41fd02842","_URL_":["BDC","Account","reg"],"headpic":"Member/default","userid":180,"accountid":179,"password":"c8a0b904a3202681c653b1d344a88505","_deviceid":"","system":2,"field":"mobile","addtime":1451277430,"nickname":"","_model":"","username":"15200917588"}
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
         * _system : 2
         * code : 123456
         * mobile : 15200917588
         * active : 1
         * solevar : 4bb9d7a41fd02842
         * _URL_ : ["BDC","Account","reg"]
         * headpic : Member/default
         * userid : 180
         * accountid : 179
         * password : c8a0b904a3202681c653b1d344a88505
         * _deviceid :
         * system : 2
         * field : mobile
         * addtime : 1451277430
         * nickname :
         * _model :
         * username : 15200917588
         */
        private String _system;
        private String code;
        private String mobile;
        private int active;
        private String solevar;
        private List<String> _URL_;
        private String headpic;
        private int userid;
        private int accountid;
        private String password;
        private String _deviceid;
        private int system;
        private String field;
        private int addtime;
        private String nickname;
        private String _model;
        private String username;

        public void set_system(String _system) {
            this._system = _system;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setActive(int active) {
            this.active = active;
        }

        public void setSolevar(String solevar) {
            this.solevar = solevar;
        }

        public void set_URL_(List<String> _URL_) {
            this._URL_ = _URL_;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public void setAccountid(int accountid) {
            this.accountid = accountid;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void set_deviceid(String _deviceid) {
            this._deviceid = _deviceid;
        }

        public void setSystem(int system) {
            this.system = system;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void set_model(String _model) {
            this._model = _model;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String get_system() {
            return _system;
        }

        public String getCode() {
            return code;
        }

        public String getMobile() {
            return mobile;
        }

        public int getActive() {
            return active;
        }

        public String getSolevar() {
            return solevar;
        }

        public List<String> get_URL_() {
            return _URL_;
        }

        public String getHeadpic() {
            return headpic;
        }

        public int getUserid() {
            return userid;
        }

        public int getAccountid() {
            return accountid;
        }

        public String getPassword() {
            return password;
        }

        public String get_deviceid() {
            return _deviceid;
        }

        public int getSystem() {
            return system;
        }

        public String getField() {
            return field;
        }

        public int getAddtime() {
            return addtime;
        }

        public String getNickname() {
            return nickname;
        }

        public String get_model() {
            return _model;
        }

        public String getUsername() {
            return username;
        }
    }
}
