package com.xiaochen.module.mvvm

import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.xiaochen.common.base.BaseActivity
import com.xiaochen.common.utils.LogUtil
import com.xiaochen.module.mvvm.viewmodel.TestViewModel
import com.xiaochen.common.sdk.RouterPathConstant
import kotlinx.android.synthetic.main.mvvm_activity_main.*

@Route(path = RouterPathConstant.MVVM_ACTIVITY)
class MainActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(TestViewModel::class.java)
    }

    override fun getLayoutResId(): Int {
        return R.layout.mvvm_activity_main
    }

    override fun initData() {
        super.initData()
        findViewById<View>(R.id.back_button).setOnClickListener {
            finish()
        }
        val title = findViewById<TextView>(R.id.title_text)
        title.text = "mvvm测试"
        mViewModel.mHomeLiveData.observe(this, Observer {
            LogUtil.e("MainActivity", "调用成功")
        })
        mViewModel.mExceptionLiveData.observe(this, Observer {
            LogUtil.e("MainActivity error", it.message + "")
        })
    }

    override fun initListener() {
        button_1.setOnClickListener {
            mViewModel.getArticleInfo(1)
        }
    }
}
