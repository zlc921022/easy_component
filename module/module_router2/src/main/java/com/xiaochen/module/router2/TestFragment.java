package com.xiaochen.module.router2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaochen.common.sdk.RouterPathConstant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020/4/19
 */
@Route(path = RouterPathConstant.TEST_FRAGMENT)
public class TestFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.router2_fragment_test, container, false);
    }
}
