package com.ubit.wallet.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ubit.wallet.R;
import com.ubit.wallet.bean.AssetsBean;
import com.ubit.wallet.bean.UserInfoBean;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AssetsAdapter extends BaseQuickAdapter<AssetsBean.ItemBean, BaseViewHolder> {

    private Context mContext;

    public AssetsAdapter(Context context) {
        super(R.layout.item_assets);
        mContext = context;
    }

    public void setUserInfo(UserInfoBean.Info userInfo) {
        List<AssetsBean.ItemBean> list = getData();
        if (userInfo != null && !list.isEmpty()) {
            String name;
            for (AssetsBean.ItemBean item : list) {
                name = item.getName().toUpperCase();
                if (name.equals("BTC")) {
                    item.setAddress(userInfo.getAddress_btc());
                } else if (item.getName().toUpperCase().equals("USDT")) {
                    item.setAddress(userInfo.getAddress_eth());
                } else {
                    item.setAddress(userInfo.getAddress_eth());
                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, AssetsBean.ItemBean item) {
        helper.setImageResource(R.id.ivCoinIcon, mContext.getResources().getIdentifier("app_" + item.getName().toLowerCase(),
                "drawable", mContext.getPackageName()))
                .setText(R.id.tvCoinName, item.getName())
                .setText(R.id.tvTotalValue, item.getAmount())
                .setText(R.id.tvEqualValue, "≈ $ " + item.getTousdt())
                .setText(R.id.tvAddress, item.getAddress());

    }
}
