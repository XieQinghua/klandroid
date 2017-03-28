package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/19.
 */
public class MyActivitBean {
    /**
     * status : 1
     * data : {"count":"3","list":[{"id":"2","isdel":"4","solevar":"321321312","userid":"d192a4c4e49e1234","isget":"3","orgid":"","gettotal":"0.00","getpre":"0.00","getpep":"0","totalfee":"10.00","prefee":"44.00","totalpep":"22","joinpep":"33","collect":"22","good":"0","title":"二号公益","actcontact":"","actjob":"","acttel":"","actemail":"","status":"1","actbegin":"1448155658","enrollend":"1447982858","address":"湖南","detail":"长沙","fennum":"0.0","defnum":"5.0","subject":"party/JbisQ3rCpdZE","nickname":"","head":"","hasgood":"0"},{"id":"3","isdel":"2","solevar":"2f25ddbe86be631f","userid":"d192a4c4e49e1234","isget":"3","orgid":"","gettotal":"0.00","getpre":"0.00","getpep":"0","totalfee":"11.00","prefee":"44.00","totalpep":"22","joinpep":"33","collect":"0","good":"0","title":"活动名称活动名称活动名称活动名称","actcontact":"","actjob":"","acttel":"","actemail":"","status":"3","actbegin":"1447905675","enrollend":"1447732875","address":"湖南","detail":"长沙","fennum":"0.0","defnum":"5.0","subject":"","nickname":"","head":"","hasgood":"0"},{"id":"1","isdel":"4","solevar":"312312312312","userid":"d192a4c4e49e1234","isget":"2","orgid":"","gettotal":"0.00","getpre":"0.00","getpep":"0","totalfee":"0.00","prefee":"0.00","totalpep":"0","joinpep":"0","collect":"0","good":"0","title":"一号公益","actcontact":"","actjob":"","acttel":"","actemail":"","status":"1","actbegin":"0","enrollend":"0","address":"","detail":"","fennum":"0.0","defnum":"5.0","subject":"party/8rbKt2zBFzkJ","nickname":"","head":"","hasgood":"0"}]}
     * info :
     */
    private int status;
    /**
     * count : 3
     * list : [{"id":"2","isdel":"4","solevar":"321321312","userid":"d192a4c4e49e1234","isget":"3","orgid":"","gettotal":"0.00","getpre":"0.00","getpep":"0","totalfee":"10.00","prefee":"44.00","totalpep":"22","joinpep":"33","collect":"22","good":"0","title":"二号公益","actcontact":"","actjob":"","acttel":"","actemail":"","status":"1","actbegin":"1448155658","enrollend":"1447982858","address":"湖南","detail":"长沙","fennum":"0.0","defnum":"5.0","subject":"party/JbisQ3rCpdZE","nickname":"","head":"","hasgood":"0"},{"id":"3","isdel":"2","solevar":"2f25ddbe86be631f","userid":"d192a4c4e49e1234","isget":"3","orgid":"","gettotal":"0.00","getpre":"0.00","getpep":"0","totalfee":"11.00","prefee":"44.00","totalpep":"22","joinpep":"33","collect":"0","good":"0","title":"活动名称活动名称活动名称活动名称","actcontact":"","actjob":"","acttel":"","actemail":"","status":"3","actbegin":"1447905675","enrollend":"1447732875","address":"湖南","detail":"长沙","fennum":"0.0","defnum":"5.0","subject":"","nickname":"","head":"","hasgood":"0"},{"id":"1","isdel":"4","solevar":"312312312312","userid":"d192a4c4e49e1234","isget":"2","orgid":"","gettotal":"0.00","getpre":"0.00","getpep":"0","totalfee":"0.00","prefee":"0.00","totalpep":"0","joinpep":"0","collect":"0","good":"0","title":"一号公益","actcontact":"","actjob":"","acttel":"","actemail":"","status":"1","actbegin":"0","enrollend":"0","address":"","detail":"","fennum":"0.0","defnum":"5.0","subject":"party/8rbKt2zBFzkJ","nickname":"","head":"","hasgood":"0"}]
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
        private String count;
        /**
         * id : 2
         * isdel : 4
         * solevar : 321321312
         * userid : d192a4c4e49e1234
         * isget : 3
         * orgid :
         * gettotal : 0.00
         * getpre : 0.00
         * getpep : 0
         * totalfee : 10.00
         * prefee : 44.00
         * totalpep : 22
         * joinpep : 33
         * collect : 22
         * good : 0
         * title : 二号公益
         * actcontact :
         * actjob :
         * acttel :
         * actemail :
         * status : 1
         * actbegin : 1448155658
         * enrollend : 1447982858
         * address : 湖南
         * detail : 长沙
         * fennum : 0.0
         * defnum : 5.0
         * subject : party/JbisQ3rCpdZE
         * nickname :
         * head :
         * hasgood : 0
         */

        private List<ListEntity> list;

        public void setCount(String count) {
            this.count = count;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public String getCount() {
            return count;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private String id;
            private String isdel;
            private String solevar;
            private String userid;
            private String isget;
            private String orgid;
            private String gettotal;
            private String getpre;
            private String getpep;
            private String totalfee;
            private String prefee;
            private String totalpep;
            private String joinpep;
            private String collect;
            private String good;
            private String title;
            private String actcontact;
            private String actjob;
            private String acttel;
            private String actemail;
            private String status;
            private String actbegin;
            private String enrollend;
            private String address;
            private String detail;
            private String fennum;
            private String defnum;
            private String subject;
            private String nickname;
            private String head;
            private String hasgood;

            public void setId(String id) {
                this.id = id;
            }

            public void setIsdel(String isdel) {
                this.isdel = isdel;
            }

            public void setSolevar(String solevar) {
                this.solevar = solevar;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setIsget(String isget) {
                this.isget = isget;
            }

            public void setOrgid(String orgid) {
                this.orgid = orgid;
            }

            public void setGettotal(String gettotal) {
                this.gettotal = gettotal;
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

            public void setStatus(String status) {
                this.status = status;
            }

            public void setActbegin(String actbegin) {
                this.actbegin = actbegin;
            }

            public void setEnrollend(String enrollend) {
                this.enrollend = enrollend;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public void setFennum(String fennum) {
                this.fennum = fennum;
            }

            public void setDefnum(String defnum) {
                this.defnum = defnum;
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

            public void setHasgood(String hasgood) {
                this.hasgood = hasgood;
            }

            public String getId() {
                return id;
            }

            public String getIsdel() {
                return isdel;
            }

            public String getSolevar() {
                return solevar;
            }

            public String getUserid() {
                return userid;
            }

            public String getIsget() {
                return isget;
            }

            public String getOrgid() {
                return orgid;
            }

            public String getGettotal() {
                return gettotal;
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

            public String getStatus() {
                return status;
            }

            public String getActbegin() {
                return actbegin;
            }

            public String getEnrollend() {
                return enrollend;
            }

            public String getAddress() {
                return address;
            }

            public String getDetail() {
                return detail;
            }

            public String getFennum() {
                return fennum;
            }

            public String getDefnum() {
                return defnum;
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

            public String getHasgood() {
                return hasgood;
            }
        }
    }
}
