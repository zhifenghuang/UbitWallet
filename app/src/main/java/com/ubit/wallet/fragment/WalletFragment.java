package com.ubit.wallet.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ubit.wallet.R;
import com.ubit.wallet.adapter.AssetsRecordAdapter;
import com.ubit.wallet.bean.AssetsBean;
import com.ubit.wallet.dialog.MyDialogFragment;
import com.ubit.wallet.utils.Constants;
import com.ubit.wallet.utils.QRCodeUtil;
import com.ubit.wallet.utils.Utils;

import java.util.ArrayList;

public class WalletFragment extends BaseFragment {

    private AssetsRecordAdapter mAdapter;
    private Bitmap mQrBmp;

    private AssetsBean.ItemBean mCurrentAsset;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void onViewCreated(View view) {
        mCurrentAsset = (AssetsBean.ItemBean) getArguments().getSerializable(Constants.BUNDLE_EXTRA);
        setImage(R.id.ivCoinIcon, mContext.getResources().getIdentifier("app_" + mCurrentAsset.getName().toLowerCase(),
                "drawable", mContext.getPackageName()));
        setText(R.id.tvTitle, getString(R.string.app_xxx_wallet, mCurrentAsset.getName()));
        setText(R.id.tvCoinName, mCurrentAsset.getName());
        setText(R.id.tvCoinFullName, mCurrentAsset.getFullName());
        setText(R.id.tvTotalValue, mCurrentAsset.getAmount());
        setText(R.id.tvEqualValue, "≈ $ " + mCurrentAsset.getTousdt());
        setText(R.id.tvAddress, mCurrentAsset.getAddress());
        setTopStatusBarStyle(R.id.rlTop);
        setViewsOnClickListener(R.id.ivCopyAddress, R.id.llTransfer, R.id.llCollectMoney, R.id.llFilter);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        getAdapter().setNewInstance(list);
    }

    private AssetsRecordAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new AssetsRecordAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    gotoPager(AssetsRecordDetailFragment.class);
                }
            });
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivCopyAddress:
                Utils.copyData(mContext, mCurrentAsset.getAddress());
                showToast(getString(R.string.app_copy_succress));
                break;
            case R.id.llTransfer:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BUNDLE_EXTRA, mCurrentAsset);
                gotoPager(TransferFragment.class, bundle);
                break;
            case R.id.llCollectMoney:
                showQrCodeDialog();
                break;
            case R.id.llFilter:
                showFilterDialog(v);
                break;
        }
    }

    protected boolean isNeedSetTopStyle() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mQrBmp != null && !mQrBmp.isRecycled()) {
            mQrBmp.recycle();
        }
        mQrBmp = null;
    }


    private void showQrCodeDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_qrcode_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.tvCopy);
                ((ImageView) view.findViewById(R.id.ivCoinIcon)).setImageResource(mContext.getResources().getIdentifier("app_" + mCurrentAsset.getName().toLowerCase(),
                        "drawable", mContext.getPackageName()));
                ((TextView) view.findViewById(R.id.tv)).setText(getString(R.string.app_xxx_address, mCurrentAsset.getName()));
                if (mQrBmp != null) {
                    mQrBmp.recycle();
                }
                mQrBmp = QRCodeUtil.createQRImage(mContext, mCurrentAsset.getAddress(), null);
                ((ImageView) view.findViewById(R.id.ivQrCode)).setImageBitmap(mQrBmp);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tvCopy:
                        Utils.copyData(mContext, mCurrentAsset.getAddress());
                        showToast(getString(R.string.app_copy_succress));
                        break;
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
        dialogFragment.setOnDismiss(new MyDialogFragment.IDismissListener() {
            @Override
            public void onDismiss() {
                if (mQrBmp != null) {
                    mQrBmp.recycle();
                }
                mQrBmp = null;
            }
        });
    }


    public void showFilterDialog(final View locationView) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_filter_record_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.llParent,
                        R.id.ll, R.id.tvAll, R.id.tvTransfer, R.id.tvCollect, R.id.tvProfit);
                View llReport = view.findViewById(R.id.ll);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llReport.getLayoutParams();
                int[] location = new int[2];
                locationView.getLocationOnScreen(location);
                lp.topMargin = location[1] + view.getHeight();
                llReport.setLayoutParams(lp);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tvAll:
                        break;
                    case R.id.tvTransfer:
                        break;
                    case R.id.tvCollect:
                        break;
                    case R.id.tvProfit:
                        break;
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }
}
