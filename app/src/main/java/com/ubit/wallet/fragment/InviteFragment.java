package com.ubit.wallet.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ubit.wallet.R;
import com.ubit.wallet.adapter.InviteAdapter;

public class InviteFragment extends BaseFragment {

    private InviteAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.ivQrCode);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().addData("");
        getAdapter().addData("");
        getAdapter().addData("");

        fv(R.id.tvInviteCode).bringToFront();
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivQrCode:
                gotoPager(MyInviteQrCodeFragment.class);
                break;

        }
    }

    private InviteAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new InviteAdapter(getActivity());
        }
        return mAdapter;
    }

    protected boolean isNeedSetTopStyle() {
        return false;
    }
}
