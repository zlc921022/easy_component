package com.xiaochen.module.jetpack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaochen.module.jetpack.room.User;

/**
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/23 18:42
 */
public class MyPagingAdapter extends PagedListAdapter<User, MyPagingAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;

    protected MyPagingAdapter(Context context, @NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = getItem(position);
        String userInfo = "id==" + user.id + " ; name ==" + user.userName;
        holder.mUserInfo.setText(userInfo);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mUserInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserInfo = itemView.findViewById(R.id.user_info);
        }
    }
}
