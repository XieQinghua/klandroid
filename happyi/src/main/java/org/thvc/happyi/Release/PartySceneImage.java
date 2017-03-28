package org.thvc.happyi.Release;


/**
 * Created by Administrator on 2015/9/11.
 * 活动现场单张图片bean
 */
public class PartySceneImage {
    private String userid;
    private String footprintid;
    private String filePath;
    private String pathlist;
    private String status;
    private String currentTime;

    public PartySceneImage(String userid, String footprintid, String filePath, String pathlist, String status, String currentTime) {
        this.userid = userid;
        this.footprintid = footprintid;
        this.filePath = filePath;
        this.pathlist = pathlist;
        this.status = status;
        this.currentTime = currentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFootprintid() {
        return footprintid;
    }

    public void setFootprintid(String footprintid) {
        this.footprintid = footprintid;
    }

    public String getPathlist() {
        return pathlist;
    }

    public void setPathlist(String pathlist) {
        this.pathlist = pathlist;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }


}
