package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class Foundationlistbean {
    /**
     * status : 1
     * data : {"count":"1","maxPage":1,"list":[{"id":"15","solevar":"41cab0d56c7b57e4","collect":"15","nickname":"芒果V基金","headpic":"Member/Fz5zpEWW2Mmn","content":"芒果V基金由湖南广播电视台与中国社会福利基金会共同发起成\r\n立，是第一个由媒体集团发起、拥有广泛媒体资源的全国性公募慈\r\n善基金。芒果V基金秉承\u201c公民慈善、快乐慈善、透明慈善\u201d理\r\n念，专注于在广大电视观众、尤其是青少年中进行公民素质提升和\r\n公民道德建设。\r\n芒果V基金主要有\u201c快乐校车\u201d、\u201c快乐课桌\u201d、\u201c快乐工具\r\n书\u201d等以\u201c快乐\u201d为主题的系列公益项目，希望通过系列节目的播\r\n出、系列活动的策划、系列项目的实施，培养和倡导一种新的价值\r\n取向和社会风气，这就是：让爱心成为一种自觉习惯、让公益成为\r\n一张社会风尚、让慈善成为一种随手行为。","partycount":"109","actcontact":"刘常青"}]}
     * info :
     * version : 15123003
     */

    private int status;
    /**
     * count : 1
     * maxPage : 1
     * list : [{"id":"15","solevar":"41cab0d56c7b57e4","collect":"15","nickname":"芒果V基金","headpic":"Member/Fz5zpEWW2Mmn","content":"芒果V基金由湖南广播电视台与中国社会福利基金会共同发起成\r\n立，是第一个由媒体集团发起、拥有广泛媒体资源的全国性公募慈\r\n善基金。芒果V基金秉承\u201c公民慈善、快乐慈善、透明慈善\u201d理\r\n念，专注于在广大电视观众、尤其是青少年中进行公民素质提升和\r\n公民道德建设。\r\n芒果V基金主要有\u201c快乐校车\u201d、\u201c快乐课桌\u201d、\u201c快乐工具\r\n书\u201d等以\u201c快乐\u201d为主题的系列公益项目，希望通过系列节目的播\r\n出、系列活动的策划、系列项目的实施，培养和倡导一种新的价值\r\n取向和社会风气，这就是：让爱心成为一种自觉习惯、让公益成为\r\n一张社会风尚、让慈善成为一种随手行为。","partycount":"109","actcontact":"刘常青"}]
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
         * id : 15
         * solevar : 41cab0d56c7b57e4
         * collect : 15
         * nickname : 芒果V基金
         * headpic : Member/Fz5zpEWW2Mmn
         * content : 芒果V基金由湖南广播电视台与中国社会福利基金会共同发起成
         立，是第一个由媒体集团发起、拥有广泛媒体资源的全国性公募慈
         善基金。芒果V基金秉承“公民慈善、快乐慈善、透明慈善”理
         念，专注于在广大电视观众、尤其是青少年中进行公民素质提升和
         公民道德建设。
         芒果V基金主要有“快乐校车”、“快乐课桌”、“快乐工具
         书”等以“快乐”为主题的系列公益项目，希望通过系列节目的播
         出、系列活动的策划、系列项目的实施，培养和倡导一种新的价值
         取向和社会风气，这就是：让爱心成为一种自觉习惯、让公益成为
         一张社会风尚、让慈善成为一种随手行为。
         * partycount : 109
         * actcontact : 刘常青
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
            private String solevar;
            private String collect;
            private String nickname;
            private String headpic;
            private String content;
            private String partycount;
            private String actcontact;

            public void setId(String id) {
                this.id = id;
            }

            public void setSolevar(String solevar) {
                this.solevar = solevar;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setPartycount(String partycount) {
                this.partycount = partycount;
            }

            public void setActcontact(String actcontact) {
                this.actcontact = actcontact;
            }

            public String getId() {
                return id;
            }

            public String getSolevar() {
                return solevar;
            }

            public String getCollect() {
                return collect;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHeadpic() {
                return headpic;
            }

            public String getContent() {
                return content;
            }

            public String getPartycount() {
                return partycount;
            }

            public String getActcontact() {
                return actcontact;
            }
        }
    }
}
