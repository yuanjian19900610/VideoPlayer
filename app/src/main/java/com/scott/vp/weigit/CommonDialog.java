package com.scott.vp.weigit;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scott.vp.R;
import com.scott.vp.utils.LogUtils;


/**
 * @author SCOTT
 * @version 1.0.3
 * @createTime 17/5/2
 */

public class CommonDialog extends Dialog implements View.OnClickListener {

    public BtnClickListener mBtnClickListener;
    private Context mContext;
    private TextView tv_message;
    private Button btn_cancel;
    private Button btn_sure;


    public CommonDialog(Context context) {
        this(context, R.style.DialogStyle);
        this.mContext = context;
        initView();
    }

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
    }

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        initView();
    }

    public void setOnBtnClickListener(BtnClickListener mBtnClickListener) {
        this.mBtnClickListener = mBtnClickListener;
    }

    private void initView() {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.layout_common_dialog, null);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        btn_sure = (Button) view.findViewById(R.id.btn_sure);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_sure.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        setContentView(view);
        int width = (int) (getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.4);
        int height = (int) (getWindow().getWindowManager().getDefaultDisplay().getHeight() * 0.35);
//        getWindow().setLayout(700, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setLayout(width, height);
        setCanceledOnTouchOutside(false);
        LogUtils.info("-----------btn_cancel 是否获取了焦点：" + btn_cancel.isFocused());

    }

    public void setMessage(String message) {
        if (message != null) {
            tv_message.setText(message);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                if (mBtnClickListener != null) {
                    mBtnClickListener.onClickSure();
                }
                dismiss();
                break;
            case R.id.btn_cancel:
                if (mBtnClickListener != null) {
                    mBtnClickListener.onClickCancel();
                }
                dismiss();
                break;
        }
    }

    public void setButtonText(String leftStr, String rightStr) {
        btn_cancel.setText(leftStr);
        btn_sure.setText(rightStr);
    }

    public void setSureBackground(int resId) {
        if (btn_sure != null) {
            btn_sure.setBackgroundResource(resId);
        }
    }

    public void setCancelBackground(int resId) {
        if (btn_cancel != null) {
            btn_cancel.setBackgroundResource(resId);
        }
    }

    public interface BtnClickListener {
        void onClickSure();

        void onClickCancel();
    }
}
