package org.thvc.happyi.Release;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.thvc.happyi.DB.DbOpenHelper;

/**
 * Created by Administrator on 2015/11/23.
 */
public class FounDataDao {
    private DbOpenHelper dbOpenHelper;

    public FounDataDao(Context context) {
        this.dbOpenHelper = new DbOpenHelper(context);
    }

    /**
     * 添加表数据
     *
     * @param founDataText
     */
    public void insert(FounDataText founDataText) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into happyi_foundationdata values(?,?,?,?,?,?,?)", new Object[]{
                founDataText.getUserid(), founDataText.getHeadpic(), founDataText.getNickname(),
                founDataText.getAddress(), founDataText.getOrgcontact(), founDataText.getOrgemail()
                , founDataText.getOrgtel()
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
    public FounDataText select(String userid) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor c = null;
        FounDataText foundationDataText = null;
        try {
            c = db.rawQuery("select * from happyi_foundationdata where userid = ?  LIMIT 1", new String[]{userid});
            if (c.moveToNext()) {
                String headpic = c.getString(c.getColumnIndex("headpic"));
                String nickname = c.getString(c.getColumnIndex("nickname"));
                String address = c.getString(c.getColumnIndex("address"));
                String orgcontact = c.getString(c.getColumnIndex("orgcontact"));
                String orgemail = c.getString(c.getColumnIndex("orgemail"));
                String orgtel = c.getString(c.getColumnIndex("orgtel"));
                foundationDataText = new FounDataText(userid, headpic, nickname, address, orgcontact, orgemail, orgtel);
            }
            c.close();
        } catch (Exception e) {
            Log.e("FounDataDao", e.toString());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        db.close();
        return foundationDataText;
    }

    /**
     * 修改个人资料数据
     *
     * @param userid
     */
    public void update(String userid, FounDataText founDataText) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update happyi_foundationdata set nickname=?, address=?, orgcontact=?, orgemail=?, orgtel=?, headpic=? where userid = ? ",
                new Object[]{founDataText.getNickname(), founDataText.getAddress(), founDataText.getOrgcontact(),
                        founDataText.getOrgemail(), founDataText.getOrgtel(), founDataText.getHeadpic(), userid});
        db.close();
    }

    /**
     * 清空表
     *
     * @param userid
     */
    public void delete(String userid) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("happyi_foundationdata", "userid=?", new String[]{userid});
        db.close();
    }
}
