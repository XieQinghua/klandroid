package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by huangxinqi
 * on 2015/11/27-17:54.
 */
public class JoinPeopleBean {

    /**
     * status : 1
     * data : {"count":"11","maxPage":1,"list":[{"id":"31","userid":"QDBsR","isjoin":"1","isrefund":"2","ispay":"2","payfee":"1.00","model":"","dataid":"1","realname":"刘炜","job":"公益","tel":"15973111115","sex":"1","age":"32","first":"1","addtime":"1448507933","time":"1天前","headpic":"Member/default","nickname":""},{"id":"27","userid":"d192a4c4e49e1236","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"曾韦伯","job":"CTO","tel":"15874005820","sex":"1","age":"21","first":"1","addtime":"1448327492","time":"一个月前","headpic":"headpic/QE6DPERiYDWt","nickname":"老曾"},{"id":"15","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"测试报名","job":"互联网","tel":"13800000000","sex":"2","age":"23","first":"2","addtime":"1448102109","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"14","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"罗苗","job":"前端","tel":"13973153958","sex":"1","age":"25","first":"2","addtime":"1448101963","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"13","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"罗苗","job":"前端","tel":"13973153958","sex":"1","age":"25","first":"2","addtime":"1448101923","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"12","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"张平","job":"互联网","tel":"13973153958","sex":"1","age":"30","first":"2","addtime":"1448101746","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"11","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"denfils","job":"IT","tel":"13198761234","sex":"1","age":"33","first":"1","addtime":"1448092053","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"10","userid":"f7a3f9a7d0a8987f","isjoin":"1","isrefund":"2","ispay":"2","payfee":"180.00","model":"","dataid":"1","realname":"v哥哥很高更好相处","job":"0","tel":"13142270525","sex":"1","age":"15","first":"2","addtime":"1448079540","time":"一个月前","headpic":"avatar/f7a3f9a7d0a8987f.jpg","nickname":"房管局"},{"id":"9","userid":"f7a3f9a7d0a8987f","isjoin":"1","isrefund":"2","ispay":"2","payfee":"180.00","model":"","dataid":"1","realname":"v哥哥很高更好相处","job":"0","tel":"13142270525","sex":"1","age":"15","first":"2","addtime":"1448079473","time":"一个月前","headpic":"avatar/f7a3f9a7d0a8987f.jpg","nickname":"房管局"},{"id":"8","userid":"f7a3f9a7d0a8987f","isjoin":"1","isrefund":"2","ispay":"2","payfee":"180.00","model":"","dataid":"1","realname":"v哥哥很高更好相处","job":"0","tel":"13142270525","sex":"1","age":"15","first":"1","addtime":"1448079424","time":"一个月前","headpic":"avatar/f7a3f9a7d0a8987f.jpg","nickname":"房管局"},{"id":"7","userid":"ad67c3dc7d8ba573","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"张平","job":"互联网","tel":"13973153958","sex":"1","age":"30","first":"1","addtime":"1448016183","time":"一个月前","headpic":"Member/default","nickname":""}]}
     * info :
     */

    private int status;
    /**
     * count : 11
     * maxPage : 1
     * list : [{"id":"31","userid":"QDBsR","isjoin":"1","isrefund":"2","ispay":"2","payfee":"1.00","model":"","dataid":"1","realname":"刘炜","job":"公益","tel":"15973111115","sex":"1","age":"32","first":"1","addtime":"1448507933","time":"1天前","headpic":"Member/default","nickname":""},{"id":"27","userid":"d192a4c4e49e1236","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"曾韦伯","job":"CTO","tel":"15874005820","sex":"1","age":"21","first":"1","addtime":"1448327492","time":"一个月前","headpic":"headpic/QE6DPERiYDWt","nickname":"老曾"},{"id":"15","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"测试报名","job":"互联网","tel":"13800000000","sex":"2","age":"23","first":"2","addtime":"1448102109","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"14","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"罗苗","job":"前端","tel":"13973153958","sex":"1","age":"25","first":"2","addtime":"1448101963","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"13","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"罗苗","job":"前端","tel":"13973153958","sex":"1","age":"25","first":"2","addtime":"1448101923","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"12","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"张平","job":"互联网","tel":"13973153958","sex":"1","age":"30","first":"2","addtime":"1448101746","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"11","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"denfils","job":"IT","tel":"13198761234","sex":"1","age":"33","first":"1","addtime":"1448092053","time":"一个月前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"10","userid":"f7a3f9a7d0a8987f","isjoin":"1","isrefund":"2","ispay":"2","payfee":"180.00","model":"","dataid":"1","realname":"v哥哥很高更好相处","job":"0","tel":"13142270525","sex":"1","age":"15","first":"2","addtime":"1448079540","time":"一个月前","headpic":"avatar/f7a3f9a7d0a8987f.jpg","nickname":"房管局"},{"id":"9","userid":"f7a3f9a7d0a8987f","isjoin":"1","isrefund":"2","ispay":"2","payfee":"180.00","model":"","dataid":"1","realname":"v哥哥很高更好相处","job":"0","tel":"13142270525","sex":"1","age":"15","first":"2","addtime":"1448079473","time":"一个月前","headpic":"avatar/f7a3f9a7d0a8987f.jpg","nickname":"房管局"},{"id":"8","userid":"f7a3f9a7d0a8987f","isjoin":"1","isrefund":"2","ispay":"2","payfee":"180.00","model":"","dataid":"1","realname":"v哥哥很高更好相处","job":"0","tel":"13142270525","sex":"1","age":"15","first":"1","addtime":"1448079424","time":"一个月前","headpic":"avatar/f7a3f9a7d0a8987f.jpg","nickname":"房管局"},{"id":"7","userid":"ad67c3dc7d8ba573","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"1","realname":"张平","job":"互联网","tel":"13973153958","sex":"1","age":"30","first":"1","addtime":"1448016183","time":"一个月前","headpic":"Member/default","nickname":""}]
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
        private int maxPage;
        /**
         * id : 31
         * userid : QDBsR
         * isjoin : 1
         * isrefund : 2
         * ispay : 2
         * payfee : 1.00
         * model :
         * dataid : 1
         * realname : 刘炜
         * job : 公益
         * tel : 15973111115
         * sex : 1
         * age : 32
         * first : 1
         * addtime : 1448507933
         * time : 1天前
         * headpic : Member/default
         * nickname :
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
            private String userid;
            private String isjoin;
            private String isrefund;
            private String ispay;
            private String payfee;
            private String model;
            private String dataid;
            private String realname;
            private String job;
            private String tel;
            private String sex;
            private String age;
            private String first;
            private String addtime;
            private String time;
            private String headpic;
            private String nickname;

            public void setId(String id) {
                this.id = id;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setIsjoin(String isjoin) {
                this.isjoin = isjoin;
            }

            public void setIsrefund(String isrefund) {
                this.isrefund = isrefund;
            }

            public void setIspay(String ispay) {
                this.ispay = ispay;
            }

            public void setPayfee(String payfee) {
                this.payfee = payfee;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public void setFirst(String first) {
                this.first = first;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getId() {
                return id;
            }

            public String getUserid() {
                return userid;
            }

            public String getIsjoin() {
                return isjoin;
            }

            public String getIsrefund() {
                return isrefund;
            }

            public String getIspay() {
                return ispay;
            }

            public String getPayfee() {
                return payfee;
            }

            public String getModel() {
                return model;
            }

            public String getDataid() {
                return dataid;
            }

            public String getRealname() {
                return realname;
            }

            public String getJob() {
                return job;
            }

            public String getTel() {
                return tel;
            }

            public String getSex() {
                return sex;
            }

            public String getAge() {
                return age;
            }

            public String getFirst() {
                return first;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getTime() {
                return time;
            }

            public String getHeadpic() {
                return headpic;
            }

            public String getNickname() {
                return nickname;
            }
        }
    }
}
