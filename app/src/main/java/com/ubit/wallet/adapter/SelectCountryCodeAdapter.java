package com.ubit.wallet.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ubit.wallet.R;
import com.ubit.wallet.bean.PhoneCodeBean;

import org.jetbrains.annotations.NotNull;

public class SelectCountryCodeAdapter extends BaseQuickAdapter<PhoneCodeBean.CodeBean, BaseViewHolder> {
    public SelectCountryCodeAdapter() {
        super(R.layout.item_select_country_code);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, PhoneCodeBean.CodeBean codeBean) {
        helper.setText(R.id.tvCountry, codeBean.getCountry())
                .setText(R.id.tvCode, "+" + codeBean.getCode());
    }
}
