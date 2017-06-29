package com.flyzend.baseproject.rx;

import com.trello.rxlifecycle2.components.RxFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import okhttp3.ResponseBody;

/**
 * Created by 王灿 on 2017/6/28.
 */

public class RxBus {
    private static RxBus instance;

    private FlowableProcessor<Object> processorBus;

    public static RxBus build() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    RxBus tempInstance = new RxBus();
                    tempInstance.processorBus = PublishProcessor.create().toSerialized();
                    instance = tempInstance;
                }
            }
        }
        return instance;
    }

    public Disposable getEvent(RxAppCompatActivity activity, int code, Consumer<BaseEventBean> observer) {
        return new BuildFlowable(activity, code).bindLifeCycle(toFlowable()).subscribe(observer);
    }

    public Disposable getEvent(com.trello.rxlifecycle2.components.support.RxFragment rxV4Fragment, int code, Consumer<BaseEventBean> observer) {
        return new BuildFlowable(rxV4Fragment, code).bindLifeCycle(toFlowable()).subscribe(observer);
    }

    public Disposable getEvent(RxFragment rxFragment, int code, Consumer<BaseEventBean> observer) {
        return new BuildFlowable(rxFragment, code).bindLifeCycle(toFlowable()).subscribe(observer);
    }
//
//    public void unRegister(Disposable disposable) {
//        if (disposable != null && !disposable.isDisposed()) {
//            disposable.dispose();
//        }
//    }
//
//    public void unRegister(CompositeDisposable compositeDisposable) {
//        if (compositeDisposable != null) {
//            compositeDisposable.dispose();
//        }
//    }

    public void postEvent(final int eventType, final Object event) {
        BaseEventBean eventBean = new BaseEventBean();
        eventBean.setType(eventType);
        eventBean.setEvent(event);
        processorBus.onNext(eventBean);
    }

    private Flowable toFlowable() {
        return processorBus;
    }

    public boolean hasSubscribers() {
        return processorBus.hasSubscribers();
    }

    static class BuildFlowable {
        private int mEventCode;
        private RxAppCompatActivity mRxAppCompatActivity;
        private RxFragment mRxFragment;
        private com.trello.rxlifecycle2.components.support.RxFragment mV4Fragment;

        public BuildFlowable(RxAppCompatActivity rxAppCompatActivity, int eventCode) {
            mRxAppCompatActivity = rxAppCompatActivity;
            mEventCode = eventCode;
        }

        public BuildFlowable(RxFragment rxFragment, int eventCode) {
            mRxFragment = rxFragment;
            mEventCode = eventCode;
        }

        public BuildFlowable(com.trello.rxlifecycle2.components.support.RxFragment v4Fragment, int eventCode) {
            mV4Fragment = v4Fragment;
            mEventCode = eventCode;
        }

        private Flowable bindLifeCycle(Flowable flowable) {
            flowable = flowable.filter(new Predicate() {
                @Override
                public boolean test(Object o) throws Exception {
                    return mEventCode == ((BaseEventBean) o).getType();
                }
            });
            if (mRxAppCompatActivity != null) {
                return flowable.compose(mRxAppCompatActivity.<ResponseBody>bindToLifecycle());
            } else if (mRxFragment != null) {
                return flowable.compose(mRxFragment.<ResponseBody>bindToLifecycle());
            } else if (mV4Fragment != null) {
                return flowable.compose(mV4Fragment.<ResponseBody>bindToLifecycle());
            }
            return flowable;
        }
    }
}
