package com.ubit.wallet.fragment;

import android.view.View;

import com.ubit.wallet.R;
import com.ubit.wallet.dialog.MyDialogFragment;

public class EditUserInfoFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_user_info;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.llAvatar, R.id.llNick);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.llAvatar:
                showSelectPhotoDialog();
                break;
            case R.id.llNick:
                gotoPager(UpdateNickFragment.class);
                break;
        }
    }

    private void showSelectPhotoDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_select_photo_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.paddingView,
                        R.id.tvCancel,
                        R.id.tvTakePhoto, R.id.tvAlbum);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tvTakePhoto:
                    case R.id.tvAlbum:
                        break;
                }
            }
        });
        dialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
    }
}
