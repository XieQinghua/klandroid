package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2016/3/29.
 */
public class SetInFoBean {
    /**
     * status : 1
     * data : {"id":"3","ncount":40,"nmin":2,"nmax":200,"ocount":60,"omin":2,"omax":200}
     * info : 操作成功
     * version : 1.3
     */

    private int status;
    /**
     * id : 3
     * ncount : 40
     * nmin : 2
     * nmax : 200
     * ocount : 60
     * omin : 2
     * omax : 200
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
        private String id;
        private int ncount;
        private int nmin;
        private int nmax;
        private int ocount;
        private int omin;
        private int omax;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNcount() {
            return ncount;
        }

        public void setNcount(int ncount) {
            this.ncount = ncount;
        }

        public int getNmin() {
            return nmin;
        }

        public void setNmin(int nmin) {
            this.nmin = nmin;
        }

        public int getNmax() {
            return nmax;
        }

        public void setNmax(int nmax) {
            this.nmax = nmax;
        }

        public int getOcount() {
            return ocount;
        }

        public void setOcount(int ocount) {
            this.ocount = ocount;
        }

        public int getOmin() {
            return omin;
        }

        public void setOmin(int omin) {
            this.omin = omin;
        }

        public int getOmax() {
            return omax;
        }

        public void setOmax(int omax) {
            this.omax = omax;
        }
    }
}
