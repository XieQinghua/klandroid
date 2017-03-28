package org.thvc.happyi.utils;

/**
 * Created by Administrator on 2015/11/05.
 */
public class RequestParam {

    private String name;
    private String value;

    public RequestParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
