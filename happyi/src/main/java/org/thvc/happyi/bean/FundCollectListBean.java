package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by huangxinqi
 * on 2015/11/24-14:38.
 * 查询收藏的基金会列表
 */
public class FundCollectListBean {

    /**
     * status : 1
     * data : {"count":"1","maxPage":1,"list":[{"id":"43","solevar":"41cab0d56c7b57e4","userid":"d192a4c4e49e1236","type":"3","dataid":"41cab0d56c7b57e4","num":"1","remark":"","addtime":"1448327772","status":"2","nickname":"芒果V基金","content":"芒果V基金由湖南广播电视台与中国社会福利基金会共同发起成立，是第一个由媒体集团发起、拥有广泛媒体资源的全国性公募慈善基金。芒果V基金秉承\u201c公民慈善、快乐慈善、透明慈善\u201d理念，专注于在广大电视观众、尤其是青少年中进行公民素质提升和公民道德建设。\r\n　　芒果V基金主要有\u201c快乐校车\u201d、\u201c快乐课桌\u201d、\u201c快乐工具书\u201d等以\u201c快乐\u201d为主题的系列公益项目，希望通过系列节目的播出、系列活动的策划、系列项目的实施，培养和倡导一种新的价值取向和社会风气，这就是：让爱心成为一种自觉习惯、让公益成为一张社会风尚、让慈善成为一种随手行为。","collect":"1","headpic":"avatar/41cab0d56c7b57e4.jpg","realname":"Dd"}]}
     * info :
     */

    private int status;
    /**
     * count : 1
     * maxPage : 1
     * list : [{"id":"43","solevar":"41cab0d56c7b57e4","userid":"d192a4c4e49e1236","type":"3","dataid":"41cab0d56c7b57e4","num":"1","remark":"","addtime":"1448327772","status":"2","nickname":"芒果V基金","content":"芒果V基金由湖南广播电视台与中国社会福利基金会共同发起成立，是第一个由媒体集团发起、拥有广泛媒体资源的全国性公募慈善基金。芒果V基金秉承\u201c公民慈善、快乐慈善、透明慈善\u201d理念，专注于在广大电视观众、尤其是青少年中进行公民素质提升和公民道德建设。\r\n　　芒果V基金主要有\u201c快乐校车\u201d、\u201c快乐课桌\u201d、\u201c快乐工具书\u201d等以\u201c快乐\u201d为主题的系列公益项目，希望通过系列节目的播出、系列活动的策划、系列项目的实施，培养和倡导一种新的价值取向和社会风气，这就是：让爱心成为一种自觉习惯、让公益成为一张社会风尚、让慈善成为一种随手行为。","collect":"1","headpic":"avatar/41cab0d56c7b57e4.jpg","realname":"Dd"}]
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
         * id : 43
         * solevar : 41cab0d56c7b57e4
         * userid : d192a4c4e49e1236
         * type : 3
         * dataid : 41cab0d56c7b57e4
         * num : 1
         * remark :
         * addtime : 1448327772
         * status : 2
         * nickname : 芒果V基金
         * content : 芒果V基金由湖南广播电视台与中国社会福利基金会共同发起成立，是第一个由媒体集团发起、拥有广泛媒体资源的全国性公募慈善基金。芒果V基金秉承“公民慈善、快乐慈善、透明慈善”理念，专注于在广大电视观众、尤其是青少年中进行公民素质提升和公民道德建设。
         　　芒果V基金主要有“快乐校车”、“快乐课桌”、“快乐工具书”等以“快乐”为主题的系列公益项目，希望通过系列节目的播出、系列活动的策划、系列项目的实施，培养和倡导一种新的价值取向和社会风气，这就是：让爱心成为一种自觉习惯、让公益成为一张社会风尚、让慈善成为一种随手行为。
         * collect : 1
         * headpic : avatar/41cab0d56c7b57e4.jpg
         * realname : Dd
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
            private String userid;
            private String type;
            private String dataid;
            private String num;
            private String remark;
            private String addtime;
            private String status;
            private String nickname;
            private String content;
            private String collect;
            private String headpic;
            private String realname;

            public void setId(String id) {
                this.id = id;
            }

            public void setSolevar(String solevar) {
                this.solevar = solevar;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getId() {
                return id;
            }

            public String getSolevar() {
                return solevar;
            }

            public String getUserid() {
                return userid;
            }

            public String getType() {
                return type;
            }

            public String getDataid() {
                return dataid;
            }

            public String getNum() {
                return num;
            }

            public String getRemark() {
                return remark;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getStatus() {
                return status;
            }

            public String getNickname() {
                return nickname;
            }

            public String getContent() {
                return content;
            }

            public String getCollect() {
                return collect;
            }

            public String getHeadpic() {
                return headpic;
            }

            public String getRealname() {
                return realname;
            }
        }
    }
}
