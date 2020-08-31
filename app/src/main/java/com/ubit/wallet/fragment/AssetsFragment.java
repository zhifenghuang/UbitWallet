package com.ubit.wallet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.adapter.AssetsAdapter;
import com.ubit.wallet.bean.AssetsBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.Constants;

public class AssetsFragment extends BaseFragment {

    private AssetsAdapter mAdapter;
    private AssetsBean mAssetsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_assets;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        mAssetsBean = DataManager.getInstance().getMyAssets();
        resetAssetsUI();
    }

    private void resetAssetsUI() {
        if (mAssetsBean == null) {
            return;
        }
        setText(R.id.tvTotal, mAssetsBean.getTotal());
        setText(R.id.tvEqual, "≈ $ " + mAssetsBean.getTotal());
        getAdapter().setNewInstance(mAssetsBean.getList());
        getAdapter().setUserInfo(DataManager.getInstance().getUserInfo());
        DataManager.getInstance().saveMyAssets(mAssetsBean);
    }

    private AssetsAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new AssetsAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    AssetsBean.ItemBean bean = mAdapter.getItem(position);
                    if (!TextUtils.isEmpty(bean.getAddress())) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.BUNDLE_EXTRA, mAdapter.getItem(position));
                        gotoPager(WalletFragment.class, bundle);
                    }
                }
            });
        }
        return mAdapter;
    }

    public void resetAddress() {
        if (getView() == null) {
            return;
        }
        getAdapter().setUserInfo(DataManager.getInstance().getUserInfo());
    }

    @Override
    public void onResume() {
        super.onResume();
        getAssetsInfo();
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }


    private void getAssetsInfo() {
        HttpMethods.getInstance().get_assets(DataManager.getInstance().getMyInfo().getToken(),
                new HttpObserver(new SubscriberOnNextListener<AssetsBean>() {
                    @Override
                    public void onNext(AssetsBean bean, String msg) {
                        if (bean == null) {
                            return;
                        }
                        if (getActivity() == null || getView() == null) {
                            return;
                        }
                        mAssetsBean = bean;
                        resetAssetsUI();
                    }
                }, getActivity(), false, (BaseActivity) getActivity()));
    }


}
