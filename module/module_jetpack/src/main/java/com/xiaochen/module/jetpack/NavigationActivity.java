package com.xiaochen.module.jetpack;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.xiaochen.common.base.BaseActivity;

/**
 * @author admin
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/23 14:11
 */
public class NavigationActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.fragment_home);
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void initView() {
        super.initView();
        TextView title = findViewById(R.id.title_text);
        title.setText("Navigation");
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });
    }
}
