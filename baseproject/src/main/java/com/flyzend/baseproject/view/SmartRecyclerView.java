package com.flyzend.baseproject.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by 王灿 on 2017/8/1.
 * 封装SmartRefreshLayout与RecyclerView 实现下拉刷新，上拉加载更多
 * 默认使用垂直线性布局管理器
 *
 * 推荐直接使用官方RecyclerView 配合第三方下来刷新
 */
@Deprecated
public class SmartRecyclerView extends SmartRefreshLayout {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private boolean isRefreshEnable;

    public SmartRecyclerView(Context context) {
        this(context, null);
    }

    public SmartRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setRefreshEnable(boolean refreshEnable) {
        isRefreshEnable = refreshEnable;
    }

    private void init() {
        mRecyclerView = new RecyclerView(mContext);
        mRecyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        addView(mRecyclerView);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mRecyclerView.setAdapter(adapter);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            mLayoutManager = layoutManager;
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration != null) {
            mItemDecoration = itemDecoration;
            mRecyclerView.addItemDecoration(mItemDecoration);
        }
    }

    //获取RecyclerView
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
