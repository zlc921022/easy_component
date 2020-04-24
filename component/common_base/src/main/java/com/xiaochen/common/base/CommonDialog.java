package com.xiaochen.common.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : 对话框工具类
 */
public class CommonDialog implements View.OnClickListener {

    private final TextView mDialogOk;
    private final TextView mDialogCancel;
    private final TextView mTvContent;
    private static WeakReference<Activity> mReference;
    private final AlertDialog mAlertDialog;
    private final TextView mTvTitle;

    private CommonDialog(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(
                R.layout.layout_common_dialog, null, false);
        builder.setView(dialogView);
        mAlertDialog = builder.create();

        mTvTitle = dialogView.findViewById(R.id.tv_title);
        mDialogOk = dialogView.findViewById(R.id.dialog_ok);
        mDialogCancel = dialogView.findViewById(R.id.dialog_cancel);
        mTvContent = dialogView.findViewById(R.id.tv_content);
        mDialogOk.setOnClickListener(this);
        mDialogCancel.setOnClickListener(this);
        if(mAlertDialog.getWindow() != null) {
            mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        }
    }

    public static CommonDialog getDialog(Activity activity) {
        mReference = new WeakReference<>(activity);
        return new CommonDialog(activity);
    }

    public void showDialog() {
        if (isActive()) {
            mAlertDialog.setCanceledOnTouchOutside(false);
            if (!mAlertDialog.isShowing()) {
                mAlertDialog.show();
            }
        }
    }

    //activity是否存在
    private boolean isActive() {
        Activity activity = mReference.get();
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    public boolean isShowing() {
        return mAlertDialog.isShowing();
    }

    public void dismiss() {
        if (isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    public CommonDialog setTitle(String title){
        mTvTitle.setText(title);
        return this;
    }

    public CommonDialog setContent(String content) {
        mTvContent.setText(content);
        return this;
    }

    public CommonDialog setDialogOkText(String text){
        mDialogOk.setText(text);
        return this;
    }

    public CommonDialog setDialogCancelText(String text){
        mDialogCancel.setText(text);
        return this;
    }

    public CommonDialog setDialogCancelShow(int visible){
        mDialogCancel.setVisibility(visible);
        return this;
    }

    public CommonDialog setCanceledOnTouchOutside(boolean cancel){
        mAlertDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (mOnDialogBtnClickListener == null) {
            return;
        }
        if (id == R.id.dialog_ok) {
            dismiss();
            mOnDialogBtnClickListener.onBtnOkClick();
        } else if (id == R.id.dialog_cancel) {
            dismiss();
            mOnDialogBtnClickListener.onBtnCancelClick();
        }
    }

    public interface OnDialogBtnClickListener {
        void onBtnOkClick();

        default void onBtnCancelClick() {
        }
    }

    private OnDialogBtnClickListener mOnDialogBtnClickListener;

    public CommonDialog setOnDialogBtnClickListener(OnDialogBtnClickListener onDialogBtnClickListener) {
        mOnDialogBtnClickListener = onDialogBtnClickListener;
        return this;
    }
}
