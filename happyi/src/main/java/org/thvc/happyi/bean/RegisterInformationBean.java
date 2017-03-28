package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/25.
 */
public class RegisterInformationBean {


    /**
     * status : 1
     * data : {"count":"4","maxPage":1,"list":[{"id":"25","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"8","realname":"陈家攀","job":"大司空","tel":"13786104732","sex":"2","age":"31","first":"2","addtime":"1448272060","time":"1天前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"24","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"0.00","model":"","dataid":"8","realname":"张玺1","job":"php","tel":"13786104732","sex":"2","age":"15","first":"2","addtime":"1448266187","time":"1天前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"23","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"8","realname":"陈家攀","job":"大司马","tel":"15675800751","sex":"1","age":"25","first":"2","addtime":"1448264596","time":"1天前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"22","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"8","realname":"张玺1","job":"大联盟酋长","tel":"13786104732","sex":"2","age":"25","first":"1","addtime":"1448264018","time":"1天前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"}]}
     * info :
     */

    private int status;
    /**
     * count : 4
     * maxPage : 1
     * list : [{"id":"25","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"8","realname":"陈家攀","job":"大司空","tel":"13786104732","sex":"2","age":"31","first":"2","addtime":"1448272060","time":"1天前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"24","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"0.00","model":"","dataid":"8","realname":"张玺1","job":"php","tel":"13786104732","sex":"2","age":"15","first":"2","addtime":"1448266187","time":"1天前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"23","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"8","realname":"陈家攀","job":"大司马","tel":"15675800751","sex":"1","age":"25","first":"2","addtime":"1448264596","time":"1天前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"},{"id":"22","userid":"d192a4c4e49e1237","isjoin":"1","isrefund":"2","ispay":"2","payfee":"100.00","model":"","dataid":"8","realname":"张玺1","job":"大联盟酋长","tel":"13786104732","sex":"2","age":"25","first":"1","addtime":"1448264018","time":"1天前","headpic":"headpic/K3SRTQ5tA3xz","nickname":"张玺n1"}]
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
         * id : 25
         * userid : d192a4c4e49e1237
         * isjoin : 1
         * isrefund : 2
         * ispay : 2
         * payfee : 100.00
         * model :
         * dataid : 8
         * realname : 陈家攀
         * job : 大司空
         * tel : 13786104732
         * sex : 2
         * age : 31
         * first : 2
         * addtime : 1448272060
         * time : 1天前
         * headpic : headpic/K3SRTQ5tA3xz
         * nickname : 张玺n1
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
