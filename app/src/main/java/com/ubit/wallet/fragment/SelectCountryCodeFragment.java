package com.ubit.wallet.fragment;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.adapter.SelectCountryCodeAdapter;
import com.ubit.wallet.bean.PhoneCodeBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;

public class SelectCountryCodeFragment extends BaseFragment {

    private SelectCountryCodeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_phone_code;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getCountryCodes();
    }

    private SelectCountryCodeAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new SelectCountryCodeAdapter();
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    DataManager.getInstance().setObject(mAdapter.getItem(position));
                    getActivity().finish();
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

    }

    private void getCountryCodes() {
        HttpMethods.getInstance().get_codes("2", new HttpObserver(new SubscriberOnNextListener<PhoneCodeBean>() {
            @Override
            public void onNext(PhoneCodeBean bean, String msg) {
                if (getActivity() == null || getView() == null || bean == null || bean.getPhone_code() == null) {
                    return;
                }
                getAdapter().setNewInstance(bean.getPhone_code());
                getAdapter().notifyDataSetChanged();
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
