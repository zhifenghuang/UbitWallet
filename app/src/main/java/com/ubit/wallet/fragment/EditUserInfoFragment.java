package com.ubit.wallet.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.content.FileProvider;

import com.ubit.wallet.BuildConfig;
import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.bean.UserInfoBean;
import com.ubit.wallet.dialog.MyDialogFragment;
import com.ubit.wallet.event.UploadAvatarEvent;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.OnHttpErrorListener;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.manager.UPYFileUploadManger;
import com.ubit.wallet.utils.BitmapUtil;
import com.ubit.wallet.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public class EditUserInfoFragment extends BaseFragment {

    private static final int ALBUM_REQUEST_CODE = 10002;
    private static final int TAKE_PHOTO_REQUEST_CODE = 10001;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_user_info;
    }

    @Override
    protected void onViewCreated(View view) {
        EventBus.getDefault().register(this);
        setViewsOnClickListener(R.id.llAvatar, R.id.llNick);
    }

    @Override
    public void updateUIText() {
        UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
        setText(R.id.tvNick, myInfo.getNickname());
        Utils.displayAvatar(getActivity(), R.drawable.shape_000000_circle, myInfo.getAvatar(), fv(R.id.ivAvatar));
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
                        if (!Utils.isGrantPermission(getActivity(),
                                Manifest.permission.CAMERA)) {
                            ((BaseActivity) getActivity()).requestPermission(0, Manifest.permission.CAMERA);
                        } else {
                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File outFile = new File(Utils.getSaveFilePath(getActivity(), "output.jpg"));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", outFile);
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                            } else {
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
                            }
                            startActivityForResult(openCameraIntent, TAKE_PHOTO_REQUEST_CODE);
                        }
                        break;
                    case R.id.tvAlbum:
                        if (!Utils.isGrantPermission(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            ((BaseActivity) getActivity()).requestPermission(0, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");//相片类型
                            startActivityForResult(intent, ALBUM_REQUEST_CODE);
                        }
                        break;
                }
            }
        });
        dialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveAvatarUrl(UploadAvatarEvent avatar) {
        if (getView() == null) {
            return;
        }
        if (avatar.isSuccess()) {
            updateAvatar(avatar.getUrl());
        } else {
            ((BaseActivity) getActivity()).hideLoading();
            showToast(getString(R.string.app_upload_avatar_failed));
        }
    }

    private void updateAvatar(final String avatar) {
        HttpMethods.getInstance().updateAvatar(DataManager.getInstance().getToken(), avatar,
                new HttpObserver(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o, String msg) {
                        UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
                        myInfo.setAvatar(avatar);
                        DataManager.getInstance().saveUserInfo(myInfo);
                        if (getActivity() == null || getView() == null) {
                            return;
                        }
                        Utils.displayAvatar(getActivity(), R.drawable.shape_000000_circle, myInfo.getAvatar(), fv(R.id.ivAvatar));
                    }
                }, getActivity(), new OnHttpErrorListener() {
                    @Override
                    public void onConnectError(Throwable e) {
                        if (getActivity() == null || getView() == null) {
                            return;
                        }
                        showToast(getString(R.string.app_upload_avatar_failed));
                    }

                    @Override
                    public void onServerError(int errorCode, String errorMsg) {
                        if (getActivity() == null || getView() == null) {
                            return;
                        }
                        showToast(getString(R.string.app_upload_avatar_failed));
                    }
                }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ALBUM_REQUEST_CODE) {
                try {
                    String filePath;
                    int sdkVersion = Build.VERSION.SDK_INT;
                    if (sdkVersion >= 19) { // api >= 19
                        filePath = ((BaseActivity) getActivity()).getRealPathFromUriAboveApi19(data.getData());
                    } else { // api < 19
                        filePath = ((BaseActivity) getActivity()).getRealPathFromUriBelowAPI19(data.getData());
                    }
                    File file = new File(filePath);
                    if (file.length() > 2 * 1024 * 1024) {  //大于2M压缩处理
                        Bitmap bmp = BitmapUtil.getBitmapFromFile(filePath, getDisplaymetrics().widthPixels, getDisplaymetrics().heightPixels);
                        file = new File(Utils.saveJpeg(bmp, getActivity()));
                    }
                    ((BaseActivity) getActivity()).showLoading();
                    UPYFileUploadManger.getInstance().uploadFile(file);
                } catch (Exception e) {

                }
            } else if (requestCode == TAKE_PHOTO_REQUEST_CODE) {
                String filePath = Utils.getSaveFilePath(getActivity(), "output.jpg");
                Bitmap bmp = BitmapUtil.getBitmapFromFile(filePath, getDisplaymetrics().widthPixels, getDisplaymetrics().heightPixels);
                filePath = Utils.saveJpeg(bmp, getActivity());
                bmp.recycle();
                bmp = null;
                ((BaseActivity) getActivity()).showLoading();
                UPYFileUploadManger.getInstance().uploadFile(new File(filePath));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
