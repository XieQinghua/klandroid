package org.thvc.happyi.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/23.
 */
public class SearchListBean {

    /**
     * status : 1
     * data : [{"id":"34","title":"爱周末&定向越野急救技能大赛"},{"id":"46","title":"李锐带你爱周末"},{"id":"49","title":"爱周末儿童安全自护成长营"}]
     * info :
     */

    private int status;
    private String info;
    /**
     * id : 34
     * title : 爱周末&定向越野急救技能大赛
     */

    private List<DataEntity> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String id;
        private String title;

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
