package com.xiaochen.common.sdk

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * <p>图片加载二次封装</p >
 * @author     zhenglecheng
 * @date       2020/4/29
 */
class ImageManager private constructor() {

    companion object {
        /**
         * 正常加载图片
         * @param context
         * @param img
         * @param url
         */
        @JvmStatic
        fun showImage(context: Context, img: ImageView, url: String) {
            val options = RequestOptions()
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(img)
        }

        /**
         * 加载正方形图片
         * @param context
         * @param img
         * @param url
         */
        @JvmStatic
        fun showSquare(context: Context, img: ImageView, url: String) {
            val options = RequestOptions()
                    .centerCrop()
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(img)
        }

        /**
         * 加载头像类圆形图片
         * @param context
         * @param img
         * @param url
         */
        @JvmStatic
        fun showCircle(context: Context, img: ImageView, url: String) {
            val options = RequestOptions()
                    .circleCrop()
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(img)
        }

        /**
         * 加载圆角图片
         * @param context
         * @param img
         * @param url
         */
        @JvmStatic
        fun showRoundImage(context: Context, img: ImageView, url: String) {
            val options = RequestOptions()
                    .transform(RoundedCorners(10))
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(img)
        }

        /**
         * 加载gif图片
         * @param context
         * @param img
         * @param url
         */
        @JvmStatic
        fun showGif(context: Context, img: ImageView, url: String) {
            val options = RequestOptions()
            Glide.with(context)
                    .asGif()
                    .load(url)
                    .apply(options)
                    .into(img)
        }

        /**
         * 加载本地图片
         * @param context
         * @param img
         * @param url
         */
        @JvmStatic
        fun showLocalImage(context: Context, img: ImageView, url: String) {
            val options = RequestOptions()
            Glide.with(context)
                    .load(url)
                    .thumbnail(0.1f)
                    .apply(options)
                    .into(img)
        }
    }
}