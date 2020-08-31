package com.ubit.wallet.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ubit.wallet.R;
import org.jetbrains.annotations.NotNull;

public class AssetsRecordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public AssetsRecordAdapter(Context context) {
        super(R.layout.item_assets_record);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String item) {

        if (getItemCount() == 1) {
            helper.getView(R.id.llParent).setBackgroundResource(R.drawable.bg_wallet_white_round);
        } else {
            if (helper.getAdapterPosition() == 0) {
                helper.getView(R.id.llParent).setBackgroundResource(R.drawable.bg_wallet_white_round_top);
            } else if (helper.getAdapterPosition() == getItemCount() - 1) {
                helper.getView(R.id.llParent).setBackgroundResource(R.drawable.bg_wallet_white_round_bottom);
            } else {
                helper.getView(R.id.llParent).setBackgroundColor(Color.WHITE);
            }
        }
        helper.setGone(R.id.line, helper.getAdapterPosition() == getItemCount() - 1);
    }

}
