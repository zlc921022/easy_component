package com.xiaochen.common.mvp;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.xiaochen.common.data.ApiManager;
import com.xiaochen.common.data.BaseResponse;
import com.xiaochen.common.data.HttpManager;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
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
    private CompositeDisposable mDisposables;
    private Retrofit mRetrofit;
    /**
     * 子类调用view接口方法，通过这个属性去调，替换getView方法
     */
    protected T mViewProxy;

    public AbsBasePresenter(@NonNull Context context, @NonNull final T view) {
        this.mContext = context;
        this.mView = new SoftReference<T>(view);
        HttpManager.getManager().setRetrofit(createRetrofit());
        mApiManager = ApiManager.getManager();
        createViewProxy();
    }

    /**
     * 创建View接口的动态代理
     */
    private void createViewProxy() {
        if (mView.get() == null) {
            return;
        }
        Class<? extends IBaseView> aClass = mView.get().getClass();
        mViewProxy = (T) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (getView() == null) {
                            return null;
                        }
                        return method.invoke(getView(), args);
                    }
                });
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
        if (mDisposables != null) {
            mDisposables.clear();
        }
        if (mViewProxy != null) {
            mViewProxy = null;
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
            mDisposables = new CompositeDisposable();
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
            mViewProxy.showLoading();
        }

        @Override
        public void onSuccess(V data) {
            mViewProxy.setData(data);
        }

        @Override
        public void onFailure(String code, String errMessage) {
            mViewProxy.onError(errMessage, code);
        }

        @Override
        public void onEnd() {
            mViewProxy.dismissLoading();
        }
    }

}
