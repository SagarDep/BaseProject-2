package com.flyzend.baseproject.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyzend.www.baseproject.R;

/**
 * Created by 王灿 on 2017/2/13.
 * 封装swipeRefreshLayout与RecyclerView 实现下拉刷新。
 * 默认使用垂直线性布局管理器
 * Adapter 使用三方的BaseQuickAdapter
 * @deprecated 由于谷歌官方SwipeRefreshLayout 不支持自定义头部布局。
 *             推荐更换EasyRecyclerView，使用的是第三方android-Ultra-Pull-To-Refresh，支持自定义刷新布局
 */
@Deprecated
public class SwipeRecyclerView extends LinearLayout{
    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter mBaseQuickAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;

    public SwipeRecyclerView(Context context) {
        super(context);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(mContext);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.swipe_recylerview_layout, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.baseProjectSwpieResfreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.baseProjectRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        //默认不允许刷新，按需要开启
        mSwipeRefreshLayout.setEnabled(false);
    }


    public void setBaseQuickAdapter(BaseQuickAdapter baseQuickAdapter) {
        if (baseQuickAdapter != null) {
            mBaseQuickAdapter = baseQuickAdapter;
            mRecyclerView.setAdapter(mBaseQuickAdapter);
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

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener) {
        if (refreshListener != null) {
            mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        }
    }

    public void setRefreshEnable(boolean enable){
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void setRefreshing(boolean isRefresh) {
        mSwipeRefreshLayout.setRefreshing(isRefresh);
    }

    public void setEmptyView(View view){
        mBaseQuickAdapter.setEmptyView(view);
    }

    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener onItemTouchListener) {
        if (onItemTouchListener != null) {
            mRecyclerView.addOnItemTouchListener(onItemTouchListener);
        }
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }
}
