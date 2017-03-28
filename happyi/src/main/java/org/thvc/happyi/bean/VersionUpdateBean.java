package org.thvc.happyi.bean;

/**
 * Created by Administrator on 2016/1/6.
 */
public class VersionUpdateBean {
    /**
     * version : 2.0
     * url : https://www.baidu.com/
     */
    private String version;
    private String url;
    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }
}
