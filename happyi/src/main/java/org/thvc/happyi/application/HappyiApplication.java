package org.thvc.happyi.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * 项目名称：klandroid
 * 类描述：App单例模式类，获取该类内容使用HappyiApplication.getInstance()方法
 * 创建人：谢庆华.
 * 创建时间：2015/11/5 14:40
 * 修改人：Administrator
 * 修改时间：2015/11/5 14:40
 * 修改备注：
 */
public class HappyiApplication extends Application {
    public static Context applicationContext;
    private static HappyiApplication instance;
    private ArrayList<Activity> activities = new ArrayList<Activity>();


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;
        instance = this;
        /**
         * 腾讯Bugly插件初始化
         */
        CrashReport.initCrashReport(this, "900013125", false);
//        CrashReport.testJavaCrash(); // Bugly测试，解除注释将产生一个测试Crash
        /**
         * 极光推送
         */
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        /**
         * 初始化ImageLoader
         */
        initImageLoader();
    }

    public static HappyiApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return applicationContext;
    }

    private void initImageLoader() {
        //缓存设置
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory()  //1.8.6包使用时候，括号里面传入参数true
                .cacheOnDisc()    //同上
                .build();
        //参数配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                .defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 保存登录返回参数
     *
     * @param context   上下文
     * @param autologin 自动登录标识
     * @param system    用户类型
     * @param username  用户名
     * @param password  密码
     * @param userid    用户id
     * @param nickname  昵称
     * @param solevar   用户编号
     * @param loginjson 登录返回json
     */
    public void saveParam(Context context, boolean autologin, String system, String username, String password, String userid, String nickname, String solevar, String loginjson) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        sp.edit().putBoolean("autologin", autologin)
                .putString("system", system)
                .putString("username", username)
                .putString("password", password)
                .putString("userid", userid)
                .putString("nickname", nickname)
                .putString("solevar", solevar)
                .putString("loginjson", loginjson)
                .commit();
    }


    private Boolean autologin = false;
    private String system;
    private String username;
    private String password;
    private String userid;
    private String nickname;
    private String solevar;
    private String loginjson;


    public Boolean getAutologin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        autologin = sp.getBoolean("autologin", false);
        return autologin;
    }

    public void setAutologin(Context con, boolean autologin) {
        this.autologin = autologin;
        SharedPreferences sp = con.getSharedPreferences("loginparam",
                Activity.MODE_PRIVATE);
        sp.edit().putBoolean("autologin", autologin).commit();
    }

    public String getSystem(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        system = sp.getString("system", "");
        return system;
    }

    public String getUsername(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        username = sp.getString("username", "");
        return username;
    }

    public String getPassword(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        password = sp.getString("password", "");
        return password;
    }

    public String getNickname(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        nickname = sp.getString("nickname", "");
        return nickname;
    }

    public String getSolevar(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        solevar = sp.getString("solevar", "");
        return solevar;
    }

    public String getUserid(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        userid = sp.getString("userid", "");
        return userid;
    }

    public String getLoginjson(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        loginjson = sp.getString("loginjson", "");
        return loginjson;
    }


    /**
     * 清除保存的用户数据，注销登录需调用此方法
     *
     * @param context
     */
    public void clearsaveParam(Context context) {
        SharedPreferences sp = context.getSharedPreferences("loginparam", Activity.MODE_PRIVATE);
        sp.edit().clear();
        sp.edit().commit();
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void deleteActivity(Activity activity) {
        activities.remove(activity);
    }

    //finish
    public void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }


}