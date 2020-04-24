package com.xiaochen.module.jetpack.room;

import androidx.room.ColumnInfo;

/**
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/17 17:19
 */
public class Address {
    public String street;
    public String state;
    public String city;
    @ColumnInfo(name = "post_code")
    public String postCode;
}
