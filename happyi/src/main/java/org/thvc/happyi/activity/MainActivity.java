package org.thvc.happyi.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.FragmentTabAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseActivity;
import org.thvc.happyi.fragment.FoundationFragment;
import org.thvc.happyi.fragment.HomeFragment;
import org.thvc.happyi.fragment.NgoMineFragment;
import org.thvc.happyi.fragment.OrdinaryMineFragment;
import org.thvc.happyi.fragment.OrganizationFragment;
import org.thvc.happyi.receiver.NetReceiver;
import org.thvc.happyi.utils.ExampleUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 主程序MainActivity，实现形式RadioGroup+Fragment
 */
public class MainActivity extends BaseActivity {

    private ArrayList<Fragment> fragments;
    private RadioGroup myRadioGroup;
    private String system;
    NetReceiver mReceiver = new NetReceiver();
    IntentFilter mFilter = new IntentFilter();


    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    private String userid;
    private String string;

    /* 更新进度条 */
    private ProgressBar mProgress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    private Dialog mDownloadDialog;
    /* 记录进度条数量 */
    private int progress;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;

    public static final String UPDATE_APKNAME = "org.thvc.happyi.apk";

    private String Version;

    private String contexts;

    private SharedPreferences sp;

    private String VersionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(mReceiver, mFilter);
        system = HappyiApplication.getInstance().getSystem(this);
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        init();
        new FragmentTabAdapter(this, getSupportFragmentManager(), fragments, R.id.content, myRadioGroup);
//        setAlias();
        registerMessageReceiver();
        string = getIntent().getStringExtra("string");
        if (string != null) {
            String contexts = getIntent().getStringExtra("contexts");
            showNoNetWorkDlg(this, contexts);
        } else {
            VersionName = getVersionName(this);
            Version = sp.getString("Version", "");
            if (Version.equals("")) {
            } else {
                contexts = sp.getString("contexts", "");
                string = sp.getString("url", "");
                if (VersionName.equals(Version)) {
                } else {
                    showNoNetWorkDlg(this, contexts);
                }
            }
        }
        /**！！！活动现场功能已经取消*/
//        DbOpenHelper dbOpenHelper = new DbOpenHelper(this);
//        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//        boolean result = checkColumnExist1(db, "happyi.db", "happyi_partyscene_image");
//        if (result == false) {
//            dbOpenHelper.onUpgrade(db, 1, 2);
//            dbOpenHelper.close();
//        }
    }


    private String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(mReceiver);
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    private void init() {
        myRadioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new OrganizationFragment());
        if (!system.equals("")) {
            if (system.equals("2")) {
                fragments.add(new OrdinaryMineFragment());
            } else if (system.equals("3")) {
                fragments.add(new NgoMineFragment());
            } else if (system.equals("4")) {
                fragments.add(new FoundationFragment());
            }
        } else {
            fragments.add(new Fragment());
        }

    }


    /**
     * 方法1：检查某表列是否存在
     *
     * @param db
     * @param tableName  表名
     * @param columnName 列名
     * @return
     */
    private boolean checkColumnExist1(SQLiteDatabase db, String tableName, String columnName) {
        boolean result = false;
        Cursor cursor = null;
        try {
            //查询一行
            cursor = db.rawQuery("select * from " + tableName + " limit 1", null);
            if (cursor.getColumnIndex(columnName) != -1) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    //动态注册广播
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }


    /**
     * 极光推送设置别名
     */
// 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias() {
        String alias = "123456abc";
        if (TextUtils.isEmpty(alias)) {
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            return;
        }
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
            }
        }
    };


    public void showNoNetWorkDlg(final Context context, String contexts) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.show();
        //点击屏幕外侧，dialog不消失
        dialog.setCanceledOnTouchOutside(false);
        TextView tv_message = (TextView) dialogView.findViewById(R.id.tv_message);
        tv_message.setText(("发现新版本，是否立即更新" + "\n" + contexts).replaceAll("；", "；\n"));
        dialogView.findViewById(R.id.negativeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button positiveButton = (Button) dialogView.findViewById(R.id.positiveButton);
        positiveButton.setText("立即更新");
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDownloadDialog();
                dialog.dismiss();
            }
        });
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new Builder(MainActivity.this);
        builder.setTitle("正在更新");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }


    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    URL url = new URL(string);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File apkFile = new File(Environment.getExternalStorageDirectory(), UPDATE_APKNAME);
                    // 判断文件目录是否存在
                    if (apkFile.exists()) {
                        apkFile.delete();
                    }
//                    File apkFile = new File(mSavePath, "快乐益");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandlers.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandlers.sendEmptyMessage(DOWNLOAD_FINISH);

                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    }

    ;

    /**
     * 安装APK文件
     */
    private void installApk() {
        mDownloadDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), UPDATE_APKNAME)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private Handler mHandlers = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 主页监听手机返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finishAll();
        }
        return false;
    }
}
