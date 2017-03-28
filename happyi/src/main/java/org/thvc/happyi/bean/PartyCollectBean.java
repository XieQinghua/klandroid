package org.thvc.happyi.bean;

import java.util.List;

/**
 * 我的关注-活动bean
 * 2016.04.01修改
 */
public class PartyCollectBean {

    /**
     * status : 1
     * data : {"count":"2","maxPage":1,"list":[{"id":"1112","dataid":"155","uid":"412056b007664eb2","status":"2","addtime":"1458289080","type":"1","partyid":"155","title":"庆祝\u201c甘蔗音乐计划\u201d及璐熙可寻找至美的你活动启动线上大派送。\u2014\u2014我们在湖南毅行终点等你来拿奖","solevar":"0e610edd589d04da","orgid":"41cab0d56c7b57e4","ngoid":"412056b007664eb2","userid":"412056b007664eb2","collect":"17","getopre":"0.00","getpre":"0.00","prefee":"0.00","totalpep":"5000","joinpep":"519","actbegin":"1458057600","actend":"1458144000","enrollend":"1459872000","isget":"3","isdel":"6","sort":"9","province":"湖南省","city":"长沙市","addr":"线上","partystatus":",2,8,9,","subject":"party/A2ciEizDZBpA","statuscn":[{"id":2,"title":"明星公益","icon":"fl4.png"}],"nickname":"湖南快乐益网络科技有限公司","head":"ngo/2KSFb3M2wPS6","fundname":"芒果V基金","fundhead":"Member/Fz5zpEWW2Mmn","hascollect":1},{"id":"1155","dataid":"141","uid":"412056b007664eb2","status":"2","addtime":"1458809651","type":"1","partyid":"141","title":"红色征途·湖南毅行","solevar":"4e374517bfb3d53f","orgid":"41cab0d56c7b57e4","ngoid":"b60f81b265b0b597","userid":"b60f81b265b0b597","collect":"33","getopre":"0.00","getpre":"0.00","prefee":"85.00","totalpep":"600","joinpep":"600","actbegin":"1460156400","actend":"1460242800","enrollend":"1459353600","isget":"3","isdel":"7","sort":"1","province":"湖南省","city":"长沙市","addr":"洋湖湿地公园先导牛广场","partystatus":",3,5,6,","subject":"party/PnEtEdYNSipQ","statuscn":[{"id":2,"title":"明星公益","icon":"fl4.png"}],"nickname":"湖南贝尔弗特体育文化有限公司","head":"ngo/FfhQx2BAKACY","fundname":"芒果V基金","fundhead":"Member/Fz5zpEWW2Mmn","hascollect":1}]}
     * info :
     * version : 1.3
     */

    private int status;
    /**
     * count : 2
     * maxPage : 1
     * list : [{"id":"1112","dataid":"155","uid":"412056b007664eb2","status":"2","addtime":"1458289080","type":"1","partyid":"155","title":"庆祝\u201c甘蔗音乐计划\u201d及璐熙可寻找至美的你活动启动线上大派送。\u2014\u2014我们在湖南毅行终点等你来拿奖","solevar":"0e610edd589d04da","orgid":"41cab0d56c7b57e4","ngoid":"412056b007664eb2","userid":"412056b007664eb2","collect":"17","getopre":"0.00","getpre":"0.00","prefee":"0.00","totalpep":"5000","joinpep":"519","actbegin":"1458057600","actend":"1458144000","enrollend":"1459872000","isget":"3","isdel":"6","sort":"9","province":"湖南省","city":"长沙市","addr":"线上","partystatus":",2,8,9,","subject":"party/A2ciEizDZBpA","statuscn":[{"id":2,"title":"明星公益","icon":"fl4.png"}],"nickname":"湖南快乐益网络科技有限公司","head":"ngo/2KSFb3M2wPS6","fundname":"芒果V基金","fundhead":"Member/Fz5zpEWW2Mmn","hascollect":1},{"id":"1155","dataid":"141","uid":"412056b007664eb2","status":"2","addtime":"1458809651","type":"1","partyid":"141","title":"红色征途·湖南毅行","solevar":"4e374517bfb3d53f","orgid":"41cab0d56c7b57e4","ngoid":"b60f81b265b0b597","userid":"b60f81b265b0b597","collect":"33","getopre":"0.00","getpre":"0.00","prefee":"85.00","totalpep":"600","joinpep":"600","actbegin":"1460156400","actend":"1460242800","enrollend":"1459353600","isget":"3","isdel":"7","sort":"1","province":"湖南省","city":"长沙市","addr":"洋湖湿地公园先导牛广场","partystatus":",3,5,6,","subject":"party/PnEtEdYNSipQ","statuscn":[{"id":2,"title":"明星公益","icon":"fl4.png"}],"nickname":"湖南贝尔弗特体育文化有限公司","head":"ngo/FfhQx2BAKACY","fundname":"芒果V基金","fundhead":"Member/Fz5zpEWW2Mmn","hascollect":1}]
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
         * id : 1112
         * dataid : 155
         * uid : 412056b007664eb2
         * status : 2
         * addtime : 1458289080
         * type : 1
         * partyid : 155
         * title : 庆祝“甘蔗音乐计划”及璐熙可寻找至美的你活动启动线上大派送。——我们在湖南毅行终点等你来拿奖
         * solevar : 0e610edd589d04da
         * orgid : 41cab0d56c7b57e4
         * ngoid : 412056b007664eb2
         * userid : 412056b007664eb2
         * collect : 17
         * getopre : 0.00
         * getpre : 0.00
         * prefee : 0.00
         * totalpep : 5000
         * joinpep : 519
         * actbegin : 1458057600
         * actend : 1458144000
         * enrollend : 1459872000
         * isget : 3
         * isdel : 6
         * sort : 9
         * province : 湖南省
         * city : 长沙市
         * addr : 线上
         * partystatus : ,2,8,9,
         * subject : party/A2ciEizDZBpA
         * statuscn : [{"id":2,"title":"明星公益","icon":"fl4.png"}]
         * nickname : 湖南快乐益网络科技有限公司
         * head : ngo/2KSFb3M2wPS6
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
            private String dataid;
            private String uid;
            private String status;
            private String addtime;
            private String type;
            private String partyid;
            private String title;
            private String solevar;
            private String orgid;
            private String ngoid;
            private String userid;
            private String collect;
            private String getopre;
            private String getpre;
            private String prefee;
            private String totalpep;
            private String joinpep;
            private String actbegin;
            private String actend;
            private String enrollend;
            private String isget;
            private String isdel;
            private String sort;
            private String province;
            private String city;
            private String addr;
            private String partystatus;
            private String subject;
            private String nickname;
            private String head;
            private String fundname;
            private String fundhead;
            private int hascollect;
            /**
             * id : 2
             * title : 明星公益
             * icon : fl4.png
             */

            private List<StatuscnEntity> statuscn;

            public void setId(String id) {
                this.id = id;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setPartyid(String partyid) {
                this.partyid = partyid;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setSolevar(String solevar) {
                this.solevar = solevar;
            }

            public void setOrgid(String orgid) {
                this.orgid = orgid;
            }

            public void setNgoid(String ngoid) {
                this.ngoid = ngoid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public void setGetopre(String getopre) {
                this.getopre = getopre;
            }

            public void setGetpre(String getpre) {
                this.getpre = getpre;
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

            public void setActbegin(String actbegin) {
                this.actbegin = actbegin;
            }

            public void setActend(String actend) {
                this.actend = actend;
            }

            public void setEnrollend(String enrollend) {
                this.enrollend = enrollend;
            }

            public void setIsget(String isget) {
                this.isget = isget;
            }

            public void setIsdel(String isdel) {
                this.isdel = isdel;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public void setPartystatus(String partystatus) {
                this.partystatus = partystatus;
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

            public String getDataid() {
                return dataid;
            }

            public String getUid() {
                return uid;
            }

            public String getStatus() {
                return status;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getType() {
                return type;
            }

            public String getPartyid() {
                return partyid;
            }

            public String getTitle() {
                return title;
            }

            public String getSolevar() {
                return solevar;
            }

            public String getOrgid() {
                return orgid;
            }

            public String getNgoid() {
                return ngoid;
            }

            public String getUserid() {
                return userid;
            }

            public String getCollect() {
                return collect;
            }

            public String getGetopre() {
                return getopre;
            }

            public String getGetpre() {
                return getpre;
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

            public String getActbegin() {
                return actbegin;
            }

            public String getActend() {
                return actend;
            }

            public String getEnrollend() {
                return enrollend;
            }

            public String getIsget() {
                return isget;
            }

            public String getIsdel() {
                return isdel;
            }

            public String getSort() {
                return sort;
            }

            public String getProvince() {
                return province;
            }

            public String getCity() {
                return city;
            }

            public String getAddr() {
                return addr;
            }

            public String getPartystatus() {
                return partystatus;
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
