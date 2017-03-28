package org.thvc.happyi.Release;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.thvc.happyi.DB.DbOpenHelper;


/**
 * Created by Administrator on 2015/9/11.
 * 活动现场图片Dao
 */
public class PartySceneImageDao {

    private DbOpenHelper dbOpenHelper;

    public PartySceneImageDao(Context context) {
        this.dbOpenHelper = new DbOpenHelper(context);
    }

    /**
     * 添加图片表数据
     *
     * @param partySceneImage
     */
    public void imageInsert(PartySceneImage partySceneImage) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into happyi_partyscene_image values(?,?,?,?,?,?)", new Object[]{
                partySceneImage.getUserid(), partySceneImage.getFootprintid(), partySceneImage.getFilePath(),
                partySceneImage.getPathlist(), partySceneImage.getStatus(), partySceneImage.getCurrentTime()
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
    public PartySceneImage select(String userid, String footprintid) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from happyi_partyscene_image where userid = ? and footprintid = ? and status=0 LIMIT 1"
                , new String[]{userid, footprintid});
        PartySceneImage partySceneImage = null;
        if (c != null && c.moveToNext()) {
            String status = c.getString(c.getColumnIndex("status"));
            String userids = c.getString(c.getColumnIndex("userid"));
            String footprintids = c.getString(c.getColumnIndex("footprintid"));
            String filePath = c.getString(c.getColumnIndex("filePath"));
            String currentTime = c.getString(c.getColumnIndex("addtime"));
            String pathlist = c.getString(c.getColumnIndex("pathlist"));
            partySceneImage = new PartySceneImage(userids, footprintids, filePath, pathlist, status, currentTime);
        }
        c.close();
        db.close();
        return partySceneImage;
    }

    /**
     * 图片更新
     *
     * @param userid
     * @param filePath
     * @param status
     */
    public void imageUpdate(String userid, String filePath, String status) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update happyi_partyscene_image set status =? where userid = ? and filePath = ?", new Object[]{status, userid, filePath});
        db.close();
    }
}
