package com.ubit.wallet.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ubit.wallet.R;
import com.ubit.wallet.bean.DataTypeBean;

import org.jetbrains.annotations.NotNull;

public class DataTypeAdapter extends BaseQuickAdapter<DataTypeBean.DataType, BaseViewHolder> {

    private Context mContext;
    private int mSelectIndex;

    public DataTypeAdapter(Context context) {
        super(R.layout.item_data_type);
        mContext = context;
        mSelectIndex = 0;
    }

    public void setSelectIndex(int selectIndex) {
        mSelectIndex = selectIndex;
        notifyDataSetChanged();
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, DataTypeBean.DataType item) {
        helper.setText(R.id.tvName, item.getName());
        TextView tvName = helper.getView(R.id.tvName);
        View line = helper.getView(R.id.line);
        if (helper.getAdapterPosition() == mSelectIndex) {
            line.setVisibility(View.VISIBLE);
            tvName.setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        } else {
            line.setVisibility(View.INVISIBLE);
            tvName.setTextColor(ContextCompat.getColor(mContext, R.color.color_b2_b2_b2));
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }
    }

//    public long getCurrentType() {
//
//    }
}
