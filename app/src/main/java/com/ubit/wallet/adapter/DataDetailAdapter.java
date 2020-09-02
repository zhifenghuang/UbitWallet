package com.ubit.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ubit.wallet.R;
import com.ubit.wallet.bean.ArticleBean;

import java.util.ArrayList;

public class DataDetailAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArticleBean> mList;
    private Context mContext;

    public DataDetailAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<ArticleBean> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        if (list != null) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public ArticleBean getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getSynopsis();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_data_group, null);
        }
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(getGroup(groupPosition).getTitle());
        ImageView ivArrow = convertView.findViewById(R.id.ivArrow);
        ivArrow.setRotation(isExpanded ? 90 : 0);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_data_child, null);
        }
        TextView tvContent = convertView.findViewById(R.id.tvContent);
        tvContent.setText(getChild(groupPosition, childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
