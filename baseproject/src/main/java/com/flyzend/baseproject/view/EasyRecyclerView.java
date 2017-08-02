package com.flyzend.baseproject.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by 王灿 on 2017/8/1.
 * 封装PtrFrameLayout与RecyclerView 实现下拉刷新。
 * 默认使用垂直线性布局管理器
 * Adapter 使用三方的BaseQuickAdapter
 */

public class EasyRecyclerView extends PtrClassicFrameLayout implements PtrHandler{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private OnRefreshListener mOnRefreshListener;

    public EasyRecyclerView(Context context) {
        this(context,null);
    }

    public EasyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mRecyclerView = new RecyclerView(mContext);
        mRecyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        setPtrHandler(this);
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
    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public void setRefreshListener(OnRefreshListener onRefreshListener){
        mOnRefreshListener = onRefreshListener;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (mOnRefreshListener != null){
            mOnRefreshListener.onRefresh();
        }
    }

    public interface OnRefreshListener{
        void onRefresh();
    }
}
