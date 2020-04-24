package com.xiaochen.common.utils;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author : zlc
 * @date : On 2017/4/27
 * @eamil : zlc921022@163.com
 */

public class WebViewUtil {

    private WebViewUtil() {
    }

    /**
     * 通过地址加载webview页面
     *
     * @param webView
     * @param url
     */
    public static void loadHtmlByUrl(WebView webView, String url) {
        WebSettings webSettings = webView.getSettings();
        setWebView(webSettings);
        //加载需要显示的网页
        webView.loadUrl(url);
    }

    /**
     * 通过data加载webview页面
     *
     * @param webView
     * @param data
     */
    public static void loadHtmlByData(WebView webView, String data) {
        WebSettings webSettings = webView.getSettings();
        setWebView(webSettings);
        //加载需要显示的网页
        webView.loadData(data, "text/html; charset=UTF-8", null);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private static void setWebView(WebSettings webSetting) {
        //支持javascript
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        //自适应屏幕
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
    }

}
