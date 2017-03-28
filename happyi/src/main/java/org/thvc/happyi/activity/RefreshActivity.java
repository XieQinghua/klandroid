package org.thvc.happyi.activity;

import android.app.ProgressDialog;
import android.widget.Toast;

import org.thvc.happyi.base.BaseSwipeBackActivity;

/**
 * 用于刷新的activity，这个activity目前只有FansActivity用到了，用处不是很大了，可以直接把刷新逻辑写在FansAcitivity里面
 * Created by huangxinqi
 * on 2015/11/27-10:51.
 */
public class RefreshActivity extends BaseSwipeBackActivity {
    ProgressDialog progressDialog;
    //页码
    protected int pageIndex = 1;
    //每页数量
    protected int pageNum = 4;

    //获取数据的类型：LOAD：第一次进入时加载，REFRESH:下拉刷新，LOADMORE:上拉加载
    protected final static int LOAD = 1000;
    protected final static int REFRESH = 2000;
    protected final static int LOADMORE = 3000;


    protected void getDataList(final int pageIndex, int pageNum, final int type){

    }
    public void firstLoad(int pageNum,int type){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("正在加载中...");
        progressDialog.show();
        getDataList(1, pageNum, type);
        progressDialog.dismiss();
    }
    public void refreshDataList() {
        pageIndex=1;
        getDataList(pageIndex, pageNum, REFRESH);
    }

    public void loadDataList() {
        pageIndex+=1;
        getDataList(pageIndex, pageNum, LOADMORE);
    }
    public void refreshSuccess(){
        Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_SHORT).show();
    }
    public void loadSuccess(int dataListSize){
//        Toast.makeText(getApplicationContext(), "加载了" + dataListSize + "条数据", Toast.LENGTH_SHORT).show();
    }
}
