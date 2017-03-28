package org.thvc.happyi.Release;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.thvc.happyi.DB.DbOpenHelper;

/**
 * Created by Administrator on 2015/11/17.
 */
public class DataDao {
    private DbOpenHelper dbOpenHelper;

    public DataDao(Context context) {
        this.dbOpenHelper = new DbOpenHelper(context);
    }

    /**
     * 添加表数据
     *
     * @param dataText
     */
    public void insert(DataText dataText) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into happyi_data values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
                dataText.getUserid(), dataText.getSystem(), dataText.getUsername(),
                dataText.getMobile(), dataText.getNickname(), dataText.getAge(), dataText.getBirthday(),
                dataText.getSex(), dataText.getContent(), dataText.getHeadpic(), dataText.getRealname(),
                dataText.getEmail(), dataText.getJob(), dataText.getIdcard()});
        db.close();
    }


    /**
     * 查询数据库
     *
     * @param userid
     * @param
     * @return
     */
    public DataText select(String userid) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor c = null;
        DataText dataText = null;
        try {
            c = db.rawQuery("select * from happyi_data where userid = ?  LIMIT 1", new String[]{userid});
            if (c.moveToNext()) {
                String system = c.getString(c.getColumnIndex("system"));
                String username = c.getString(c.getColumnIndex("username"));
                String mobile = c.getString(c.getColumnIndex("mobile"));
                String nickname = c.getString(c.getColumnIndex("nickname"));
                String age = c.getString(c.getColumnIndex("age"));
                String birthday = c.getString(c.getColumnIndex("birthday"));
                String sex = c.getString(c.getColumnIndex("sex"));
                String content = c.getString(c.getColumnIndex("content"));
                String headpic = c.getString(c.getColumnIndex("headpic"));
                String realname = c.getString(c.getColumnIndex("realname"));
                String email = c.getString(c.getColumnIndex("email"));
                String job = c.getString(c.getColumnIndex("job"));
                String idcard = c.getString(c.getColumnIndex("idcard"));
                dataText = new DataText(userid, system, username, mobile, nickname, age, birthday, sex, content, headpic, realname, email, job, idcard);
            }
            c.close();
        } catch (Exception e) {
            Log.e("DataDao", e.toString());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        db.close();
        return dataText;
    }

    /**
     * 修改个人资料数据
     *
     * @param userid
     * @param dataText
     */
    public void update(String userid, DataText dataText) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update happyi_data set system=?,username=?,mobile = ?,nickname = ?,age=?,birthday = ?,sex = ?,content = ?,headpic= ?,realname = ?,email = ?,job = ?,idcard = ?  where userid = ? ",
                new Object[]{dataText.getSystem(), dataText.getUsername(), dataText.getMobile(), dataText.getNickname(),
                        dataText.getAge(), dataText.getBirthday(), dataText.getSex(),
                        dataText.getContent(), dataText.getHeadpic(), dataText.getRealname(),
                        dataText.getEmail(), dataText.getJob(), dataText.getIdcard(), userid});
        db.close();
    }

    /**
     * 清空表
     *
     * @param userid
     */
    public void delete(String userid) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("happyi_data", "userid=?", new String[]{userid});
//        db.execSQL("delete from happyi_data where userid=?", new Object[]{userid});
        db.close();
    }
}
