package org.thvc.happyi.Release;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.thvc.happyi.DB.DbOpenHelper;

/**
 * Created by Administrator on 2016/1/16.
 * 活动现场文本Dao
 */
public class PartySceneTextDao {
    private DbOpenHelper dbOpenHelper;

    public PartySceneTextDao(Context context) {
        this.dbOpenHelper = new DbOpenHelper(context);
    }


    /**
     * 添加图片表数据
     *
     * @param footprints
     */
    public void textInsert(PartySceneText footprints) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into happyi_partyscene_text values(?,?,?,?)", new Object[]{
                footprints.getUserid(), footprints.getFootprintid(), footprints.getPathlist(), footprints.getStatus()
        });
        db.close();
    }

    /**
     * 查询数据库
     *
     * @param userid
     * @param footprintid
     * @return
     */
    public PartySceneText select(String userid, String footprintid) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from happyi_partyscene_text where userid = ? and footprintid = ? and status=0 LIMIT 1", new String[]{userid, footprintid});
        PartySceneText partySceneText = null;
        if (c != null && c.moveToNext()) {
            String status = c.getString(c.getColumnIndex("status"));
            String userids = c.getString(c.getColumnIndex("userid"));
            String footprintids = c.getString(c.getColumnIndex("footprintid"));
            String pathlist = c.getString(c.getColumnIndex("pathlist"));
            partySceneText = new PartySceneText(userids, footprintids, pathlist, status);
        }
        c.close();
        db.close();
        return partySceneText;
    }

    public void textUpdate(String userid, String footprintid, String pathlist) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update happyi_partyscene_text set pathlist = ? where userid = ? and footprintid = ?", new Object[]{pathlist, userid, footprintid});
        db.close();
    }
}
