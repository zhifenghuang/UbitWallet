package com.ubit.wallet.fragment;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.ubit.wallet.R;
import com.ubit.wallet.utils.QRCodeUtil;

public class MyInviteQrCodeFragment extends BaseFragment {

    private Bitmap mQrBmp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_invite_qrcode;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvSave);
        mQrBmp = QRCodeUtil.createQRImage(mContext, "www.donwan.com", null);
        ((ImageView) view.findViewById(R.id.ivQrCode)).setImageBitmap(mQrBmp);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvSave:
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mQrBmp != null && !mQrBmp.isRecycled()) {
            mQrBmp.recycle();
        }
        mQrBmp = null;
    }

    protected boolean isNeedSetTopStyle() {
        return false;
    }
}
