package com.ubit.wallet.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ubit.wallet.R;

import org.jetbrains.annotations.NotNull;

public class InviteAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public InviteAdapter(Context context) {
        super(R.layout.item_invite);
        mContext = context;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, String item) {


    }
}
