package com.xiaochen.module.jetpack;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/14 18:25
 */
public class MyViewModel extends ViewModel {
   // public String name = "zlc";
    private MutableLiveData<String> mLiveData;

    MutableLiveData<String> getLiveData() {
        if(mLiveData == null){
            mLiveData = new MutableLiveData<>();
        }
        return mLiveData;
    }
}
