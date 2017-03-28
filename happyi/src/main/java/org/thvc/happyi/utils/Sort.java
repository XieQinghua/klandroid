package org.thvc.happyi.utils;

import java.util.Comparator;

/**
 * Created by huangxinqi on 2016/1/12.
 */
public class Sort implements Comparator<RequestParam> {

    @Override
    public int compare(RequestParam p1, RequestParam p2) {

        char[] a=p1.getName().toCharArray();
        char[] b=p2.getName().toCharArray();

        int length=Math.min(a.length,b.length);

        for (int i=0;i<length;i++){
            if (a[i]!=b[i]){
                return a[i]-b[i];
            }
        }
        return a.length-b.length;
    }
}
