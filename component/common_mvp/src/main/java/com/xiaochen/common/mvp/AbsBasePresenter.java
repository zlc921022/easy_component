package com.xiaochen.common.mvp;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.xiaochen.common.data.ApiManager;
import com.xiaochen.common.data.BaseResponse;
import com.xiaochen.common.data.HttpManager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * presenter的父类,所有子类都必须实现它
 *
 * @param <T>
 */
@SuppressWarnings("all")
public abstract class AbsBasePresenter<T extends IBaseView> extends BasePresenter {

    protected Context mContext;
    protected ApiManager mApiManager;
    private SoftReference<T> mView;
    private LifecycleOwner mLifecycle;
    private List<Disposable> mDisposables;
    private Retrofit mRetrofit;

    public AbsBasePresenter(@NonNull Context context, @NonNull final T view) {
        this.mContext = context;
        this.mView = new SoftReference<T>(view);
        HttpManager.getManager().setRetrofit(createRetrofit());
        mApiManager = ApiManager.getManager();
    }

    /**
     * 创建Retrofit 针对rxjava单独处理一下
     */
    protected Retrofit createRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = HttpManager.getManager()
                    .getRetrofit()
                    .newBuilder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    @Override
    public void onCreateView(LifecycleOwner lifecycleOwner) {
        super.onCreateView(lifecycleOwner);
        this.mLifecycle = lifecycleOwner;
    }

    @Override
    public void onDetachView(LifecycleOwner lifecycleOwner) {
        super.onDetachView(lifecycleOwner);
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        if (mDisposables == null) {
            return;
        }
        for (Disposable d : mDisposables) {
            if (!d.isDisposed()) {
                d.dispose();
            }
        }
    }

    /**
     * 获取view接口对象
     * 如果为null，要么就是没实现view接口，要么就是activity被销毁了
     *
     * @return
     */
    protected T getView() {
        if (mView == null) {
            return null;
        }
        return mView.get();
    }

    /**
     * 请求数据
     *
     * @param observable
     * @param observer
     */
    protected <V, P extends BaseResponse<V>, Q extends BaseResponseObserver<V, P>> void requestData(
            final Observable<P> observable, final Q observer) {
        if (getView() == null || mLifecycle == null) {
            return;
        }
        if (mDisposables == null) {
            mDisposables = new ArrayList<>();
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 统一处理loading显示和关闭
     *
     * @param <V> 返回的实体data
     * @param <P> 返回的包含响应码，错误信息的的对象
     */
    protected abstract class ResponseObserverCallBack<V, P extends BaseResponse<V>> extends BaseResponseObserver<V, P> {

        @Override
        public void onSubscribe(Disposable d) {
            super.onSubscribe(d);
            mDisposables.add(d);
        }

        @Override
        public void onStart() {
            if (getView() != null) {
                getView().showLoading();
            }
        }

        @Override
        public abstract void onSuccess(V data);

        @Override
        public abstract void onFailure(String code, String errMessage);

        @Override
        public void onEnd() {
            if (getView() != null) {
                getView().dismissLoading();
            }
        }
    }

}
