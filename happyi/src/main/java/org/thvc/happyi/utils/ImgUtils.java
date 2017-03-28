package org.thvc.happyi.utils;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.thvc.happyi.R;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.view.CircleImageView;

import java.text.SimpleDateFormat;

/**
 * 项目名称：klandroid
 * 类描述：图片处理工具
 * 创建人：谢庆华.
 * 创建时间：2015/11/24 17:07
 * 修改人：huangxinqi
 * 修改时间：2015/11/30 12:07
 * 修改备注：添加了两个方法，分别获取矩形和圆形图片，但是url只有HappyiApi.QINIU，没有加参数
 */
public class ImgUtils {
    /**
     * 用户圆图片头像显示方法，如果图片修改，会实时更新
     *
     * @param view    注意！此控件必须为CircleImageView
     * @param solevar
     */
    public static void setCircleImage(CircleImageView view, String solevar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");//如果图片修改，会实时更新
        String time = sdf.format(new java.util.Date());
        String url = HappyiApi.QINIU + "avatar/" + solevar + ".jpg-Thumb200?" + time;
        view.setImageUrl(url, VolleyHepler.getInstance().getImageLoader());
    }

    /**
     * 头像显示方法，传七牛图片路径
     *
     * @param view 注意！此控件必须为CircleImageView
     * @param head 服务器返回的head或者headpic七牛路径
     */
    public static void setHeadImage(CircleImageView view, String head) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");//实时更新
        String time = sdf.format(new java.util.Date());
        String url = HappyiApi.QINIU + head + "-Thumb200" + "?" + time;
        view.setImageUrl(url, VolleyHepler.getInstance().getImageLoader());
    }

    /**
     * 关注的活动的Item中Ngo列表头像显示方法
     *
     * @param view 注意！此控件必须为CircleImageView
     * @param head 服务器返回的head或者headpic七牛路径
     */
    public static void setNGOHeadImage(CircleImageView view, String head) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");//如果图片更换则需要1小时刷新
        String time = sdf.format(new java.util.Date());
        String url = HappyiApi.QINIU + head + "-Thumb200";
        view.setImageUrl(url, VolleyHepler.getInstance().getImageLoader());
    }

    /**
     * 用于活动主题图片显示，如果图片更换则需要1小时刷新，长高比3:2
     *
     * @param imageView 加载图片的imageView
     * @param subject   服务器返回的subject字段七牛路径
     */
    private static com.nostra13.universalimageloader.core.ImageLoader imageLoader;//加载矩形图像imageLoader

    public static void setRectangleImage(ImageView imageView, String subject) {
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");//如果图片更换则需要1小时刷新
        String time = sdf.format(new java.util.Date());
        String url = HappyiApi.QINIU + subject + "-Thumb640" + "?" + time;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_empty_default)         //加载开始默认的图片
                .showImageForEmptyUri(R.drawable.img_empty_default)  //url爲空會显示该图片，自己放在drawable里面的
                .showImageOnFail(R.drawable.img_empty_default)       //加载图片出现问题，会显示该图片
                .cacheInMemory()                                     //缓存用
                .cacheOnDisc()                                       //缓存用
                .build();
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.displayImage(url, imageView, options);
    }

    public static void setRectangleImages(ImageView imageView, String subject) {
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        String url = "http://www.happiyi.com/" + subject;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_empty_default)         //加载开始默认的图片
                .showImageForEmptyUri(R.drawable.img_empty_default)  //url爲空會显示该图片，自己放在drawable里面的
                .showImageOnFail(R.drawable.img_empty_default)       //加载图片出现问题，会显示该图片
                .cacheInMemory()                                     //缓存用
                .cacheOnDisc()                                       //缓存用
                .build();
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.displayImage(url, imageView, options);
    }


    /**
     * 显示原图，用于查看活动现场图集
     *
     * @param imageView
     * @param subject
     */
    public static void setRectangleImageRaw(ImageView imageView, String subject) {
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        String url = HappyiApi.QINIU + subject;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_empty_default)
                .showImageForEmptyUri(R.drawable.img_empty_default)
                .showImageOnFail(R.drawable.img_empty_default)
                .cacheInMemory()
                .cacheOnDisc()
                .build();
        imageLoader.displayImage(url, imageView, options);
    }

    /**
     * 获取矩形图片
     * 七牛url没有后缀
     */
    public static void setImageView(ImageView view, String picUrl) {
        String url = HappyiApi.QINIU + picUrl + "-Thumb200";
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_empty_default)         //加载开始默认的图片
                .showImageForEmptyUri(R.drawable.img_empty_default)  //url爲空會显示该图片，自己放在drawable里面的
                .showImageOnFail(R.drawable.img_empty_default)       //加载图片出现问题，会显示该图片
                .cacheInMemory()                                     //缓存用
                .cacheOnDisc()                                       //缓存用
                .build();
        imageLoader.displayImage(url, view, options);
    }


    /**
     * 获取圆形图片
     * 七牛url没有后缀
     */
    public static void setCircleImageView(CircleImageView view, String picUrl) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");//实时更新
        String time = sdf.format(new java.util.Date());
        String url = HappyiApi.QINIU + picUrl + "-Thumb200" + "?" + time;
        view.setImageUrl(url, VolleyHepler.getInstance().getImageLoader());
    }
}
