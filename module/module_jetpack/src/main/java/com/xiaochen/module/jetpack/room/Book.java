package com.xiaochen.module.jetpack.room;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

/**
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/17 17:15
 */
public class Book {
    @PrimaryKey
    private String bookId;
    private String title;
    @ColumnInfo(name = "user_id")
    private String userId;
}
