package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2015/12/22.
 */
public class ConcernBean {

    /**
     * status : 1
     * data : {"hascollect":"2"}
     * info : 关注
     */

    private String status;
    /**
     * hascollect : 2
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
        private String hascollect;

        public void setHascollect(String hascollect) {
            this.hascollect = hascollect;
        }

        public String getHascollect() {
            return hascollect;
        }
    }
}
