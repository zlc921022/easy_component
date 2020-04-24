package com.xiaochen.module.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaochen.common.mvvm.BaseResult
import com.xiaochen.common.mvvm.BaseViewModel
import com.xiaochen.module.mvvm.repository.TestRepository
import com.xiaochen.module.mvvm.response.HomeArticleRespVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * <p></p >
 * @author     zhenglecheng
 * @date       2019/12/18
 */
class TestViewModel : BaseViewModel() {

    val mHomeLiveData: MutableLiveData<HomeArticleRespVO.Data> = MutableLiveData()
    private val testRepository = TestRepository()

    fun getArticleInfo(page: Int) {
        launchUI {
            val result = withContext(Dispatchers.IO) {
                testRepository.getArticle(page)
            }
            if (result is BaseResult.Success) {
                mHomeLiveData.value = result.data
            } else if (result is BaseResult.Error) {
                mExceptionLiveData.value = result.exception
            }
        }
    }

}