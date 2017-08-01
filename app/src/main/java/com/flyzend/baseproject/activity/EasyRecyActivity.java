package com.flyzend.baseproject.activity;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyzend.baseproject.R;
import com.flyzend.baseproject.adapter.SwipeRecyAdapter;
import com.flyzend.baseproject.datas.DataServer;
import com.flyzend.baseproject.view.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class EasyRecyActivity extends BaseActivity
        implements EasyRecyclerView.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    private EasyRecyclerView mEasyRecyclerView;
    private SwipeRecyAdapter mSwipeRecyAdapter;
    private final int PAGE_SIZE = 10;
    private int PAGE = 1;
    private List<String> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_recy);
        initViews();
        initData();
    }

    protected void initViews() {
        mEasyRecyclerView = findAviewById(R.id.easyRecyclerView);
        //设置下拉刷新监听
        mEasyRecyclerView.setRefreshListener(this);
    }

    protected void initData() {
        //模拟网络耗时操作，2秒后执行接下来操作
        Flowable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                //获取数据
                datas = DataServer.getData(PAGE,PAGE_SIZE);
                mSwipeRecyAdapter = new SwipeRecyAdapter(datas);
                //设置刷新为false 此刻已经刷新完成
                mEasyRecyclerView.refreshComplete();
                //设置adapter
                mEasyRecyclerView.setAdapter(mSwipeRecyAdapter);
                mSwipeRecyAdapter.setOnLoadMoreListener(EasyRecyActivity.this,mEasyRecyclerView.getRecyclerView());
            }
        });
    }


    @Override
    public void onRefresh() {
        PAGE = 1;
        Flowable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                datas = DataServer.getData(PAGE,PAGE_SIZE);
                mSwipeRecyAdapter.setNewData(datas);
                mEasyRecyclerView.refreshComplete();
                showToast("刷新完成");
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
                Flowable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                datas = DataServer.getData(PAGE,PAGE_SIZE);
                mSwipeRecyAdapter.addData(datas);
                if (PAGE == 3){
                    mSwipeRecyAdapter.loadMoreEnd();
                }else {
                    mSwipeRecyAdapter.loadMoreComplete();
                    PAGE++;
                }
            }
        });
    }
}
