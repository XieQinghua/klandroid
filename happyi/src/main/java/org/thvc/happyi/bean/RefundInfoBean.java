package org.thvc.happyi.bean;

import java.io.Serializable;

/**
 * Created by huangxinqi
 * on 2015/12/14-15:45.
 */
public class RefundInfoBean implements Serializable{
    private static final long serialVersionUID = -5805074870212833337L;
    private String userid;
    private String dataid;
    private String joinid;
    private String reason;
    private String alipay;
    private String bank;
    private String cardno;
    private String tel;
    private String email;
    private String realname;

    public String getAlipay() {
        return alipay;
    }

    public RefundInfoBean setAlipay(String alipay) {
        this.alipay = alipay;
        return this;
    }

    public String getBank() {
        return bank;
    }

    public RefundInfoBean setBank(String bank) {
        this.bank = bank;
        return this;
    }

    public String getCardno() {
        return cardno;
    }

    public RefundInfoBean setCardno(String cardno) {
        this.cardno = cardno;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public RefundInfoBean setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RefundInfoBean setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRealname() {
        return realname;
    }

    public RefundInfoBean setRealname(String realname) {
        this.realname = realname;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public RefundInfoBean setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getDataid() {
        return dataid;
    }

    public RefundInfoBean setDataid(String dataid) {
        this.dataid = dataid;
        return this;
    }

    public String getJoinid() {
        return joinid;
    }

    public RefundInfoBean setJoinid(String joinid) {
        this.joinid = joinid;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RefundInfoBean setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
