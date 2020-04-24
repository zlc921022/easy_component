package com.xiaochen.module.mvp.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.xiaochen.common.mvp.AbsBasePresenter;
import com.xiaochen.module.mvp.view.ITestView;
import com.xiaochen.module.mvp.api.IServiceApi;
import com.xiaochen.module.mvp.response.HomeArticleRespVO;

import io.reactivex.Observable;

/**
 * <p>测试</p >
 *
 * @author zhenglecheng
 * @date 2019/12/26
 */
public class TestPresenter extends AbsBasePresenter<ITestView> {

    private final IServiceApi mServiceApi;

    public TestPresenter(@NonNull Context context,ITestView view) {
        super(context,view);
        mServiceApi = mApiManager.createApi(IServiceApi.class);
    }

    public void getHomeArticles(final int page) {
        ResponseObserverCallBack<HomeArticleRespVO.Data, HomeArticleRespVO> observer =
                new ResponseObserverCallBack<HomeArticleRespVO.Data, HomeArticleRespVO>() {

                    @Override
                    public void onSuccess(HomeArticleRespVO.Data data) {
                        if (getView() != null) {
                            getView().setData(data);
                        }
                    }

                    @Override
                    public void onFailure(String code, String errMessage) {
                        if (getView() != null) {
                            getView().onError(errMessage, code);
                        }
                    }
                };
        Observable<HomeArticleRespVO> observable = mServiceApi.getHomeArticles(page);
        requestData(observable, observer);
    }

}
