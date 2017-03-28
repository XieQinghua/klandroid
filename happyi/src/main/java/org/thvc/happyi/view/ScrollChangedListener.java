package org.thvc.happyi.view;

import android.widget.ScrollView;

/**
 * 项目名称：klandroid
 * 类描述：监听ScrollView滑动距离接口
 * 创建人：谢庆华.
 * 创建时间：2016/1/4 19:50
 * 修改人：Administrator
 * 修改时间：2016/1/4 19:50
 * 修改备注：
 */
public interface ScrollChangedListener {
    void onScrollChanged(CustomScrollView scrollView, int l, int t, int oldl, int oldt);
}
