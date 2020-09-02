package com.ubit.wallet.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ubit.wallet.R;
import com.ubit.wallet.adapter.AssetsAdapter;
import com.ubit.wallet.adapter.MessageCenterAdapter;

public class MessageCenterFragment extends BaseFragment {

    private MessageCenterAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_center;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().addData("");
        getAdapter().addData("");
        getAdapter().addData("");
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }

    private MessageCenterAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new MessageCenterAdapter(getActivity());
        }
        return mAdapter;
    }
}
