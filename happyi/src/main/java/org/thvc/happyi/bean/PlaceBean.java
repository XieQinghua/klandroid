package org.thvc.happyi.bean;

import java.io.Serializable;

/**
 * Created by huangxinqi on 2016/1/23.
 * 位置信息
 */
public class PlaceBean implements Serializable{

    private static final long serialVersionUID = 6251259916004902085L;
    private String cityName;
    private double myLat;
    private double myLon;
    private double desLat;
    private double desLon;

    public String getCityName() {
        return cityName;
    }

    public PlaceBean setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public double getMyLat() {
        return myLat;
    }

    public PlaceBean setMyLat(double myLat) {
        this.myLat = myLat;
        return this;
    }

    public double getMyLon() {
        return myLon;
    }

    public PlaceBean setMyLon(double myLon) {
        this.myLon = myLon;
        return this;
    }

    public double getDesLat() {
        return desLat;
    }

    public PlaceBean setDesLat(double desLat) {
        this.desLat = desLat;
        return this;
    }

    public double getDesLon() {
        return desLon;
    }

    public PlaceBean setDesLon(double desLon) {
        this.desLon = desLon;
        return this;
    }
}
