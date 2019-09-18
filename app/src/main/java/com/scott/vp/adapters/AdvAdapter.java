package com.scott.vp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.scott.vp.R;
import com.scott.vp.weigit.FocusRelativeLayout;


/**
 * Created by yuanjian on 17/9/25.
 */

public class AdvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private OnItemStateListener mListener;
    private static int[] mColorIds = {R.color.color_blue, R.color.color_gray, R.color.color_light_red,
            R.color.color_green, R.color.color_yellow, R.color.color_orange, R.color.color_black,
            R.color.color_personal_top};

    public AdvAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemStateListener(OnItemStateListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(View.inflate(mContext, R.layout.layout_adv_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
        viewHolder.mRelativeLayout.setBackgroundColor(ContextCompat.getColor(mContext, mColorIds[position]));
    }

    @Override
    public int getItemCount() {
        return mColorIds.length;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FocusRelativeLayout mRelativeLayout;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mRelativeLayout = (FocusRelativeLayout) itemView.findViewById(R.id.frl_main);
            mRelativeLayout.setOnClickListener(this);
            mRelativeLayout.setMyFocusChangeListener(new FocusRelativeLayout.MyFocusChangeListener() {
                @Override
                public void focusChanged(boolean isFocus) {
                    if(isFocus){
                        if (mListener != null) {
                            mListener.onItemClick(null, getAdapterPosition());
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemStateListener {
        void onItemClick(View view, int position);
    }

}
