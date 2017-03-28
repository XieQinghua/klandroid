package org.thvc.happyi.fragment;

import android.app.ProgressDialog;

/**
 * Created by huangxinqi
 * on 2015/11/25-15:10.
 * 需要下拉刷新上拉加载的faramgent继承此fragment
 * 我写的Fragment基本继承此fragment
 * 以ColllectNGO为例子
 */
public abstract class RefreshFragment extends BaseFragment {
    ProgressDialog progressDialog;
    //页码
    protected int pageIndex = 1;
    //每页数量
    protected int pageNum = 10;

    //获取数据的类型：LOAD：第一次进入时加载，REFRESH:下拉刷新，LOADMORE:上拉加载
    public final static int LOAD = 1000;
    public final static int REFRESH = 2000;
    public final static int LOADMORE = 3000;


    /**
     * 获取服务器数据，每一个继承此类的fragment都要重写该方法，需要两个参数，一个是当前页码，另一个是类型：下拉刷新，第一次加载数据，加载更多数据
     *
     * @param pageIndex
     * @param type
     */
    protected void getDataList(final int pageIndex, final int type) {

    }

    /**
     * 第一次加载数据
     *
     * @param pageNum
     * @param type
     */
    public void firstLoad(int pageNum, int type) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在加载中...");
        progressDialog.show();
        getDataList(1, type);
        progressDialog.dismiss();
    }

    /**
     * 刷新数据，页码变为1
     */
    public void refreshDataList() {
        pageIndex = 1;
        getDataList(pageIndex, REFRESH);
    }

    /**
     * 加载数据，页码加1
     */
    public void loadDataList() {
        pageIndex += 1;
        getDataList(pageIndex, LOADMORE);
    }

    /**
     * 刷新成功后的操作可以写这里，但是现在不用Toast了
     */
    public void refreshSuccess() {
        //Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载成功后的操作可以写这里，但是现在不用Toast了
     *
     * @param dataListSize
     */
    public void loadSuccess(int dataListSize) {
        //Toast.makeText(getActivity(), "加载了" + dataListSize + "条数据", Toast.LENGTH_SHORT).show();
    }
}
