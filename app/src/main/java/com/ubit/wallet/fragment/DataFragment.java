package com.ubit.wallet.fragment;

import android.view.View;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.adapter.DataDetailAdapter;
import com.ubit.wallet.adapter.DataTypeAdapter;
import com.ubit.wallet.bean.ArticleBean;
import com.ubit.wallet.bean.DataTypeBean;
import com.ubit.wallet.bean.ListBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;

import java.util.ArrayList;
import java.util.HashMap;

public class DataFragment extends BaseFragment {

    private DataTypeAdapter mAdapter;

    private DataDetailAdapter mDataDetailAdapter;

    private HashMap<Integer, ArrayList<ArticleBean>> mMap;

    private int mCurrentType = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvService);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<DataTypeBean.DataType> list = DataManager.getInstance().getDataTypes();
        getAdapter().setNewInstance(list);
        getDataTypes();
        if (list != null && !list.isEmpty()) {
            mCurrentType = list.get(0).getId();
            getDataList(mCurrentType);
        }
        ExpandableListView listView = view.findViewById(R.id.listView);
        listView.setGroupIndicator(null);
        listView.setAdapter(getDataDetailAdapter());
    }

    @Override
    public void updateUIText() {

    }

    private DataTypeAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new DataTypeAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    mAdapter.setSelectIndex(position);
                    mCurrentType = getAdapter().getItem(position).getId();
                    getDataDetailAdapter().setData(getMap().get(mCurrentType));
                    getDataList(mCurrentType);
                }
            });
        }
        return mAdapter;
    }

    private DataDetailAdapter getDataDetailAdapter() {
        if (mDataDetailAdapter == null) {
            mDataDetailAdapter = new DataDetailAdapter(getActivity());
        }
        return mDataDetailAdapter;
    }

    private HashMap<Integer, ArrayList<ArticleBean>> getMap() {
        if (mMap == null) {
            mMap = new HashMap<>();
        }
        return mMap;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvService:
                gotoPager(ServiceFragment.class);
                break;
        }
    }

    private void getDataTypes() {
        HttpMethods.getInstance().get_data_category(new HttpObserver(new SubscriberOnNextListener<DataTypeBean>() {
            @Override
            public void onNext(DataTypeBean bean, String msg) {
                if (bean == null || bean.getList() == null) {
                    return;
                }
                DataManager.getInstance().saveDataType(bean.getList());
                if (getActivity() == null || getView() == null) {
                    return;
                }
                int index = 0;
                for (DataTypeBean.DataType type : bean.getList()) {
                    if (type.getId() == mCurrentType) {
                        break;
                    }
                    ++index;
                }
                if (mCurrentType == -1) {
                    mCurrentType = bean.getList().get(index).getId();
                    getDataList(mCurrentType);
                }
                getAdapter().setNewInstance(bean.getList());
                getAdapter().setSelectIndex(index);
                getDataDetailAdapter().setData(getMap().get(mCurrentType));
                getDataList(mCurrentType);
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }

    private void getDataList(final int type) {
        HttpMethods.getInstance().data_list(DataManager.getInstance().getToken(), String.valueOf(type),
                new HttpObserver(new SubscriberOnNextListener<ListBean<ArticleBean>>() {
                    @Override
                    public void onNext(ListBean<ArticleBean> bean, String msg) {
                        if (getActivity() == null || getView() == null || bean == null) {
                            return;
                        }
                        getMap().put(type, bean.getList());
                        if (mCurrentType == type) {
                            getDataDetailAdapter().setData(bean.getList());
                        }
                    }
                }, getActivity(), false, (BaseActivity) getActivity()));
    }

}
