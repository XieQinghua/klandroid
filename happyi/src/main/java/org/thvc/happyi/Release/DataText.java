package org.thvc.happyi.Release;

/**
 * Created by Administrator on 2015/11/17.
 * 修改：huangxinqi
 * 添加空构造方法
 */
public class DataText {
    private String userid;
    private String system;
    private String username;
    private String mobile;
    private String nickname;
    private String age;
    private String birthday;
    private String sex;
    private String content;
    private String headpic;
    private String realname;
    private String email;
    private String job;
    private String idcard;


    public DataText() {

    }

    public DataText(String userid, String system, String username, String mobile, String nickname, String age, String birthday, String sex, String content, String headpic, String realname, String email, String job, String idcard) {
        this.userid = userid;
        this.system = system;
        this.username = username;
        this.mobile = mobile;
        this.nickname = nickname;
        this.age = age;
        this.birthday = birthday;
        this.sex = sex;
        this.content = content;
        this.headpic = headpic;
        this.email = email;
        this.realname = realname;
        this.job = job;
        this.idcard = idcard;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getUserid() {
        return userid;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSystem() {

        return system;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
