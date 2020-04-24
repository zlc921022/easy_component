package com.xiaochen.module.jetpack;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import com.xiaochen.module.jetpack.room.AppDataBase;
import com.xiaochen.module.jetpack.room.User;
import com.xiaochen.module.jetpack.room.UserDao;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/23 18:30
 */
public class UserViewModel extends AndroidViewModel {

    private Application mApplication;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    public LiveData<PagedList<User>> getUsers() {
        UserDao userDao = AppDataBase.getInstance(mApplication).userDao();
        return new LivePagedListBuilder<>(userDao.getAll(), getPagedListConfig()).build();
    }

    public Flowable<PagedList<User>> getUsersByRxJava() {
        UserDao userDao = AppDataBase.getInstance(mApplication).userDao();
        return new RxPagedListBuilder<>(userDao.getAll(), getPagedListConfig()).buildFlowable(BackpressureStrategy.LATEST);
    }

    private PagedList.Config getPagedListConfig(){
        return new PagedList.Config.Builder()
                .setPageSize(50)
                .setPrefetchDistance(150)
                .setEnablePlaceholders(true)
                .build();
    }

}
