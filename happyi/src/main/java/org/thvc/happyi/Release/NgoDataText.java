package org.thvc.happyi.Release;

/**
 * Created by Administrator on 2015/11/18.
 */
public class NgoDataText {
    private String userid;
    private String username;
    private String realname;
    private String nickname;
    private String address;
    private String headpic;
    private String orgcontact;
    private String orgtel;
    private String orgemail;

    public NgoDataText(String userid, String username, String realname, String nickname, String address, String headpic, String orgcontact, String orgtel, String orgemail) {
        this.userid = userid;
        this.username = username;
        this.realname = realname;
        this.nickname = nickname;
        this.address = address;
        this.headpic = headpic;
        this.orgcontact = orgcontact;
        this.orgtel = orgtel;
        this.orgemail = orgemail;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getOrgcontact() {
        return orgcontact;
    }

    public void setOrgcontact(String orgcontact) {
        this.orgcontact = orgcontact;
    }

    public String getOrgtel() {
        return orgtel;
    }

    public void setOrgtel(String orgtel) {
        this.orgtel = orgtel;
    }

    public String getOrgemail() {
        return orgemail;
    }

    public void setOrgemail(String orgemail) {
        this.orgemail = orgemail;
    }
}
