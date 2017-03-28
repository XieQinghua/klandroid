package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2015/11/23.
 */
public class FoundationBean {
    /**
     * status : 1
     * data : {"userid":"41cab0d56c7b57e4","username":"18684775833","mobile":"18684775833","openid":"o2Ye_v5nKN2jZEQVZyKKBYNBkUnQ","password":"c8a0b904a3202681c653b1d344a88505","status":"1","active":"1","actmobile":"1","actwechat":"1","lastaddr":"","lastip":"","lasttime":"0","logintimes":"1","daylast":"2015-12-20","daytimes":"1","_deviceid":"","_model":"","_system":"2","openqq":"0797E45CDF99E839B3DCAC8F81A924FE","opensina":"","solevar":"41cab0d56c7b57e4","email":"v@vfund.org","ishot":"2","collect":"6","system":"4","im":"2","overdata":"2","level":"","uppic":"1","realname":"中国社会福利基金会","nickname":"芒果V基金","address":"长沙金鹰影视城主楼11楼","website":"www.vfund.org","age":15,"birthday":"2000-01-01","headpic":"Member/Fz5zpEWW2Mmn","sex":"1","job":"","content":"芒果V基金由湖南广播电视台与中国社会福利基金会共同发起成\r\n立，是第一个由媒体集团发起、拥有广泛媒体资源的全国性公募慈\r\n善基金。芒果V基金秉承\u201c公民慈善、快乐慈善、透明慈善\u201d理\r\n念，专注于在广大电视观众、尤其是青少年中进行公民素质提升和\r\n公民道德建设。\r\n芒果V基金主要有\u201c快乐校车\u201d、\u201c快乐课桌\u201d、\u201c快乐工具\r\n书\u201d等以\u201c快乐\u201d为主题的系列公益项目，希望通过系列节目的播\r\n出、系列活动的策划、系列项目的实施，培养和倡导一种新的价值\r\n取向和社会风气，这就是：让爱心成为一种自觉习惯、让公益成为\r\n一张社会风尚、让慈善成为一种随手行为。","blog":"","weibo":"","wechat":"","qq":"","latitude":"","longitude":"","geohash":"","usejifen":"0","totaljifen":"0","usefee":"0.00","sort":"1","edits":"22","moditime":"1450864142","isdel":"1","idcard":"","info":{"orgcontact":"李强","orgjob":"副秘书长","orgtel":"0731-84802229","orgemail":"lq@vfund.org","actcontact":"刘常青","actjob":"专职志愿者","acttel":"18508425347","actemail":"liuchangqing@vfund.org"},"messagecount":"0"}
     * info :
     */

    private int status;
    /**
     * userid : 41cab0d56c7b57e4
     * username : 18684775833
     * mobile : 18684775833
     * openid : o2Ye_v5nKN2jZEQVZyKKBYNBkUnQ
     * password : c8a0b904a3202681c653b1d344a88505
     * status : 1
     * active : 1
     * actmobile : 1
     * actwechat : 1
     * lastaddr :
     * lastip :
     * lasttime : 0
     * logintimes : 1
     * daylast : 2015-12-20
     * daytimes : 1
     * _deviceid :
     * _model :
     * _system : 2
     * openqq : 0797E45CDF99E839B3DCAC8F81A924FE
     * opensina :
     * solevar : 41cab0d56c7b57e4
     * email : v@vfund.org
     * ishot : 2
     * collect : 6
     * system : 4
     * im : 2
     * overdata : 2
     * level :
     * uppic : 1
     * realname : 中国社会福利基金会
     * nickname : 芒果V基金
     * address : 长沙金鹰影视城主楼11楼
     * website : www.vfund.org
     * age : 15
     * birthday : 2000-01-01
     * headpic : Member/Fz5zpEWW2Mmn
     * sex : 1
     * job :
     * content : 芒果V基金由湖南广播电视台与中国社会福利基金会共同发起成
     立，是第一个由媒体集团发起、拥有广泛媒体资源的全国性公募慈
     善基金。芒果V基金秉承“公民慈善、快乐慈善、透明慈善”理
     念，专注于在广大电视观众、尤其是青少年中进行公民素质提升和
     公民道德建设。
     芒果V基金主要有“快乐校车”、“快乐课桌”、“快乐工具
     书”等以“快乐”为主题的系列公益项目，希望通过系列节目的播
     出、系列活动的策划、系列项目的实施，培养和倡导一种新的价值
     取向和社会风气，这就是：让爱心成为一种自觉习惯、让公益成为
     一张社会风尚、让慈善成为一种随手行为。
     * blog :
     * weibo :
     * wechat :
     * qq :
     * latitude :
     * longitude :
     * geohash :
     * usejifen : 0
     * totaljifen : 0
     * usefee : 0.00
     * sort : 1
     * edits : 22
     * moditime : 1450864142
     * isdel : 1
     * idcard :
     * info : {"orgcontact":"李强","orgjob":"副秘书长","orgtel":"0731-84802229","orgemail":"lq@vfund.org","actcontact":"刘常青","actjob":"专职志愿者","acttel":"18508425347","actemail":"liuchangqing@vfund.org"}
     * messagecount : 0
     */

    private DataEntity data;
    private String info;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }

    public static class DataEntity {
        private String userid;
        private String username;
        private String mobile;
        private String openid;
        private String password;
        private String status;
        private String active;
        private String actmobile;
        private String actwechat;
        private String lastaddr;
        private String lastip;
        private String lasttime;
        private String logintimes;
        private String daylast;
        private String daytimes;
        private String _deviceid;
        private String _model;
        private String _system;
        private String openqq;
        private String opensina;
        private String solevar;
        private String email;
        private String ishot;
        private String collect;
        private String system;
        private String im;
        private String overdata;
        private String level;
        private String uppic;
        private String realname;
        private String nickname;
        private String address;
        private String website;
        private int age;
        private String birthday;
        private String headpic;
        private String sex;
        private String job;
        private String content;
        private String blog;
        private String weibo;
        private String wechat;
        private String qq;
        private String latitude;
        private String longitude;
        private String geohash;
        private String usejifen;
        private String totaljifen;
        private String usefee;
        private String sort;
        private String edits;
        private String moditime;
        private String isdel;
        private String idcard;
        /**
         * orgcontact : 李强
         * orgjob : 副秘书长
         * orgtel : 0731-84802229
         * orgemail : lq@vfund.org
         * actcontact : 刘常青
         * actjob : 专职志愿者
         * acttel : 18508425347
         * actemail : liuchangqing@vfund.org
         */

        private InfoEntity info;
        private String messagecount;

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public void setActmobile(String actmobile) {
            this.actmobile = actmobile;
        }

        public void setActwechat(String actwechat) {
            this.actwechat = actwechat;
        }

        public void setLastaddr(String lastaddr) {
            this.lastaddr = lastaddr;
        }

        public void setLastip(String lastip) {
            this.lastip = lastip;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }

        public void setLogintimes(String logintimes) {
            this.logintimes = logintimes;
        }

        public void setDaylast(String daylast) {
            this.daylast = daylast;
        }

        public void setDaytimes(String daytimes) {
            this.daytimes = daytimes;
        }

        public void set_deviceid(String _deviceid) {
            this._deviceid = _deviceid;
        }

        public void set_model(String _model) {
            this._model = _model;
        }

        public void set_system(String _system) {
            this._system = _system;
        }

        public void setOpenqq(String openqq) {
            this.openqq = openqq;
        }

        public void setOpensina(String opensina) {
            this.opensina = opensina;
        }

        public void setSolevar(String solevar) {
            this.solevar = solevar;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setIshot(String ishot) {
            this.ishot = ishot;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public void setIm(String im) {
            this.im = im;
        }

        public void setOverdata(String overdata) {
            this.overdata = overdata;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setUppic(String uppic) {
            this.uppic = uppic;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setBlog(String blog) {
            this.blog = blog;
        }

        public void setWeibo(String weibo) {
            this.weibo = weibo;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setGeohash(String geohash) {
            this.geohash = geohash;
        }

        public void setUsejifen(String usejifen) {
            this.usejifen = usejifen;
        }

        public void setTotaljifen(String totaljifen) {
            this.totaljifen = totaljifen;
        }

        public void setUsefee(String usefee) {
            this.usefee = usefee;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setEdits(String edits) {
            this.edits = edits;
        }

        public void setModitime(String moditime) {
            this.moditime = moditime;
        }

        public void setIsdel(String isdel) {
            this.isdel = isdel;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public void setInfo(InfoEntity info) {
            this.info = info;
        }

        public void setMessagecount(String messagecount) {
            this.messagecount = messagecount;
        }

        public String getUserid() {
            return userid;
        }

        public String getUsername() {
            return username;
        }

        public String getMobile() {
            return mobile;
        }

        public String getOpenid() {
            return openid;
        }

        public String getPassword() {
            return password;
        }

        public String getStatus() {
            return status;
        }

        public String getActive() {
            return active;
        }

        public String getActmobile() {
            return actmobile;
        }

        public String getActwechat() {
            return actwechat;
        }

        public String getLastaddr() {
            return lastaddr;
        }

        public String getLastip() {
            return lastip;
        }

        public String getLasttime() {
            return lasttime;
        }

        public String getLogintimes() {
            return logintimes;
        }

        public String getDaylast() {
            return daylast;
        }

        public String getDaytimes() {
            return daytimes;
        }

        public String get_deviceid() {
            return _deviceid;
        }

        public String get_model() {
            return _model;
        }

        public String get_system() {
            return _system;
        }

        public String getOpenqq() {
            return openqq;
        }

        public String getOpensina() {
            return opensina;
        }

        public String getSolevar() {
            return solevar;
        }

        public String getEmail() {
            return email;
        }

        public String getIshot() {
            return ishot;
        }

        public String getCollect() {
            return collect;
        }

        public String getSystem() {
            return system;
        }

        public String getIm() {
            return im;
        }

        public String getOverdata() {
            return overdata;
        }

        public String getLevel() {
            return level;
        }

        public String getUppic() {
            return uppic;
        }

        public String getRealname() {
            return realname;
        }

        public String getNickname() {
            return nickname;
        }

        public String getAddress() {
            return address;
        }

        public String getWebsite() {
            return website;
        }

        public int getAge() {
            return age;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getHeadpic() {
            return headpic;
        }

        public String getSex() {
            return sex;
        }

        public String getJob() {
            return job;
        }

        public String getContent() {
            return content;
        }

        public String getBlog() {
            return blog;
        }

        public String getWeibo() {
            return weibo;
        }

        public String getWechat() {
            return wechat;
        }

        public String getQq() {
            return qq;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getGeohash() {
            return geohash;
        }

        public String getUsejifen() {
            return usejifen;
        }

        public String getTotaljifen() {
            return totaljifen;
        }

        public String getUsefee() {
            return usefee;
        }

        public String getSort() {
            return sort;
        }

        public String getEdits() {
            return edits;
        }

        public String getModitime() {
            return moditime;
        }

        public String getIsdel() {
            return isdel;
        }

        public String getIdcard() {
            return idcard;
        }

        public InfoEntity getInfo() {
            return info;
        }

        public String getMessagecount() {
            return messagecount;
        }

        public static class InfoEntity {
            private String orgcontact;
            private String orgjob;
            private String orgtel;
            private String orgemail;
            private String actcontact;
            private String actjob;
            private String acttel;
            private String actemail;

            public void setOrgcontact(String orgcontact) {
                this.orgcontact = orgcontact;
            }

            public void setOrgjob(String orgjob) {
                this.orgjob = orgjob;
            }

            public void setOrgtel(String orgtel) {
                this.orgtel = orgtel;
            }

            public void setOrgemail(String orgemail) {
                this.orgemail = orgemail;
            }

            public void setActcontact(String actcontact) {
                this.actcontact = actcontact;
            }

            public void setActjob(String actjob) {
                this.actjob = actjob;
            }

            public void setActtel(String acttel) {
                this.acttel = acttel;
            }

            public void setActemail(String actemail) {
                this.actemail = actemail;
            }

            public String getOrgcontact() {
                return orgcontact;
            }

            public String getOrgjob() {
                return orgjob;
            }

            public String getOrgtel() {
                return orgtel;
            }

            public String getOrgemail() {
                return orgemail;
            }

            public String getActcontact() {
                return actcontact;
            }

            public String getActjob() {
                return actjob;
            }

            public String getActtel() {
                return acttel;
            }

            public String getActemail() {
                return actemail;
            }
        }
    }
}
