package com.xiaochen.module.jetpack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

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
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/17 17:28
 */
public class RoomActivity extends BaseActivity implements View.OnClickListener {

    private View mAdd;
    private View mDelete;
    private View mUpdate;
    private View mSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_room;
    }

    @Override
    public void initView() {
        mAdd = findViewById(R.id.add);
        mDelete = findViewById(R.id.delete);
        mUpdate = findViewById(R.id.update);
        mSelect = findViewById(R.id.select);

        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mSelect.setOnClickListener(this);

        TextView title = findViewById(R.id.title_text);
        title.setText("room");
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add) {
            add();
        } else if (id == R.id.delete) {
            delete();
        } else if (id == R.id.update) {
            update();
        } else if (id == R.id.select) {
            select();
        }
    }

    @SuppressLint("CheckResult")
    private void select() {
        Observable.create((ObservableOnSubscribe<List<User>>) emitter -> {
            UserDao userDao = AppDataBase.getInstance(RoomActivity.this).userDao();
            List<User> users = userDao.selectAll();
            if (users != null) {
                emitter.onNext(users);
                emitter.onComplete();
            } else {
                emitter.onError(new Throwable("对象为空"));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    for (User user : users) {
                        Log.e("select", "id=" + user.id + " ;name=" + user.userName);
                    }
                }, throwable -> Log.e("accept error", throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    private void update() {
        final User user = new User();
        user.id = 0;
        user.userName = "cc";
        Observable.create((ObservableOnSubscribe<User>) emitter -> {
            UserDao userDao = AppDataBase.getInstance(RoomActivity.this).userDao();
            userDao.update(user);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user1 -> {

                }, throwable -> Log.e("accept error", throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    private void delete() {
        final User user = new User();
        user.id = 1;
        Observable.create((ObservableOnSubscribe<User>) emitter -> {
            UserDao userDao = AppDataBase.getInstance(RoomActivity.this).userDao();
            userDao.delete(user);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user1 -> {

                }, throwable -> Log.e("accept error", throwable.getMessage()));
    }

    private int index = 4;

    @SuppressLint("CheckResult")
    private void add() {
        final List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final User user = new User();
            user.id = index + i;
            user.userName = "zlc:" + index + i;
            users.add(user);
        }
        Observable.create((ObservableOnSubscribe<User>) emitter -> {
            UserDao userDao = AppDataBase.getInstance(RoomActivity.this).userDao();
            userDao.insertAll(users);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {

                }, throwable -> Log.e("accept error", throwable.getMessage()));
    }
}
