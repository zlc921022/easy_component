package com.xiaochen.module.jetpack.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author admin
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/16 11:14
 */
@Database(entities = {User.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    private static AppDataBase mAppDataBase;

    public static AppDataBase getInstance(Context context) {
        if (mAppDataBase == null) {
            synchronized (AppDataBase.class) {
                if (mAppDataBase == null) {
                    mAppDataBase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "user_data").build();
                }
            }
        }
        return mAppDataBase;
    }
}
