package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2015/12/11.
 */
public class UpdataActivityBean {

    /**
     * status : 1
     * data : {"actid":"23"}
     * info : 活动报名
     */

    private String status;
    /**
     * actid : 23
     */

    private DataEntity data;
    private String info;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }

    public static class DataEntity {
        private String actid;

        public void setActid(String actid) {
            this.actid = actid;
        }

        public String getActid() {
            return actid;
        }
    }
}
