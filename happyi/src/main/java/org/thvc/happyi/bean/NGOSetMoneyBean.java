package org.thvc.happyi.bean;

/**
 * Created by huangxinqi on 2016/3/30.
 */
public class NGOSetMoneyBean {

    /**
     * status : 1
     * data : {"id":15,"getpre":0,"getopre":0,"isdel":6}
     * info :
     * version : 1.3
     */

    private int status;
    /**
     * id : 15
     * getpre : 0
     * getopre : 0
     * isdel : 6
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
        private int id;
        private int getpre;
        private int getopre;
        private int isdel;

        public void setId(int id) {
            this.id = id;
        }

        public void setGetpre(int getpre) {
            this.getpre = getpre;
        }

        public void setGetopre(int getopre) {
            this.getopre = getopre;
        }

        public void setIsdel(int isdel) {
            this.isdel = isdel;
        }

        public int getId() {
            return id;
        }

        public int getGetpre() {
            return getpre;
        }

        public int getGetopre() {
            return getopre;
        }

        public int getIsdel() {
            return isdel;
        }
    }
}
