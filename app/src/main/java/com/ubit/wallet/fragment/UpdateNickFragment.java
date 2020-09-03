package com.ubit.wallet.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.bean.UserInfoBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;

public class UpdateNickFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_nick;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRight);
        UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
        EditText etNick = view.findViewById(R.id.etNick);
        etNick.setText(myInfo.getNickname());
        etNick.setSelection(myInfo.getNickname().length());
        etNick.requestFocus();
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvRight:
                final String nick = getTextById(R.id.etNick).trim();
                if (TextUtils.isEmpty(nick)) {
                    showToast(R.string.app_please_input_nick);
                    return;
                }
                HttpMethods.getInstance().updateNick(DataManager.getInstance().getToken(), nick,
                        new HttpObserver(new SubscriberOnNextListener() {
                            @Override
                            public void onNext(Object o, String msg) {
                                UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
                                myInfo.setNickname(nick);
                                DataManager.getInstance().saveUserInfo(myInfo);
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                showToast(R.string.app_update_nick_success);
                                goBack();
                            }
                        }, getActivity(), (BaseActivity) getActivity()));
                break;
        }
    }
}
