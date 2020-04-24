package com.xiaochen.module.jetpack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.module.jetpack.room.AppDataBase;
import com.xiaochen.module.jetpack.room.User;
import com.xiaochen.module.jetpack.room.UserDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author admin
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/23 18:15
 */
public class PagingActivity extends BaseActivity {

    private UserViewModel mViewModel;
    private int index = 0;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button addData = findViewById(R.id.add_data);
        addData.setOnClickListener(v -> addData());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        // viewModel
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyPagingAdapter adapter = new MyPagingAdapter(this, mCallback);
        mViewModel.getUsers().observe(this, adapter::submitList);
///        mViewModel.getUsersByRxJava().subscribe(adapter::submitList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_paging;
    }

    @Override
    protected void initView() {
        super.initView();
        TextView title = findViewById(R.id.title_text);
        title.setText("Paging");
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });
    }

    private final DiffUtil.ItemCallback<User> mCallback = new DiffUtil.ItemCallback<User>() {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

    @SuppressLint("CheckResult")
    public void addData() {
        final List<User> users = new ArrayList<>();
        int size = 5;
        for (int i = 0; i < size; i++) {
            final User user = new User();
            final int temp = index + i;
            user.id = temp;
            user.userName = "zlc:" + temp;
            users.add(user);
        }
        index = index + size;
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            UserDao userDao = AppDataBase.getInstance(this).userDao();
            userDao.insertAll(users);
            emitter.onNext(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSuccess -> {
                    Log.e("数据添加", "添加成功");
                }, throwable -> Log.e("accept error", throwable.getMessage()));
    }
}
