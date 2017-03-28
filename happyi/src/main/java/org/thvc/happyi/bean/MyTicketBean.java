package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class MyTicketBean {

    /**
     * status : 1
     * data : {"count":"1","maxPage":1,"list":[{"id":"2886","userid":"7cc3fef2d884ab67","isjoin":"1","isrefund":"2","ispay":"1","payfee":"1.00","model":"","dataid":"17","realname":"松梁","job":"0","tel":"15116409705","sex":"1","age":"23","first":"1","addtime":"1454034183","idcard":null,"qcode":"Upload/Join/17/d9c26574841b71ef_2886.png","scan":"0","partytitle":"绿色方舟\u201c救\u201d在你身边","time":"46分钟前","headpic":"Member/7cc3fef2d884ab67.jpg","head":"Member/7cc3fef2d884ab67.jpg","nickname":"跳涨剑圣"}]}
     * info :
     */

    private int status;
    /**
     * count : 1
     * maxPage : 1
     * list : [{"id":"2886","userid":"7cc3fef2d884ab67","isjoin":"1","isrefund":"2","ispay":"1","payfee":"1.00","model":"","dataid":"17","realname":"松梁","job":"0","tel":"15116409705","sex":"1","age":"23","first":"1","addtime":"1454034183","idcard":null,"qcode":"Upload/Join/17/d9c26574841b71ef_2886.png","scan":"0","partytitle":"绿色方舟\u201c救\u201d在你身边","time":"46分钟前","headpic":"Member/7cc3fef2d884ab67.jpg","head":"Member/7cc3fef2d884ab67.jpg","nickname":"跳涨剑圣"}]
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
         * id : 2886
         * userid : 7cc3fef2d884ab67
         * isjoin : 1
         * isrefund : 2
         * ispay : 1
         * payfee : 1.00
         * model :
         * dataid : 17
         * realname : 松梁
         * job : 0
         * tel : 15116409705
         * sex : 1
         * age : 23
         * first : 1
         * addtime : 1454034183
         * idcard : null
         * qcode : Upload/Join/17/d9c26574841b71ef_2886.png
         * scan : 0
         * partytitle : 绿色方舟“救”在你身边
         * time : 46分钟前
         * headpic : Member/7cc3fef2d884ab67.jpg
         * head : Member/7cc3fef2d884ab67.jpg
         * nickname : 跳涨剑圣
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
            private Object idcard;
            private String qcode;
            private String scan;
            private String partytitle;
            private String time;
            private String headpic;
            private String head;
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

            public void setIdcard(Object idcard) {
                this.idcard = idcard;
            }

            public void setQcode(String qcode) {
                this.qcode = qcode;
            }

            public void setScan(String scan) {
                this.scan = scan;
            }

            public void setPartytitle(String partytitle) {
                this.partytitle = partytitle;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public void setHead(String head) {
                this.head = head;
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

            public Object getIdcard() {
                return idcard;
            }

            public String getQcode() {
                return qcode;
            }

            public String getScan() {
                return scan;
            }

            public String getPartytitle() {
                return partytitle;
            }

            public String getTime() {
                return time;
            }

            public String getHeadpic() {
                return headpic;
            }

            public String getHead() {
                return head;
            }

            public String getNickname() {
                return nickname;
            }
        }
    }
}
