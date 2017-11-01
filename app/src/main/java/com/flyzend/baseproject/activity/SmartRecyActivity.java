package com.flyzend.baseproject.activity;

import android.os.Bundle;

import com.flyzend.baseproject.R;
import com.flyzend.baseproject.adapter.SwipeRecyAdapter;
import com.flyzend.baseproject.datas.DataServer;
import com.flyzend.baseproject.view.SmartRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class SmartRecyActivity extends BaseActivity {
    private SmartRecyclerView mSmartRecyclerView;
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
        mSmartRecyclerView = findAviewById(R.id.easyRecyclerView);
        mSmartRecyclerView.setEnableAutoLoadmore(true);
        mSmartRecyclerView.setEnableLoadmore(true);
        mSmartRecyclerView.setEnableLoadmoreWhenContentNotFull(true);
        mSmartRecyclerView.setRefreshHeader(new ClassicsHeader(this));//设置Header
        mSmartRecyclerView.setRefreshFooter(new ClassicsFooter(this));//设置Footer
        mSmartRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                PAGE = 1;
                Flowable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        datas = DataServer.getData(PAGE++,PAGE_SIZE);
                        mSwipeRecyAdapter.setNewData(datas);
                        mSmartRecyclerView.finishRefresh();
                        mSmartRecyclerView.setLoadmoreFinished(false);
                        showToast("刷新完成");
                    }
                });
            }
        });
        mSmartRecyclerView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Flowable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mSmartRecyclerView.finishLoadmore();
                        datas = DataServer.getData(PAGE,PAGE_SIZE);
                        mSwipeRecyAdapter.addData(datas);
                        if (PAGE == 3){
                            mSmartRecyclerView.setLoadmoreFinished(true);
                        }else {
                            PAGE++;
                        }
                    }
                });
            }
        });
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
                mSmartRecyclerView.finishRefresh();
                //设置adapter
                mSmartRecyclerView.setAdapter(mSwipeRecyAdapter);
            }
        });
    }
}
