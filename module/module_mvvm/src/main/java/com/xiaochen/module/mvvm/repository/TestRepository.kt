package com.xiaochen.module.mvvm.repository
import com.xiaochen.common.data.ApiManager
import com.xiaochen.common.mvvm.BaseRepository
import com.xiaochen.common.mvvm.BaseResult
import com.xiaochen.module.mvvm.api.IServiceApi
import com.xiaochen.module.mvvm.response.HomeArticleRespVO

/**
 * <p></p >
 * @author     zhenglecheng
 * @date       2019/12/16
 */
class TestRepository : BaseRepository() {

    private val mApiManager by lazy {
        ApiManager.getManager().createApi(IServiceApi::class.java)
    }

    suspend fun getArticle(page: Int): BaseResult<HomeArticleRespVO.Data> {
        return safeApiCall(call = { requestArticle(page) }, errorMessage = "网络错误")
    }

    private suspend fun requestArticle(page: Int): BaseResult<HomeArticleRespVO.Data> {
        return executeResponse(mApiManager.getHomeArticles(page))
    }
}