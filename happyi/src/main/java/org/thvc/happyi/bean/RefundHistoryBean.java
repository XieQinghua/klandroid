package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/12/18.
 */
public class RefundHistoryBean {

    /**
     * status : 1
     * data : {"count":"1","maxPage":1,"list":[{"id":"5","dataid":"21","userid":"8deedd84f51197e4","joinid":"137","payfee":"1.00","alipay":"18774096336","bank":"","realname":"周芝花","cardno":"18774096336","tel":"18774096336","email":"369856231@qq.con","addtime":"1450335161","isdel":"3","reason":"时间原因","nickname":"好妹妹","headpic":"avatar/8deedd84f51197e4.jpg","head":"avatar/8deedd84f51197e4.jpg","title":"\u201c一枝黄花\u201d清理行动","enrollend":"1450800000"}]}
     * info :
     */

    private int status;
    /**
     * count : 1
     * maxPage : 1
     * list : [{"id":"5","dataid":"21","userid":"8deedd84f51197e4","joinid":"137","payfee":"1.00","alipay":"18774096336","bank":"","realname":"周芝花","cardno":"18774096336","tel":"18774096336","email":"369856231@qq.con","addtime":"1450335161","isdel":"3","reason":"时间原因","nickname":"好妹妹","headpic":"avatar/8deedd84f51197e4.jpg","head":"avatar/8deedd84f51197e4.jpg","title":"\u201c一枝黄花\u201d清理行动","enrollend":"1450800000"}]
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
         * id : 5
         * dataid : 21
         * userid : 8deedd84f51197e4
         * joinid : 137
         * payfee : 1.00
         * alipay : 18774096336
         * bank :
         * realname : 周芝花
         * cardno : 18774096336
         * tel : 18774096336
         * email : 369856231@qq.con
         * addtime : 1450335161
         * isdel : 3
         * reason : 时间原因
         * nickname : 好妹妹
         * headpic : avatar/8deedd84f51197e4.jpg
         * head : avatar/8deedd84f51197e4.jpg
         * title : “一枝黄花”清理行动
         * enrollend : 1450800000
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
            private String userid;
            private String joinid;
            private String payfee;
            private String alipay;
            private String bank;
            private String realname;
            private String cardno;
            private String tel;
            private String email;
            private String addtime;
            private String isdel;
            private String reason;
            private String nickname;
            private String headpic;
            private String head;
            private String title;
            private String enrollend;

            public void setId(String id) {
                this.id = id;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setJoinid(String joinid) {
                this.joinid = joinid;
            }

            public void setPayfee(String payfee) {
                this.payfee = payfee;
            }

            public void setAlipay(String alipay) {
                this.alipay = alipay;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public void setCardno(String cardno) {
                this.cardno = cardno;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setIsdel(String isdel) {
                this.isdel = isdel;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setEnrollend(String enrollend) {
                this.enrollend = enrollend;
            }

            public String getId() {
                return id;
            }

            public String getDataid() {
                return dataid;
            }

            public String getUserid() {
                return userid;
            }

            public String getJoinid() {
                return joinid;
            }

            public String getPayfee() {
                return payfee;
            }

            public String getAlipay() {
                return alipay;
            }

            public String getBank() {
                return bank;
            }

            public String getRealname() {
                return realname;
            }

            public String getCardno() {
                return cardno;
            }

            public String getTel() {
                return tel;
            }

            public String getEmail() {
                return email;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getIsdel() {
                return isdel;
            }

            public String getReason() {
                return reason;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHeadpic() {
                return headpic;
            }

            public String getHead() {
                return head;
            }

            public String getTitle() {
                return title;
            }

            public String getEnrollend() {
                return enrollend;
            }
        }
    }
}
