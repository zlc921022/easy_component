package com.xiaochen.module.jetpack.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/16 11:18
 */
@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Insert
    void insertAll(List<User> users);

    @Delete
    int delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user WHERE user_name = :name")
    User selectUser(String name);

    @Query("SELECT * FROM user")
    List<User> selectAll();

    @Query("SELECT * FROM user")
    DataSource.Factory<Integer,User> getAll();
}
