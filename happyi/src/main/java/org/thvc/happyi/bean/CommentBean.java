package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by huangxinqi
 * on 2015/11/30-11:15.
 */
public class CommentBean {

    /**
     * status : 1
     * data : {"count":"1","maxPage":1,"list":[{"id":"2","dataid":"2","content":"很好哦","trait":"5.0","level":"3","ip":"","userid":"d192a4c4e49e1237","nickname":"张玺","headpic":"Member/default","isdel":"2","adddate":"2011-11-11","addtime":"1420701566","partyName":"探望敬老院","timeBefore":"一个月之前"}]}
     * info :
     */

    private int status;
    /**
     * count : 1
     * maxPage : 1
     * list : [{"id":"2","dataid":"2","content":"很好哦","trait":"5.0","level":"3","ip":"","userid":"d192a4c4e49e1237","nickname":"张玺","headpic":"Member/default","isdel":"2","adddate":"2011-11-11","addtime":"1420701566","partyName":"探望敬老院","timeBefore":"一个月之前"}]
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
         * id : 2
         * dataid : 2
         * content : 很好哦
         * trait : 5.0
         * level : 3
         * ip :
         * userid : d192a4c4e49e1237
         * nickname : 张玺
         * headpic : Member/default
         * isdel : 2
         * adddate : 2011-11-11
         * addtime : 1420701566
         * partyName : 探望敬老院
         * timeBefore : 一个月之前
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
            private String content;
            private String trait;
            private String level;
            private String ip;
            private String userid;
            private String nickname;
            private String headpic;
            private String isdel;
            private String adddate;
            private String addtime;
            private String partyName;
            private String timeBefore;

            public void setId(String id) {
                this.id = id;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setTrait(String trait) {
                this.trait = trait;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public void setIsdel(String isdel) {
                this.isdel = isdel;
            }

            public void setAdddate(String adddate) {
                this.adddate = adddate;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setPartyName(String partyName) {
                this.partyName = partyName;
            }

            public void setTimeBefore(String timeBefore) {
                this.timeBefore = timeBefore;
            }

            public String getId() {
                return id;
            }

            public String getDataid() {
                return dataid;
            }

            public String getContent() {
                return content;
            }

            public String getTrait() {
                return trait;
            }

            public String getLevel() {
                return level;
            }

            public String getIp() {
                return ip;
            }

            public String getUserid() {
                return userid;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHeadpic() {
                return headpic;
            }

            public String getIsdel() {
                return isdel;
            }

            public String getAdddate() {
                return adddate;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getPartyName() {
                return partyName;
            }

            public String getTimeBefore() {
                return timeBefore;
            }
        }
    }
}
