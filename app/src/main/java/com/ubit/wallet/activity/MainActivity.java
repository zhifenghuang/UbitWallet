package com.ubit.wallet.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ubit.wallet.R;
import com.ubit.wallet.bean.AssetsBean;
import com.ubit.wallet.bean.UserInfoBean;
import com.ubit.wallet.fragment.AssetsFragment;
import com.ubit.wallet.fragment.BaseFragment;
import com.ubit.wallet.fragment.QuotationFragment;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {


    private ArrayList<BaseFragment> mBaseFragment;
    private Fragment mCurrentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initViews();
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new AssetsFragment());
        mBaseFragment.add(new QuotationFragment());
        mBaseFragment.add(new QuotationFragment());
        mBaseFragment.add(new QuotationFragment());
        mBaseFragment.add(new QuotationFragment());
    }

    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void initViews() {
        switchFragment(mBaseFragment.get(0));
        resetBottomBar(0);
        LinearLayout llBottom = findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        View itemView;
        for (int i = 0; i < count; ++i) {
            itemView = llBottom.getChildAt(i);
            itemView.setTag(i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) view.getTag();
                    switchFragment(mBaseFragment.get(tag));
                    resetBottomBar(tag);
                }
            });
        }
    }

    private void resetBottomBar(int currentPos) {
        LinearLayout llBottom = findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        ViewGroup itemView;
        for (int i = 0; i < count; ++i) {
            itemView = (ViewGroup) llBottom.getChildAt(i);
            (((ImageView) itemView.getChildAt(0))).setImageResource(getResIdByIndex(i, currentPos == i));
            (((TextView) itemView.getChildAt(1))).
                    setTextColor(ContextCompat.getColor(this, currentPos == i ? R.color.color_00_cf_7c : R.color.color_a0_ac_c0));
        }
    }

    private int getResIdByIndex(int index, boolean isCheck) {
        int id = 0;
        switch (index) {
            case 0:
                id = isCheck ? R.drawable.app_wallet_on : R.drawable.app_wallet_off;
                break;
            case 1:
                id = isCheck ? R.drawable.app_quotation_on : R.drawable.app_quotation_off;
                break;
            case 2:
                id = isCheck ? R.drawable.app_trade_on : R.drawable.app_trade_off;
                break;
            case 3:
                id = isCheck ? R.drawable.app_data_on : R.drawable.app_data_off;
                break;
            case 4:
                id = isCheck ? R.drawable.app_mine_on : R.drawable.app_mine_off;
                break;
        }
        return id;
    }

    /**
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                ft.add(R.id.fl, to, to.toString()).commit();
            } else {
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                ft.show(to).commit();
            }
        }
        mCurrentFragment = to;
    }

    private void getUserInfo() {
        HttpMethods.getInstance().get_user(DataManager.getInstance().getMyInfo().getToken(),
                new HttpObserver(new SubscriberOnNextListener<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean bean, String msg) {
                        if (bean == null || bean.getUser_info() == null) {
                            return;
                        }
                        DataManager.getInstance().saveUserInfo(bean.getUser_info());
                        ((AssetsFragment) mBaseFragment.get(0)).resetAddress();
                    }
                }, this, false, this));
    }

}
