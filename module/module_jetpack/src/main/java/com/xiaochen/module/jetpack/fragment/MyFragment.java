package com.xiaochen.module.jetpack.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.xiaochen.module.jetpack.R;

/**
 * @author admin
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/23 14:34
 */
public class MyFragment extends Fragment {

    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        mTv = view.findViewById(R.id.tv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTv.setOnClickListener(v -> {
            Navigation.findNavController(mTv)
                    .navigate(R.id.action_myFragment_to_settingFragment);
        });

        Bundle bundle = getArguments();
        if(bundle == null){
            return;
        }
        int number = bundle.getInt("number");
        Log.e("测试数据",number+"");
    }
}
