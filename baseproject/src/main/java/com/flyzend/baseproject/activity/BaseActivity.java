package com.flyzend.baseproject.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.flyzend.baseproject.AppManager;
import com.flyzend.baseproject.dialog.BaseDialog;
import com.flyzend.baseproject.utils.LogUtil;
import com.flyzend.baseproject.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;

/**
 * Created by 王灿 on 2017/1/9.
 */

public abstract class BaseActivity extends RxAppCompatActivity
        implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected ToastUtil mToastUtil;
    private ProgressDialog mLoadingDialog;
    private boolean mLoadDialogCanceledOutSide = false;
    private List<Fragment> mFragments;
    private int fragment_container = -1;
    private OnProgressListener mOnProgressListener;
    private ProgressInfo mLastUploadingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mToastUtil = new ToastUtil(mContext);
        AppManager.getInstance().addActivity(this);
    }

    protected void updateFragment(Fragment fragment, int viewId) {
        if (fragment == null || viewId <= 0) {
            return;
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewId, fragment);
        transaction.commit();
    }

    protected <T extends View> T findAviewById(int viewId) {
        if (viewId > 0) {
            return (T) findViewById(viewId);
        }
        return null;
    }

    protected <T extends View> T findAviewInContainer(ViewGroup containerView, int childViewId) {
        if (containerView == null || childViewId <= 0) {
            return null;
        }
        return (T) containerView.findViewById(childViewId);
    }

    /**
     * 最终调用的跳转方法
     * @param startIntent intent
     * @param bundle bundle数据
     * @param requestCode 请求码 -1表示不需要回调
     */
    private void jumpToActivity(Intent startIntent, Bundle bundle, int requestCode) {
        if (startIntent != null) {
            if (bundle != null) {
                startIntent.putExtras(bundle);
            }
            if (requestCode != -1) {
                startActivity(startIntent);
            } else {
                startActivityForResult(startIntent, requestCode);
            }
        }
    }

    protected void jumpToActivity(Class<?> targetActivityClass) {
        jumpToActivity(targetActivityClass,null);
    }

    protected void jumpToActivity(Class<?> targetActivityClass, Bundle bundle) {
        jumpToActivity(targetActivityClass,bundle,-1);
    }

    protected void jumpToActivity(Class<?> targetActiviyClass, int requestCode) {
        jumpToActivity(targetActiviyClass,null,requestCode);
    }

    protected void jumpToActivity(Class<?> targetActiviyClass,Bundle bundle, int requestCode) {
        Intent startIntent = new Intent(mContext, targetActiviyClass);
        jumpToActivity(startIntent,bundle, requestCode);
    }

    protected void addProgressListener(String url,OnProgressListener progressListener){
        this.mOnProgressListener = progressListener;
        ProgressManager.getInstance().addRequestListener(url, getUploadListener());
    }

    @NonNull
    private ProgressListener getUploadListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
                // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
                // 这里我就取最新的上传进度用来展示,顺便展示下 id 的用法

                if (mLastUploadingInfo == null) {
                    mLastUploadingInfo = progressInfo;
                }

                //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
                if (progressInfo.getId() < mLastUploadingInfo.getId()) {
                    return;
                } else if (progressInfo.getId() > mLastUploadingInfo.getId()) {
                    mLastUploadingInfo = progressInfo;
                }

                int progress = mLastUploadingInfo.getPercent();
                mOnProgressListener.onProgress(progress);
                Log.d(TAG, "--Upload-- " + progress + " %  " + mLastUploadingInfo.getSpeed() + " byte/s  " + mLastUploadingInfo.toString());
                if (mLastUploadingInfo.isFinish()) {
                    //说明已经上传完成
                    Log.d(TAG, "--Upload-- finish");
                    mOnProgressListener.onComplete();
                }
            }

            @Override
            public void onError(long id, Exception e) {
               mOnProgressListener.onError(e);
            }
        };
    }

    /**
     * 基类提供普通Log输出之error级信息输出
     *
     * @param logBody
     */
    protected void e(String logBody) {
        if (logBody == null){
            return;
        }
        LogUtil.e(TAG, logBody);
    }

    protected void v(String logBody) {
        if (logBody == null){
            return;
        }
        LogUtil.v(TAG, logBody);
    }

    protected void i(String logBody) {
        if (logBody == null){
            return;
        }
        LogUtil.i(TAG, logBody);
    }

    protected void d(String logBody) {
        if (logBody == null){
            return;
        }
        LogUtil.d(TAG, logBody);
    }

    protected void showToast(String msg) {
        mToastUtil.showToast(msg);
    }

    protected void showLongToast(String msg) {
        mToastUtil.showToastLong(msg);
    }

    protected void showLoading() {
        showLoading("正在努力加载中...");
    }

    protected void setLoadDialogCanceledOutSide(boolean canceledOutSide){
        mLoadDialogCanceledOutSide = canceledOutSide;
    }

    protected void showLoading(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = ProgressDialog.show(this, null, msg, true, true);
            mLoadingDialog.setCanceledOnTouchOutside(mLoadDialogCanceledOutSide);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    protected void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    protected void addFragment(Fragment fragment) {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        }
        mFragments.add(fragment);
    }

    protected void addFragments(List<Fragment> fragments) {
        this.mFragments = fragments;
    }

    protected void removeFragment(Fragment fragment) {
        if (fragment != null && mFragments != null && mFragments.contains(fragment)) {
            mFragments.remove(fragment);
        }
    }

    protected void removeFragment(int position) {
        if (position >= 0 && mFragments != null && mFragments.size() > position) {
            mFragments.remove(position);
        }
    }


    /**
     * 设置fragment显示的布局id
     *
     * @param fragment_container
     */
    protected void setFragmentContainer(int fragment_container) {
        this.fragment_container = fragment_container;
    }

    protected void switchFragment(final int index) {
        if (fragment_container == -1 || mFragments == null) {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Flowable.fromIterable(mFragments).filter(new Predicate<Fragment>() {
            int i = 0;

            @Override
            public boolean test(Fragment fragment) throws Exception {
                //判断当前要发射的fragment是不是当前需要显示的fragment
                boolean isCurrentFragment = i == index;
                if (isCurrentFragment) {
                    if (fragment.isAdded()) {
                        fragmentTransaction.show(fragment);
                    } else {
                        fragmentTransaction.add(fragment_container, fragment);
                    }
                }
                i++;
                return !isCurrentFragment;
            }
        }).subscribe(new Consumer<Fragment>() {
            @Override
            public void accept(Fragment fragment) throws Exception {
                if (fragment.isAdded()) {
                    fragmentTransaction.hide(fragment);
                }
            }
        });
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected void showDialog(String title, String msg, DialogInterface.OnClickListener listener){
        new BaseDialog(mContext).showDialog(title,msg,listener);
    }

    public interface OnProgressListener {
        void onProgress(int percent);
        void onError(Exception e);
        void onComplete();
    }
}
