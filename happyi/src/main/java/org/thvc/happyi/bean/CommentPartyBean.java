package org.thvc.happyi.bean;

/**
 * 项目名称：klandroid
 * 类描述：活动详情页面返回的的评价json Bean
 * 创建人：谢庆华.
 * 创建时间：2015/12/5 16:10
 * 修改人：Administrator
 * 修改时间：2015/12/5 16:10
 * 修改备注：
 */
public class CommentPartyBean {

    /**
     * data : {"actid":"25","type":"1"}
     * status : 1
     * info : 活动评论
     */
    private DataEntity data;
    private String status;
    private String info;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataEntity getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public static class DataEntity {
        /**
         * actid : 25
         * type : 1
         */
        private String actid;
        private String type;

        public void setActid(String actid) {
            this.actid = actid;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getActid() {
            return actid;
        }

        public String getType() {
            return type;
        }
    }
}
