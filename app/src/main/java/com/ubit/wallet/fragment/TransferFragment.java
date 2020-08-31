package com.ubit.wallet.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.activity.CaptureActivity;
import com.ubit.wallet.bean.AssetsBean;
import com.ubit.wallet.event.CaptureEvent;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.Constants;
import com.ubit.wallet.utils.MoneyUtil;
import com.ubit.wallet.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class TransferFragment extends BaseFragment {

    private AssetsBean.ItemBean mCurrentAsset;

    private BigDecimal mFeeDecimal;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transfer;
    }

    @Override
    protected void onViewCreated(View view) {
        EventBus.getDefault().register(this);
        mCurrentAsset = (AssetsBean.ItemBean) getArguments().getSerializable(Constants.BUNDLE_EXTRA);
        setText(R.id.tvTitle, getString(R.string.app_xxx_wallet, mCurrentAsset.getName()));
        setImage(R.id.ivCoinIcon, mContext.getResources().getIdentifier("app_" + mCurrentAsset.getName().toLowerCase(),
                "drawable", mContext.getPackageName()));
        setText(R.id.tvRestValue, getString(R.string.app_rest_value, mCurrentAsset.getName(), mCurrentAsset.getAmount()));
        setText(R.id.tvTransferValue, mCurrentAsset.getName());
        setViewsOnClickListener(R.id.tvTransfer, R.id.tvAll, R.id.ivQrCode);
        getTransferFee();
        initListener();
    }


    private void initListener() {
        final EditText etTransferMoney = fv(R.id.etTransferMoney);
        setText(R.id.tvTransferValue, "--- " + mCurrentAsset.getName());
        etTransferMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = etTransferMoney.getText().toString();
                if (text.length() > 1 && text.startsWith("0") && text.charAt(1) != '.') {
                    text = text.substring(1);
                    etTransferMoney.setText(text);
                    etTransferMoney.setSelection(text.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (mFeeDecimal != null) {
                        try {
                            BigDecimal money = new BigDecimal(s.toString());
                            BigDecimal result;
                            if (MoneyUtil.moneyComp(money, mFeeDecimal) > 0) {
                                result = money.subtract(mFeeDecimal);
                            } else {
                                result = new BigDecimal(0);
                            }
                            String moneyStr = MoneyUtil.formatMoney8(result);
                            setText(R.id.tvTransferValue, moneyStr + mCurrentAsset.getName());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    setText(R.id.tvTransferValue, "--- " + mCurrentAsset.getName());
                }
            }
        });
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvTransfer:
                String moneyStr = getTextById(R.id.etTransferMoney);
                String receiveUrl = getTextById(R.id.etAddress);
                int symbol = getIndexByType(mCurrentAsset.getName());
                if (TextUtils.isEmpty(moneyStr)) {
                    showToast(R.string.app_input_transfer_number);
                    return;
                }
                if (TextUtils.isEmpty(receiveUrl)) {
                    showToast(R.string.app_input_receive_url);
                    return;
                }
                if ("TTH".equals(mCurrentAsset.getName())) {
                    try {
                        double money = Double.parseDouble(moneyStr);
                        if (money < 110) {
                            showToast(R.string.app_min_transfer_money);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        showToast(getString(R.string.app_money_number));
                        return;
                    }
                }
                HttpMethods.getInstance().transfer(DataManager.getInstance().getMyInfo().getToken(),
                        moneyStr, receiveUrl, mCurrentAsset.getAddress(), String.valueOf(symbol), mFeeDecimal.toString(),
                        new HttpObserver(new SubscriberOnNextListener() {
                            @Override
                            public void onNext(Object o, String msg) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                showToast(getString(R.string.app_transfering));
                                getActivity().finish();
                            }
                        }, getActivity(), (BaseActivity) getActivity()));
                break;
            case R.id.tvAll:
                String amount = mCurrentAsset.getAmount();
                if ("TTH".equals(mCurrentAsset.getName().toUpperCase()) && !TextUtils.isEmpty(amount) && amount.contains(".")) {
                    amount = amount.split("\\.")[0];
                }
                setText(R.id.etTransferMoney, amount);
                break;
            case R.id.ivQrCode:
                jumpScan();
                break;
        }
    }

    private void getTransferFee() {
        HttpMethods.getInstance().get_fee(DataManager.getInstance().getMyInfo().getToken(),
                new HttpObserver(new SubscriberOnNextListener<HashMap<String, String>>() {
                    @Override
                    public void onNext(HashMap<String, String> map, String msg) {
                        if (getActivity() == null || getView() == null) {
                            return;
                        }
                        if (map != null && map.containsKey(mCurrentAsset.getName().toLowerCase())) {
                            mFeeDecimal = new BigDecimal(map.get(mCurrentAsset.getName().toLowerCase()));
                            setText(R.id.tvFee, mFeeDecimal + " " + mCurrentAsset.getName());
                        }
                    }
                }, getActivity(), false, (BaseActivity) getActivity()));
    }

    private void jumpScan() {
        // 先判断是否有权限。
        PermissionUtils.permission(PermissionConstants.CAMERA).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                // 有权限，直接do anything.
                gotoPager(CaptureActivity.class);
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                //申请失败需要重新申请
                if (!permissionsDeniedForever.isEmpty()) {
                    showOpenAppSettingDialog();
                    return;
                }
            }
        }).request();

    }

    /**
     * 系统设置权限
     */
    private void showOpenAppSettingDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(ActivityUtils.getTopActivity())
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok, ((dialog, which) -> {
                    PermissionUtils.launchAppDetailsSettings();
                    dialog.dismiss();
                })).setOnCancelListener((dialog -> {
            dialog.dismiss();
        })).setCancelable(false)
                .create()
                .show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CaptureEvent event) {
        if (getView() != null && event != null && !TextUtils.isEmpty(event.getUrl())) {
            setText(R.id.etAddress, Utils.getScanUrl(event.getUrl()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private int getIndexByType(String str) {
        if ("TTH".equals(str)) {
            return 1;
        } else if ("ETH".equals(str)) {
            return 2;
        } else if ("BTC".equals(str)) {
            return 5;
        } else if ("USDT".equals(str)) {
            return 3;
        }
        return 0;
    }


}
