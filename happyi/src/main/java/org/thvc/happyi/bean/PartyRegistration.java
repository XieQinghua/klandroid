package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2015/12/5.
 */
public class PartyRegistration {

    /**
     * data : {"getpre":"15.00","prefee":"16.00","acttitle":"持行岳麓山公益环保","actid":"30","safe":"1","fundname":"芒果V基金"}
     * status : 1
     * info : 活动报名
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
         * getpre : 15.00
         * prefee : 16.00
         * acttitle : 持行岳麓山公益环保
         * actid : 30
         * safe : 1
         * fundname : 芒果V基金
         */
        private String getpre;
        private String prefee;
        private String acttitle;
        private String actid;
        private String safe;
        private String fundname;

        public void setGetpre(String getpre) {
            this.getpre = getpre;
        }

        public void setPrefee(String prefee) {
            this.prefee = prefee;
        }

        public void setActtitle(String acttitle) {
            this.acttitle = acttitle;
        }

        public void setActid(String actid) {
            this.actid = actid;
        }

        public void setSafe(String safe) {
            this.safe = safe;
        }

        public void setFundname(String fundname) {
            this.fundname = fundname;
        }

        public String getGetpre() {
            return getpre;
        }

        public String getPrefee() {
            return prefee;
        }

        public String getActtitle() {
            return acttitle;
        }

        public String getActid() {
            return actid;
        }

        public String getSafe() {
            return safe;
        }

        public String getFundname() {
            return fundname;
        }
    }
}