package com.xiaochen.module.jetpack.room;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/16 11:18
 */
@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public Integer id = 0;
    @ColumnInfo(name = "user_name")
    public String userName;

    @Override
    public boolean equals(@Nullable Object obj) {
        if( !(obj instanceof User)){
            return false;
        }
        if(obj == this){
            return true;
        }
        User user = (User) obj;
        return this.id.equals(user.id) && TextUtils.equals(this.userName,user.userName);
    }
}
