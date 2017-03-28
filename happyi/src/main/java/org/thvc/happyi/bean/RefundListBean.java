package org.thvc.happyi.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/1.
 */
public class RefundListBean {

    /**
     * status : 1
     * data : {"count":"5","maxPage":1,"list":[{"id":"5","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"26","payfee":"100.00","aliapy":"","bank":"支付宝退款","realname":"v哥哥很高更好相处","cardno":"Ty","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448524659","isdel":"3","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"},{"id":"4","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"-1","payfee":"100.00","aliapy":"","bank":"0","realname":"v哥哥很高更好相处","cardno":"0","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448435948","isdel":"4","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"},{"id":"3","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"-1","payfee":"100.00","aliapy":"","bank":"0","realname":"v哥哥很高更好相处","cardno":"0","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448435891","isdel":"3","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"},{"id":"2","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"-1","payfee":"100.00","aliapy":"","bank":"0","realname":"v哥哥很高更好相处","cardno":"0","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448435817","isdel":"2","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"},{"id":"1","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"-1","payfee":"100.00","aliapy":"","bank":"0","realname":"v哥哥很高更好相处","cardno":"0","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448435796","isdel":"2","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"}]}
     * info :
     */

    private int status;
    /**
     * count : 5
     * maxPage : 1
     * list : [{"id":"5","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"26","payfee":"100.00","aliapy":"","bank":"支付宝退款","realname":"v哥哥很高更好相处","cardno":"Ty","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448524659","isdel":"3","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"},{"id":"4","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"-1","payfee":"100.00","aliapy":"","bank":"0","realname":"v哥哥很高更好相处","cardno":"0","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448435948","isdel":"4","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"},{"id":"3","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"-1","payfee":"100.00","aliapy":"","bank":"0","realname":"v哥哥很高更好相处","cardno":"0","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448435891","isdel":"3","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"},{"id":"2","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"-1","payfee":"100.00","aliapy":"","bank":"0","realname":"v哥哥很高更好相处","cardno":"0","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448435817","isdel":"2","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"},{"id":"1","dataid":"7","userid":"f7a3f9a7d0a8987f","joinid":"-1","payfee":"100.00","aliapy":"","bank":"0","realname":"v哥哥很高更好相处","cardno":"0","tel":"13142270525","email":"wskddddhtghhv@.qq..com","addtime":"1448435796","isdel":"2","reason":"","nickname":"房管局","head":"avatar/f7a3f9a7d0a8987f.jpg","title":"安定老人院，关爱老年人","enrollend":"1448364414"}]
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
         * dataid : 7
         * userid : f7a3f9a7d0a8987f
         * joinid : 26
         * payfee : 100.00
         * aliapy :
         * bank : 支付宝退款
         * realname : v哥哥很高更好相处
         * cardno : Ty
         * tel : 13142270525
         * email : wskddddhtghhv@.qq..com
         * addtime : 1448524659
         * isdel : 3
         * reason :
         * nickname : 房管局
         * head : avatar/f7a3f9a7d0a8987f.jpg
         * title : 安定老人院，关爱老年人
         * enrollend : 1448364414
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
            private String aliapy;
            private String bank;
            private String realname;
            private String cardno;
            private String tel;
            private String email;
            private String addtime;
            private String isdel;
            private String reason;
            private String nickname;
            private String head;
            private String title;
            private String enrollend;

            public static ListEntity objectFromData(String str) {

                return new Gson().fromJson(str, ListEntity.class);
            }

            public static ListEntity objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), ListEntity.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<ListEntity> arrayListEntityFromData(String str) {

                Type listType = new TypeToken<ArrayList<ListEntity>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<ListEntity> arrayListEntityFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<ListEntity>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

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

            public void setAliapy(String aliapy) {
                this.aliapy = aliapy;
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

            public String getAliapy() {
                return aliapy;
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
