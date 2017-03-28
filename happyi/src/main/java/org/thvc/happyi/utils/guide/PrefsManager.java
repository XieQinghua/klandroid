package org.thvc.happyi.utils.guide;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * create by huangxinqi
 * 当每次新手指引出现之后对应在sharedprefrence里产生一个标识：showcaseID
 * 拥有标识的新手指引将不再出现
 * 除非使用reset_xx方法来清除掉才行
 */
public class PrefsManager {

    public static int SEQUENCE_NEVER_STARTED = 0;
    public static int SEQUENCE_FINISHED = -1;


    private static final String PREFS_NAME = "material_showcaseview_prefs";
    private static final String STATUS = "status_";
    private String showcaseID = null;
    private Context context;

    public PrefsManager(Context context, String showcaseID) {
        this.context = context;
        this.showcaseID = showcaseID;
    }


    /**
     * 单个步骤的新手指引
     */
    boolean hasFired() {
        int status = getSequenceStatus();
        return (status == SEQUENCE_FINISHED);
    }

    void setFired() {
        setSequenceStatus(SEQUENCE_FINISHED);
    }

    /**
     * 多个步骤的新手指引
     */
    int getSequenceStatus() {
        return context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(STATUS + showcaseID, SEQUENCE_NEVER_STARTED);

    }

    void setSequenceStatus(int status) {
        SharedPreferences internal = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        internal.edit().putInt(STATUS + showcaseID, status).apply();
    }


    public void resetShowcase() {
        resetShowcase(context, showcaseID);
    }

    static void resetShowcase(Context context, String showcaseID) {
        SharedPreferences internal = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        internal.edit().putInt(STATUS + showcaseID, SEQUENCE_NEVER_STARTED).apply();
    }

    public static void resetAll(Context context) {
        SharedPreferences internal = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        internal.edit().clear().apply();
    }

    public void close() {
        context = null;
    }
}
