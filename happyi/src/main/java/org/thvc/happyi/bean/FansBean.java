package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by huangxinqi
 * on 2015/11/30-16:43.
 */
public class FansBean {


    /**
     * status : 1
     * data : {"count":"1","maxPage":1,"list":[{"id":"296","userid":"b73e1b200ece1ac0","dataid":"18","addtime":"1450944475","nickname":"长沙市绿舟防灾减灾促进中心","headpic":"Member/YCe845AmeGxZ","membertype":"3","sex":"1","content":"长沙市绿舟防灾减灾促进中心（以下简称绿舟）是在长沙市民政\r\n局正式注册的民办非企业（NGO),绿舟秉持\u201c防\u201d比\u201c救\u201d更重\r\n要。\u201c尽绿舟所能，教人人自救\u201d，致力建设全民灾害第一响应\r\n能力系统，辅助政府、学校、社区及企事业单位进行防减灾体系\r\n建设。","isCollected":0,"hasCollected":1},{"id":"171","userid":"cd21feda22476a9c","dataid":"18","addtime":"1450529845","nickname":"长沙市红十字会志愿服务队","headpic":"Member/GD5xK6ByRB6S","membertype":"3","sex":"1","content":"长沙市红十字会志愿服务队本着弘扬\u201c人道、博爱、奉献\u201d的红十字精神，按照\u201c统一指导、自愿参与、自主管理、自我完善\u201d的原则开展人道救助工作和社区公益活动。动员组织广大志愿者、爱心人士，参与社区服务，旧物循环利用、灾难救助、扶贫助学、助残济困、宣传和发动无偿献血等爱心服务。","isCollected":0,"hasCollected":1},{"id":"25","userid":"434afe6928d921e4","dataid":"18","addtime":"1449156109","nickname":"匿名用户","headpic":"Member/default","membertype":"2","sex":"1","content":"","isCollected":0,"hasCollected":1},{"id":"18","userid":"f56a2e54b673c968","dataid":"18","addtime":"1449105340","nickname":"长沙市厚天消防义工服务中心","headpic":"Member/JjwehGeBHar5","membertype":"3","sex":"1","content":"\u201c厚德载物 天道酬勤\u201d。厚天消防义工团于2005年10月由消防退伍老兵唐智湘发起成立，2013年正式在长沙市民政局登记注册，是湖南省唯一以消防命名的专业从事 消防公益活动的、群众性的、非盈利性的社会组织。","isCollected":0,"hasCollected":1}]}
     * info :
     */

    private int status;
    /**
     * count : 1
     * maxPage : 1
     * list : [{"id":"296","userid":"b73e1b200ece1ac0","dataid":"18","addtime":"1450944475","nickname":"长沙市绿舟防灾减灾促进中心","headpic":"Member/YCe845AmeGxZ","membertype":"3","sex":"1","content":"长沙市绿舟防灾减灾促进中心（以下简称绿舟）是在长沙市民政\r\n局正式注册的民办非企业（NGO),绿舟秉持\u201c防\u201d比\u201c救\u201d更重\r\n要。\u201c尽绿舟所能，教人人自救\u201d，致力建设全民灾害第一响应\r\n能力系统，辅助政府、学校、社区及企事业单位进行防减灾体系\r\n建设。","isCollected":0,"hasCollected":1},{"id":"171","userid":"cd21feda22476a9c","dataid":"18","addtime":"1450529845","nickname":"长沙市红十字会志愿服务队","headpic":"Member/GD5xK6ByRB6S","membertype":"3","sex":"1","content":"长沙市红十字会志愿服务队本着弘扬\u201c人道、博爱、奉献\u201d的红十字精神，按照\u201c统一指导、自愿参与、自主管理、自我完善\u201d的原则开展人道救助工作和社区公益活动。动员组织广大志愿者、爱心人士，参与社区服务，旧物循环利用、灾难救助、扶贫助学、助残济困、宣传和发动无偿献血等爱心服务。","isCollected":0,"hasCollected":1},{"id":"25","userid":"434afe6928d921e4","dataid":"18","addtime":"1449156109","nickname":"匿名用户","headpic":"Member/default","membertype":"2","sex":"1","content":"","isCollected":0,"hasCollected":1},{"id":"18","userid":"f56a2e54b673c968","dataid":"18","addtime":"1449105340","nickname":"长沙市厚天消防义工服务中心","headpic":"Member/JjwehGeBHar5","membertype":"3","sex":"1","content":"\u201c厚德载物 天道酬勤\u201d。厚天消防义工团于2005年10月由消防退伍老兵唐智湘发起成立，2013年正式在长沙市民政局登记注册，是湖南省唯一以消防命名的专业从事 消防公益活动的、群众性的、非盈利性的社会组织。","isCollected":0,"hasCollected":1}]
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
         * id : 296
         * userid : b73e1b200ece1ac0
         * dataid : 18
         * addtime : 1450944475
         * nickname : 长沙市绿舟防灾减灾促进中心
         * headpic : Member/YCe845AmeGxZ
         * membertype : 3
         * sex : 1
         * content : 长沙市绿舟防灾减灾促进中心（以下简称绿舟）是在长沙市民政
         局正式注册的民办非企业（NGO),绿舟秉持“防”比“救”更重
         要。“尽绿舟所能，教人人自救”，致力建设全民灾害第一响应
         能力系统，辅助政府、学校、社区及企事业单位进行防减灾体系
         建设。
         * isCollected : 0
         * hasCollected : 1
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
            private String dataid;
            private String addtime;
            private String nickname;
            private String headpic;
            private String membertype;
            private String sex;
            private String content;
            private int isCollected;
            private int hasCollected;

            public void setId(String id) {
                this.id = id;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public void setMembertype(String membertype) {
                this.membertype = membertype;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setIsCollected(int isCollected) {
                this.isCollected = isCollected;
            }

            public void setHasCollected(int hasCollected) {
                this.hasCollected = hasCollected;
            }

            public String getId() {
                return id;
            }

            public String getUserid() {
                return userid;
            }

            public String getDataid() {
                return dataid;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHeadpic() {
                return headpic;
            }

            public String getMembertype() {
                return membertype;
            }

            public String getSex() {
                return sex;
            }

            public String getContent() {
                return content;
            }

            public int getIsCollected() {
                return isCollected;
            }

            public int getHasCollected() {
                return hasCollected;
            }
        }
    }
}
