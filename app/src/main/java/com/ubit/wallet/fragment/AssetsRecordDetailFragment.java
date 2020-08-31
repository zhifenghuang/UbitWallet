package com.ubit.wallet.fragment;

import android.view.View;

import com.ubit.wallet.R;

public class AssetsRecordDetailFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_assets_record_detail;
    }

    @Override
    protected void onViewCreated(View view) {
    //    setText(R.id.tvTitle, R.string.wallet_record);

    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

        }
    }


}
