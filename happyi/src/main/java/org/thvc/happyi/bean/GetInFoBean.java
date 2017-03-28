package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2016/3/29.
 */
public class GetInFoBean {

    /**
     * status : 1
     * data : {"ncount":"5","nmin":"6","nmax":"7","ocount":"2","omin":"4","omax":"3"}
     * info :
     * version : 1.3
     */

    private int status;
    /**
     * ncount : 5
     * nmin : 6
     * nmax : 7
     * ocount : 2
     * omin : 4
     * omax : 3
     */

    private DataBean data;
    private String info;
    private String version;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class DataBean {
        private String ncount;
        private String nmin;
        private String nmax;
        private String ocount;
        private String omin;
        private String omax;

        public String getNcount() {
            return ncount;
        }

        public void setNcount(String ncount) {
            this.ncount = ncount;
        }

        public String getNmin() {
            return nmin;
        }

        public void setNmin(String nmin) {
            this.nmin = nmin;
        }

        public String getNmax() {
            return nmax;
        }

        public void setNmax(String nmax) {
            this.nmax = nmax;
        }

        public String getOcount() {
            return ocount;
        }

        public void setOcount(String ocount) {
            this.ocount = ocount;
        }

        public String getOmin() {
            return omin;
        }

        public void setOmin(String omin) {
            this.omin = omin;
        }

        public String getOmax() {
            return omax;
        }

        public void setOmax(String omax) {
            this.omax = omax;
        }
    }
}
