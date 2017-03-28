package org.thvc.happyi.utils;

import com.lidroid.xutils.http.RequestParams;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Administrator on 2015/11/05.
 * 加密和网络请求的封装
 */
public class MyRequestParams extends RequestParams {
    private RequestParam param;
    private List<RequestParam> list;

    public String myRequestParams(RequestParams params) {
        RequestParams ss = params;
        java.util.List<org.apache.http.NameValuePair> getQueryStringParams = ss.getQueryStringParams();//拿到动态的所有参数
        list = new ArrayList<>();//序列化集合对象
        list.add(new RequestParam("_deviceid", "123456"));
        list.add(new RequestParam("_model", "android"));
        list.add(new RequestParam("_system", "android"));
        list.add(new RequestParam("app_id", "api_app"));
        list.add(new RequestParam("app_key", "d3a9c17d9284f979"));
        for (int i = 0; i < getQueryStringParams.size(); i++) {//遍历动态参数，添加到对象里面
            String paramsname = getQueryStringParams.get(i).getName();
            String paramsvalue = getQueryStringParams.get(i).getValue();
            param = new RequestParam(paramsname, paramsvalue);
            list.add(param);
        }
        String strlist = "&";
        Collections.sort(list, new Sort());
        Iterator<RequestParam> iterator = list.iterator();
        while (iterator.hasNext()) {
            RequestParam test = iterator.next();
//            try {
//                name = URLEncoder.encode(test.getName(), "UTF-8");
//                value = URLEncoder.encode(test.getValue(), "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            strlist += (test.getName() + "=" + test.getValue()) + "&";//动态参数的拼接
        }
//        String str = strlist.replaceAll("%","%25").toString().trim();
        String keys = strlist.substring(1, strlist.length() - 1);//去前面&和后面&
        //截取前3个字符
        String strs = keys.substring(3, keys.length());
        String strkey = keys.substring(0, 3);
        //MD5加密
        String _sign = getMD5(strs + "x-)a#2(^9" + strkey);
        String dlappkey = "";
        dlappkey = keys.replaceAll("app_key=d3a9c17d9284f979&", "");
        //拼接完整的URL
//        String url = null;
//        try {
//            url = URLDecoder.decode(sssssaa, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String url = dlappkey.toString().trim() + "&_sign" + "=" + _sign;
        return url;
    }

    /**
     * java语言md5加密
     *
     * @param info
     * @return
     */
    public String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
