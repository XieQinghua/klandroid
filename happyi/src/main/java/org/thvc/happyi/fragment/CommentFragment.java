package org.thvc.happyi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.CommentAdapter;
import org.thvc.happyi.bean.CommentBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.util.ArrayList;

/**
 * NGO的活动-》活动详情-》评论
 * Created by huangxinqi
 * on 2015/11/27-13:09.
 */
public class CommentFragment extends RefreshFragment {
    private ArrayList<CommentBean.DataEntity.ListEntity> list;
    private CommentAdapter adapter;
    private ListView lvComment;
    private String partyId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, null);
        partyId = getActivity().getIntent().getStringExtra("partyId");
        lvComment = (ListView) view.findViewById(R.id.lv_comment);
        firstLoad(pageNum, LOAD);
        return view;
    }

    @Override
    public void getDataList(final int pageIndex, final int type) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("dataid", partyId);
        params.addQueryStringParameter("p", pageIndex + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COMMENT_INDEX + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    CommentBean commentBean = ParseUtils.parseCommentBean(result);
                    if (commentBean.getStatus() == 1) {
                        ArrayList<CommentBean.DataEntity.ListEntity> receiveList = (ArrayList<CommentBean.DataEntity.ListEntity>) commentBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            switch (type) {
                                case LOAD:
                                    list = receiveList;
                                    adapter = new CommentAdapter(getActivity(), list);
                                    lvComment.setAdapter(adapter);
                                    break;
                                case LOADMORE:
                                    list.addAll(receiveList);
                                    adapter.notifyDataSetChanged();
                                    loadSuccess(receiveList.size());
                                    break;
                                case REFRESH:
                                    list = receiveList;
                                    adapter = new CommentAdapter(getActivity(), list);
                                    lvComment.setAdapter(adapter);
                                    refreshSuccess();
                            }
                        } else if (receiveList == null || receiveList.size() == 0) {
                            if (pageIndex > 1) {
                                Toast.makeText(getActivity(), "没有更多评论了", Toast.LENGTH_SHORT).show();
                            } else if (pageIndex == 1) {
                                Toast.makeText(getActivity(), "抱歉，该活动还没有评论", Toast.LENGTH_LONG).show();
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), commentBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
