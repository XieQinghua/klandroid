package org.thvc.happyi.Release;

/**
 * Created by Administrator on 2016/1/16.
 * 活动现场文本bean
 */
public class PartySceneText {
    private String userid;
    private String footprintid;
    private String pathlist;
    private String status;

    public PartySceneText(String userid, String footprintid, String pathlist, String status) {
        this.userid = userid;
        this.footprintid = footprintid;
        this.pathlist = pathlist;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
