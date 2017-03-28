package org.thvc.happyi.Release;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.thvc.happyi.DB.DbOpenHelper;

/**
 * Created by Administrator on 2015/11/18.
 */
public class NgoDataDao {
    private DbOpenHelper dbOpenHelper;

    public NgoDataDao(Context context) {
        this.dbOpenHelper = new DbOpenHelper(context);

    }

    /**
     * 添加表数据
     *
     * @param ngoDataText
     */
    public void insert(NgoDataText ngoDataText) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into happyi_ngodata values(?,?,?,?,?,?,?,?,?)", new Object[]{
                ngoDataText.getUserid(), ngoDataText.getUsername(), ngoDataText.getRealname(), ngoDataText.getNickname(),
                ngoDataText.getAddress(), ngoDataText.getHeadpic(), ngoDataText.getOrgcontact(), ngoDataText.getOrgtel(), ngoDataText.getOrgemail()

        });
        db.close();
    }


    /**
     * 查询数据库
     *
     * @param userid
     * @param
     * @return
     */
    public NgoDataText select(String userid) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor c = null;
        NgoDataText ngoDataText = null;
        try {
            c = db.rawQuery("select * from happyi_ngodata where userid = ?  LIMIT 1", new String[]{userid});
            if (c.moveToNext()) {
                String username = c.getString(c.getColumnIndex("username"));
                String realname = c.getString(c.getColumnIndex("realname"));
                String nickname = c.getString(c.getColumnIndex("nickname"));
                String address = c.getString(c.getColumnIndex("address"));
                String headpic = c.getString(c.getColumnIndex("headpic"));
                String orgcontact = c.getString(c.getColumnIndex("orgcontact"));
                String orgtel = c.getString(c.getColumnIndex("orgtel"));
                String orgemail = c.getString(c.getColumnIndex("orgemail"));
                ngoDataText = new NgoDataText(userid, username, realname, nickname, address, headpic, orgcontact, orgtel, orgemail);
            }
            c.close();
        } catch (Exception e) {
            Log.e("NgoDataDao", e.toString());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        db.close();
        return ngoDataText;
    }

    /**
     * 修改NGO资料数据
     *
     * @param userid
     * @param ngoDataText
     */
    public void update(String userid, NgoDataText ngoDataText) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update happyi_ngodata set username=?,realname=?,nickname=?,address=?,headpic=?,orgcontact=?,orgtel=?,orgemail=? where userid = ? ",
                new Object[]{ngoDataText.getUsername(), ngoDataText.getRealname(), ngoDataText.getNickname(),
                        ngoDataText.getAddress(), ngoDataText.getHeadpic(), ngoDataText.getOrgcontact(),
                        ngoDataText.getOrgtel(), ngoDataText.getOrgemail(), userid});
        db.close();
    }

    /**
     * 清空表
     *
     * @param userid
     */
    public void delete(String userid) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("happyi_ngodata", "userid=?", new String[]{userid});
//        db.execSQL("delete from happyi_ngodata where userid=?", new Object[]{userid});
        db.close();
    }
}
