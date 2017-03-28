package org.thvc.happyi.Release;

/**
 * Created by Administrator on 2015/11/23.
 */
public class FounDataText {
    private String userid;
    private String headpic;
    private String nickname;
    private String address;
    private String orgcontact;
    private String orgemail;
    private String orgtel;

    public FounDataText(String userid, String headpic, String nickname, String address, String orgcontact, String orgemail, String orgtel) {
        this.userid = userid;
        this.headpic = headpic;
        this.nickname = nickname;
        this.address = address;
        this.orgcontact = orgcontact;
        this.orgemail = orgemail;
        this.orgtel = orgtel;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
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

    public String getOrgcontact() {
        return orgcontact;
    }

    public void setOrgcontact(String orgcontact) {
        this.orgcontact = orgcontact;
    }

    public String getOrgemail() {
        return orgemail;
    }

    public void setOrgemail(String orgemail) {
        this.orgemail = orgemail;
    }

    public String getOrgtel() {
        return orgtel;
    }

    public void setOrgtel(String orgtel) {
        this.orgtel = orgtel;
    }
}
