package org.thvc.happyi.DB;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 类描述：创建SQLite  缓存发布数据
 * 创建人：颜松梁.
 * 创建时间：2015/11/17.
 * 修改人：Administrator
 * 修改时间：2015/11/17.
 * 修改备注：
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(Context context) {
        // 版本不要为0开始
        super(context, "happyi.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //普通用户数据
        db.execSQL("create table happyi_data (userid varchar(32),system varchar(32),username varchar(32)," +
                "mobile varchar(32),nickname varchar(32),age varchar(32),birthday varchar(32),sex varchar(32),content varchar(32),headpic varchar(32),realname varchar(32),email varchar(32),job varchar(32),idcard varchar(32))");
        //NGO用户数据
        db.execSQL("create table happyi_ngodata(userid varchar(32),username varchar(32),realname varchar(32),nickname varchar(32),address varchar(32)," +
                "headpic varchar(32),orgcontact varchar(32),orgtel varchar(32),orgemail varchar(32))");
        //基金会用户数据
        db.execSQL("create table happyi_foundationdata(userid varchar(32),headpic varchar(32),nickname varchar(32),address varchar(32),orgcontact varchar(32)," +
                "orgemail varchar(32),orgtel varchar(32) )");

        /**
         *  status 0 未发布  1正在发布 2发布失败 3发布成功 4 未开启上传
         *  userid 操作用户ID
         *  footprintid  缓存对象ID
         *  content 缓存对象数据-Json格式
         *  pathlist 对象的七牛图片路径 多图使用都好拼接
         *  addtime 创建日期
         */
        //活动现场图片缓存
        db.execSQL("create table happyi_partyscene_image (userid varchar(32),footprintid varchar(32),filePath varchar(500),pathlist text,status varchar(5),addtime date)");
        //活动现场文本缓存
        db.execSQL("create table happyi_partyscene_text (userid varchar(32),footprintid varchar(32),pathlist text,status varchar(5))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("create table happyi_partyscene_image (userid varchar(32),footprintid varchar(32),filePath varchar(500),pathlist text,status varchar(5),addtime date)");
            db.execSQL("create table happyi_partyscene_text (userid varchar(32),footprintid varchar(32),pathlist text,status varchar(5))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
