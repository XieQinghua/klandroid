package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/30.
 * 2016.04.01修改
 */
public class NgoPartyListBean {

    /**
     * status : 1
     * data : {"count":"1","maxPage":1,"list":[{"id":"171","solevar":"70261a49717ed17e","userid":"27678e336c1d3a14","isdel":"8","isget":"3","orgid":"41cab0d56c7b57e4","getopre":"0.00","getpre":"0.00","getpep":"40","totalfee":"4000.00","prefee":"100.00","totalpep":"40","joinpep":"2","collect":"1","good":"0","title":"\u201c绣·湘逢有你\u201d 柳建新刺绣艺术博物馆 春日欣赏体验会","description":"湘绣是什么？\r\n湘绣是等你来欣赏的，\r\n丝光线影~\r\n\r\n湘绣是什么？\r\n湘绣是等你来倾听的，\r\n缎情绸语~\r\n\r\n湘绣是什么？\r\n湘绣是等你来触碰的，\r\n针实物活~\r\n\r\n这一切妙境，\r\n就在本周六，\r\n柳建新刺绣艺术博物馆，\r\n等你来品味。\r\n\r\n\u201c绣·湘逢有你\u201d春日欣赏体验会，\r\n为入馆的客人准备了：湘绣珍品联展、湘绣文化解读、湘绣创意DIY体验活动。\r\n我们静待与绣有缘的你光临。\r\n","actcontact":"刘潇雨","acttel":"13975192177","status":",9,","datetype":"2","actbegin":"1458972000","actend":"1459058400","enrollstart":"1458835200","enrollend":"1458921600","sign":"","ngono":"27678e336c1d3a14","bpfield":",1,2,3,4,","type":",1,","maxnum":"0","recnum":"0","fennum":"0.0","defnum":"5.0","addtime":"1458786238","safe":"2","recommend":"2","support":"2","supprotmoney":"0","qrcode":"","lng":"113.043361","lat":"28.150590","province":"湖南省","city":"长沙市","area":"2144","addr":"万家丽中路万科金域华府三期8-101，柳建新刺绣艺术博物馆","addrdes":"柳建新刺绣艺术博物馆（万科金域华府三期8-101）\r\n近万家丽中路，位于湖南联通小区与万佳广场之间，万科繁华街坡上。\r\n附近公交站（湖南联通）66路/802路/810路，距离约300米。","subject":"party/HsYrwhkh4Kmr","statuscn":[{"id":9,"title":"文化艺术","icon":"fl11.png"}],"nickname":"柳建新刺绣艺术博物馆","head":"ngo/Cw4DR8WiBTtG","fundname":"芒果V基金","fundhead":"Member/Fz5zpEWW2Mmn","hascollect":1}]}
     * info :
     * version : 1.3
     */

    private int status;
    /**
     * count : 1
     * maxPage : 1
     * list : [{"id":"171","solevar":"70261a49717ed17e","userid":"27678e336c1d3a14","isdel":"8","isget":"3","orgid":"41cab0d56c7b57e4","getopre":"0.00","getpre":"0.00","getpep":"40","totalfee":"4000.00","prefee":"100.00","totalpep":"40","joinpep":"2","collect":"1","good":"0","title":"\u201c绣·湘逢有你\u201d 柳建新刺绣艺术博物馆 春日欣赏体验会","description":"湘绣是什么？\r\n湘绣是等你来欣赏的，\r\n丝光线影~\r\n\r\n湘绣是什么？\r\n湘绣是等你来倾听的，\r\n缎情绸语~\r\n\r\n湘绣是什么？\r\n湘绣是等你来触碰的，\r\n针实物活~\r\n\r\n这一切妙境，\r\n就在本周六，\r\n柳建新刺绣艺术博物馆，\r\n等你来品味。\r\n\r\n\u201c绣·湘逢有你\u201d春日欣赏体验会，\r\n为入馆的客人准备了：湘绣珍品联展、湘绣文化解读、湘绣创意DIY体验活动。\r\n我们静待与绣有缘的你光临。\r\n","actcontact":"刘潇雨","acttel":"13975192177","status":",9,","datetype":"2","actbegin":"1458972000","actend":"1459058400","enrollstart":"1458835200","enrollend":"1458921600","sign":"","ngono":"27678e336c1d3a14","bpfield":",1,2,3,4,","type":",1,","maxnum":"0","recnum":"0","fennum":"0.0","defnum":"5.0","addtime":"1458786238","safe":"2","recommend":"2","support":"2","supprotmoney":"0","qrcode":"","lng":"113.043361","lat":"28.150590","province":"湖南省","city":"长沙市","area":"2144","addr":"万家丽中路万科金域华府三期8-101，柳建新刺绣艺术博物馆","addrdes":"柳建新刺绣艺术博物馆（万科金域华府三期8-101）\r\n近万家丽中路，位于湖南联通小区与万佳广场之间，万科繁华街坡上。\r\n附近公交站（湖南联通）66路/802路/810路，距离约300米。","subject":"party/HsYrwhkh4Kmr","statuscn":[{"id":9,"title":"文化艺术","icon":"fl11.png"}],"nickname":"柳建新刺绣艺术博物馆","head":"ngo/Cw4DR8WiBTtG","fundname":"芒果V基金","fundhead":"Member/Fz5zpEWW2Mmn","hascollect":1}]
     */

    private DataEntity data;
    private String info;
    private String version;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
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

    public DataEntity getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }

    public String getVersion() {
        return version;
    }

    public static class DataEntity {
        private String count;
        private int maxPage;
        /**
         * id : 171
         * solevar : 70261a49717ed17e
         * userid : 27678e336c1d3a14
         * isdel : 8
         * isget : 3
         * orgid : 41cab0d56c7b57e4
         * getopre : 0.00
         * getpre : 0.00
         * getpep : 40
         * totalfee : 4000.00
         * prefee : 100.00
         * totalpep : 40
         * joinpep : 2
         * collect : 1
         * good : 0
         * title : “绣·湘逢有你” 柳建新刺绣艺术博物馆 春日欣赏体验会
         * description : 湘绣是什么？
         湘绣是等你来欣赏的，
         丝光线影~

         湘绣是什么？
         湘绣是等你来倾听的，
         缎情绸语~

         湘绣是什么？
         湘绣是等你来触碰的，
         针实物活~

         这一切妙境，
         就在本周六，
         柳建新刺绣艺术博物馆，
         等你来品味。

         “绣·湘逢有你”春日欣赏体验会，
         为入馆的客人准备了：湘绣珍品联展、湘绣文化解读、湘绣创意DIY体验活动。
         我们静待与绣有缘的你光临。

         * actcontact : 刘潇雨
         * acttel : 13975192177
         * status : ,9,
         * datetype : 2
         * actbegin : 1458972000
         * actend : 1459058400
         * enrollstart : 1458835200
         * enrollend : 1458921600
         * sign :
         * ngono : 27678e336c1d3a14
         * bpfield : ,1,2,3,4,
         * type : ,1,
         * maxnum : 0
         * recnum : 0
         * fennum : 0.0
         * defnum : 5.0
         * addtime : 1458786238
         * safe : 2
         * recommend : 2
         * support : 2
         * supprotmoney : 0
         * qrcode :
         * lng : 113.043361
         * lat : 28.150590
         * province : 湖南省
         * city : 长沙市
         * area : 2144
         * addr : 万家丽中路万科金域华府三期8-101，柳建新刺绣艺术博物馆
         * addrdes : 柳建新刺绣艺术博物馆（万科金域华府三期8-101）
         近万家丽中路，位于湖南联通小区与万佳广场之间，万科繁华街坡上。
         附近公交站（湖南联通）66路/802路/810路，距离约300米。
         * subject : party/HsYrwhkh4Kmr
         * statuscn : [{"id":9,"title":"文化艺术","icon":"fl11.png"}]
         * nickname : 柳建新刺绣艺术博物馆
         * head : ngo/Cw4DR8WiBTtG
         * fundname : 芒果V基金
         * fundhead : Member/Fz5zpEWW2Mmn
         * hascollect : 1
         */

        private List<ListEntity> list;

        public void setCount(String count) {
            this.count = count;
        }

        public void setMaxPage(int maxPage) {
            this.maxPage = maxPage;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public String getCount() {
            return count;
        }

        public int getMaxPage() {
            return maxPage;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private String id;
            private String solevar;
            private String userid;
            private String isdel;
            private String isget;
            private String orgid;
            private String getopre;
            private String getpre;
            private String getpep;
            private String totalfee;
            private String prefee;
            private String totalpep;
            private String joinpep;
            private String collect;
            private String good;
            private String title;
            private String description;
            private String actcontact;
            private String acttel;
            private String status;
            private String datetype;
            private String actbegin;
            private String actend;
            private String enrollstart;
            private String enrollend;
            private String sign;
            private String ngono;
            private String bpfield;
            private String type;
            private String maxnum;
            private String recnum;
            private String fennum;
            private String defnum;
            private String addtime;
            private String safe;
            private String recommend;
            private String support;
            private String supprotmoney;
            private String qrcode;
            private String lng;
            private String lat;
            private String province;
            private String city;
            private String area;
            private String addr;
            private String addrdes;
            private String subject;
            private String nickname;
            private String head;
            private String fundname;
            private String fundhead;
            private int hascollect;
            /**
             * id : 9
             * title : 文化艺术
             * icon : fl11.png
             */

            private List<StatuscnEntity> statuscn;

            public void setId(String id) {
                this.id = id;
            }

            public void setSolevar(String solevar) {
                this.solevar = solevar;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setIsdel(String isdel) {
                this.isdel = isdel;
            }

            public void setIsget(String isget) {
                this.isget = isget;
            }

            public void setOrgid(String orgid) {
                this.orgid = orgid;
            }

            public void setGetopre(String getopre) {
                this.getopre = getopre;
            }

            public void setGetpre(String getpre) {
                this.getpre = getpre;
            }

            public void setGetpep(String getpep) {
                this.getpep = getpep;
            }

            public void setTotalfee(String totalfee) {
                this.totalfee = totalfee;
            }

            public void setPrefee(String prefee) {
                this.prefee = prefee;
            }

            public void setTotalpep(String totalpep) {
                this.totalpep = totalpep;
            }

            public void setJoinpep(String joinpep) {
                this.joinpep = joinpep;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public void setGood(String good) {
                this.good = good;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setActcontact(String actcontact) {
                this.actcontact = actcontact;
            }

            public void setActtel(String acttel) {
                this.acttel = acttel;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setDatetype(String datetype) {
                this.datetype = datetype;
            }

            public void setActbegin(String actbegin) {
                this.actbegin = actbegin;
            }

            public void setActend(String actend) {
                this.actend = actend;
            }

            public void setEnrollstart(String enrollstart) {
                this.enrollstart = enrollstart;
            }

            public void setEnrollend(String enrollend) {
                this.enrollend = enrollend;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public void setNgono(String ngono) {
                this.ngono = ngono;
            }

            public void setBpfield(String bpfield) {
                this.bpfield = bpfield;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setMaxnum(String maxnum) {
                this.maxnum = maxnum;
            }

            public void setRecnum(String recnum) {
                this.recnum = recnum;
            }

            public void setFennum(String fennum) {
                this.fennum = fennum;
            }

            public void setDefnum(String defnum) {
                this.defnum = defnum;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setSafe(String safe) {
                this.safe = safe;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public void setSupport(String support) {
                this.support = support;
            }

            public void setSupprotmoney(String supprotmoney) {
                this.supprotmoney = supprotmoney;
            }

            public void setQrcode(String qrcode) {
                this.qrcode = qrcode;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public void setAddrdes(String addrdes) {
                this.addrdes = addrdes;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public void setFundname(String fundname) {
                this.fundname = fundname;
            }

            public void setFundhead(String fundhead) {
                this.fundhead = fundhead;
            }

            public void setHascollect(int hascollect) {
                this.hascollect = hascollect;
            }

            public void setStatuscn(List<StatuscnEntity> statuscn) {
                this.statuscn = statuscn;
            }

            public String getId() {
                return id;
            }

            public String getSolevar() {
                return solevar;
            }

            public String getUserid() {
                return userid;
            }

            public String getIsdel() {
                return isdel;
            }

            public String getIsget() {
                return isget;
            }

            public String getOrgid() {
                return orgid;
            }

            public String getGetopre() {
                return getopre;
            }

            public String getGetpre() {
                return getpre;
            }

            public String getGetpep() {
                return getpep;
            }

            public String getTotalfee() {
                return totalfee;
            }

            public String getPrefee() {
                return prefee;
            }

            public String getTotalpep() {
                return totalpep;
            }

            public String getJoinpep() {
                return joinpep;
            }

            public String getCollect() {
                return collect;
            }

            public String getGood() {
                return good;
            }

            public String getTitle() {
                return title;
            }

            public String getDescription() {
                return description;
            }

            public String getActcontact() {
                return actcontact;
            }

            public String getActtel() {
                return acttel;
            }

            public String getStatus() {
                return status;
            }

            public String getDatetype() {
                return datetype;
            }

            public String getActbegin() {
                return actbegin;
            }

            public String getActend() {
                return actend;
            }

            public String getEnrollstart() {
                return enrollstart;
            }

            public String getEnrollend() {
                return enrollend;
            }

            public String getSign() {
                return sign;
            }

            public String getNgono() {
                return ngono;
            }

            public String getBpfield() {
                return bpfield;
            }

            public String getType() {
                return type;
            }

            public String getMaxnum() {
                return maxnum;
            }

            public String getRecnum() {
                return recnum;
            }

            public String getFennum() {
                return fennum;
            }

            public String getDefnum() {
                return defnum;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getSafe() {
                return safe;
            }

            public String getRecommend() {
                return recommend;
            }

            public String getSupport() {
                return support;
            }

            public String getSupprotmoney() {
                return supprotmoney;
            }

            public String getQrcode() {
                return qrcode;
            }

            public String getLng() {
                return lng;
            }

            public String getLat() {
                return lat;
            }

            public String getProvince() {
                return province;
            }

            public String getCity() {
                return city;
            }

            public String getArea() {
                return area;
            }

            public String getAddr() {
                return addr;
            }

            public String getAddrdes() {
                return addrdes;
            }

            public String getSubject() {
                return subject;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHead() {
                return head;
            }

            public String getFundname() {
                return fundname;
            }

            public String getFundhead() {
                return fundhead;
            }

            public int getHascollect() {
                return hascollect;
            }

            public List<StatuscnEntity> getStatuscn() {
                return statuscn;
            }

            public static class StatuscnEntity {
                private int id;
                private String title;
                private String icon;

                public void setId(int id) {
                    this.id = id;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public int getId() {
                    return id;
                }

                public String getTitle() {
                    return title;
                }

                public String getIcon() {
                    return icon;
                }
            }
        }
    }
}
