package org.thvc.happyi.bean;


import java.util.List;

/**
 * Created by Administrator on 2015/11/18.
 */
public class NgoListBean {
    /**
     * data : {"count":"3","list":[{"hascollect":2,"partycount":"0","nickname":"长沙市春雷公益助学促进会","solevar":"ed297ce5daa2dab8","id":"46","collect":"0","headpic":"ngo/fXx6ec6bhHKY","content":"2012年7月注册成立，有媒体从业人员、律师、高校教师和企业高管等人共同发起，成为湖南第一家没有业务主管单位民政局直接登记的社团法人，获评湖南十大公益事件\u2014民间公益力量奖，其品牌项目连续多年获得政府部门购买服务，得到合作伙伴美国科技教育协会、田家炳基金会、台湾政治大学等机构好评。"},{"hascollect":1,"partycount":"0","nickname":"湖南省环保志愿服务联合会","solevar":"a6c8620591fe3780","id":"41","collect":"9","headpic":"ngo/kmCkMywx4Jjh","content":"湖南省环保志愿服务联合会（以下称联合会，原湖南省环保社团联合会）是2011年在共青团湖南省委、省保护母亲河行动领导小组办公室的指导下，由各界热心于环境事业人士、环保公益类社会组织、企事业单位自愿结成的联合性、非营利性、全省性的环保类社会组织，目前有团体会员单位69个，会员覆盖全省各市州。"},{"hascollect":2,"partycount":"0","nickname":"长沙市红十字会志愿服务队","solevar":"cd21feda22476a9c","id":"34","collect":"0","headpic":"ngo/GD5xK6ByRB6S","content":"长沙市红十字会志愿服务队本着弘扬\u201c人道、博爱、奉献\u201d的红十字精神，按照\u201c统一指导、自愿参与、自主管理、自我完善\u201d的原则开展人道救助工作和社区公益活动。动员组织广大志愿者、爱心人士，参与社区服务，旧物循环利用、灾难救助、扶贫助学、助残济困、宣传和发动无偿献血等爱心服务。"}],"maxPage":1}
     * status : 1
     * info :
     */
    private DataEntity data;
    private int status;
    private String info;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataEntity getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public static class DataEntity {
        /**
         * count : 3
         * list : [{"hascollect":2,"partycount":"0","nickname":"长沙市春雷公益助学促进会","solevar":"ed297ce5daa2dab8","id":"46","collect":"0","headpic":"ngo/fXx6ec6bhHKY","content":"2012年7月注册成立，有媒体从业人员、律师、高校教师和企业高管等人共同发起，成为湖南第一家没有业务主管单位民政局直接登记的社团法人，获评湖南十大公益事件\u2014民间公益力量奖，其品牌项目连续多年获得政府部门购买服务，得到合作伙伴美国科技教育协会、田家炳基金会、台湾政治大学等机构好评。"},{"hascollect":1,"partycount":"0","nickname":"湖南省环保志愿服务联合会","solevar":"a6c8620591fe3780","id":"41","collect":"9","headpic":"ngo/kmCkMywx4Jjh","content":"湖南省环保志愿服务联合会（以下称联合会，原湖南省环保社团联合会）是2011年在共青团湖南省委、省保护母亲河行动领导小组办公室的指导下，由各界热心于环境事业人士、环保公益类社会组织、企事业单位自愿结成的联合性、非营利性、全省性的环保类社会组织，目前有团体会员单位69个，会员覆盖全省各市州。"},{"hascollect":2,"partycount":"0","nickname":"长沙市红十字会志愿服务队","solevar":"cd21feda22476a9c","id":"34","collect":"0","headpic":"ngo/GD5xK6ByRB6S","content":"长沙市红十字会志愿服务队本着弘扬\u201c人道、博爱、奉献\u201d的红十字精神，按照\u201c统一指导、自愿参与、自主管理、自我完善\u201d的原则开展人道救助工作和社区公益活动。动员组织广大志愿者、爱心人士，参与社区服务，旧物循环利用、灾难救助、扶贫助学、助残济困、宣传和发动无偿献血等爱心服务。"}]
         * maxPage : 1
         */
        private String count;
        private List<ListEntity> list;
        private int maxPage;

        public void setCount(String count) {
            this.count = count;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public void setMaxPage(int maxPage) {
            this.maxPage = maxPage;
        }

        public String getCount() {
            return count;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public int getMaxPage() {
            return maxPage;
        }

        public static class ListEntity {
            /**
             * hascollect : 2
             * partycount : 0
             * nickname : 长沙市春雷公益助学促进会
             * solevar : ed297ce5daa2dab8
             * id : 46
             * collect : 0
             * headpic : ngo/fXx6ec6bhHKY
             * content : 2012年7月注册成立，有媒体从业人员、律师、高校教师和企业高管等人共同发起，成为湖南第一家没有业务主管单位民政局直接登记的社团法人，获评湖南十大公益事件—民间公益力量奖，其品牌项目连续多年获得政府部门购买服务，得到合作伙伴美国科技教育协会、田家炳基金会、台湾政治大学等机构好评。
             */
            private int hascollect;
            private String partycount;
            private String nickname;
            private String solevar;
            private String id;
            private String collect;
            private String headpic;
            private String content;

            public void setHascollect(int hascollect) {
                this.hascollect = hascollect;
            }

            public void setPartycount(String partycount) {
                this.partycount = partycount;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setSolevar(String solevar) {
                this.solevar = solevar;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getHascollect() {
                return hascollect;
            }

            public String getPartycount() {
                return partycount;
            }

            public String getNickname() {
                return nickname;
            }

            public String getSolevar() {
                return solevar;
            }

            public String getId() {
                return id;
            }

            public String getCollect() {
                return collect;
            }

            public String getHeadpic() {
                return headpic;
            }

            public String getContent() {
                return content;
            }
        }
    }
}