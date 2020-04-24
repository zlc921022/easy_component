package com.xiaochen.module.mvvm.api

import com.xiaochen.module.mvvm.response.HomeArticleRespVO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * 服务器接口{d}
 *
 * @author zhenglecheng
 * @date 2019-12-08
 */
interface IServiceApi {

    /**
     * 首页文章列表
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): HomeArticleRespVO
}
