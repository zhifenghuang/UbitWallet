package com.ubit.wallet.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.bean.UserInfoBean;
import com.ubit.wallet.dialog.MyDialogFragment;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.Utils;

public class MyFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvMsgCenter, R.id.tvRecruitFriend, R.id.tvServiceCenter, R.id.llSafeCenter, R.id.llLanguage, R.id.llLogout, R.id.ivAvatar);
    }

    @Override
    public void updateUIText() {
        final UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
        setText(R.id.tvMobile, TextUtils.isEmpty(myInfo.getPhone()) ? myInfo.getEmail() : "+" + myInfo.getPhone_code() + " " + myInfo.getPhone());
        setText(R.id.tvNick, myInfo.getNickname());
        Utils.displayAvatar(getActivity(), R.drawable.shape_000000_circle, myInfo.getAvatar(), fv(R.id.ivAvatar));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvRecruitFriend:
                gotoPager(InviteFragment.class);
                break;
            case R.id.tvMsgCenter:
                gotoPager(MessageCenterFragment.class);
                break;
            case R.id.tvServiceCenter:
                gotoPager(ServiceFragment.class);
                break;
            case R.id.ivAvatar:
                gotoPager(EditUserInfoFragment.class);
                break;
            case R.id.llSafeCenter:
                gotoPager(SafeCenterFragment.class);
                break;
            case R.id.llLanguage:
                gotoPager(LanguageSettingFragment.class);
                break;
            case R.id.llLogout:
                showLogoutOutDialog();
                break;
        }
    }

    protected boolean isNeedSetTopStyle() {
        return false;
    }

    private void showLogoutOutDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_two_btn_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                view.findViewById(R.id.tv1).setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.tv2)).setText(getString(R.string.app_are_you_sure_logout));
                ((TextView) view.findViewById(R.id.btn1)).setText(getString(R.string.app_ok));
                ((TextView) view.findViewById(R.id.btn2)).setText(getString(R.string.app_cancel));
                dialogFragment.setDialogViewsOnClickListener(view, R.id.btn1, R.id.btn2);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.btn1) {
                    DataManager.getInstance().loginOut();
                    ((BaseActivity) getActivity()).finishAllActivity();
                    gotoPager(LoginFragment.class);
                }
            }
        });
        dialogFragment.show(getChildFragmentManager(), "MyDialogFragment");
    }
}
