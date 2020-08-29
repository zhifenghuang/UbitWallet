package com.ubit.wallet.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.utils.StatusBarUtil;

/**
 * Fragment基类提供公共的页面跳转方面，公共弹窗等方法
 *
 * @author xiangwei.ma
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    // 标示是否第一次执行onStart页面
    private boolean mIsFirstOnStart = true;
    private DisplayMetrics mDisplaymetrics;
    protected Context mContext;

    protected boolean mIsToAnotherPage;

    protected abstract int getLayoutId();

    /**
     * 构造函数，不能使用带有参数的构造函数，因为系统自动回收后，会调用没有参数的构造函数
     */
    public BaseFragment() {
        super();
    }

    protected void setTopStatusBarStyle(View topView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topView.setPadding(0, StatusBarUtil.getStatusBarHeight(getActivity()) + topView.getPaddingTop(), 0, 0);
        }
    }

    protected void setTopStatusBarStyle(int viewId) {
        View topView = fv(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topView.setPadding(topView.getPaddingLeft(), StatusBarUtil.getStatusBarHeight(getActivity()) + topView.getPaddingTop(), topView.getPaddingRight(), topView.getPaddingBottom());
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    protected void setEditTextHint(int id, String text) {
        EditText et = (EditText) getView().findViewById(id);
        et.setHint(text == null ? "" : text);
    }


    protected void setText(TextView tv, String text) {
        tv.setText(text == null ? "" : text);
    }

    protected void setText(int id, String text) {
        TextView tv = (TextView) getView().findViewById(id);
        tv.setText(text == null ? "" : text);
    }

    protected void setText(int id, int textId) {
        TextView tv = getView().findViewById(id);
        tv.setText(getString(textId));
    }

    protected void setTextColor(int id, int clorId) {
        TextView tv = getView().findViewById(id);
        tv.setTextColor(getResources().getColor(clorId));
    }

    protected void setImage(int id, int drawableId) {
        ImageView iv = (ImageView) getView().findViewById(id);
        iv.setImageResource(drawableId);
    }

    protected void setBackground(int id, int drawableId) {
        View view = getView().findViewById(id);
        view.setBackgroundResource(drawableId);
    }


    protected String getTextById(int resId) {
        return ((TextView) getView().findViewById(resId)).getText().toString().trim();
    }

    protected void setViewVisible(int... viewIds) {
        if (viewIds == null || getView() == null) {
            return;
        }
        for (int viewId : viewIds) {
            View view = getView().findViewById(viewId);
            if (view != null)
                view.setVisibility(View.VISIBLE);
        }
    }

    protected void setViewGone(int... viewIds) {
        if (viewIds == null || getView() == null) {
            return;
        }
        for (int viewId : viewIds) {
            View view = getView().findViewById(viewId);
            if (view != null)
                view.setVisibility(View.GONE);
        }
    }

    protected void setViewInvisible(int... viewIds) {
        if (viewIds == null || getView() == null) {
            return;
        }
        for (int viewId : viewIds) {
            View view = getView().findViewById(viewId);
            if (view != null)
                view.setVisibility(View.INVISIBLE);
        }
    }

    protected void setEditTextMaxLength(int etId, int maxLength) {
        ((EditText) getView().findViewById(etId)).setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void hideKeyBoard(View view) {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null) {
            if (getActivity().getCurrentFocus() != null) {
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } else {
            in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showKeyBoard(View view) {
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    protected <VT extends View> VT fv(View parent, int id) {
        return (VT) parent.findViewById(id);
    }

    protected <VT extends View> VT fv(int id) {
        return (VT) getView().findViewById(id);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsFirstOnStart = true;
    }


    public int[] getInScreen(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        return location;
    }

    /**
     * 跳转到新的界面
     *
     * @param pagerClass
     */
    public void gotoPager(final Class<?> pagerClass) {
        gotoPager(pagerClass, null);
    }

    /**
     * 跳转到新的界面
     *
     * @param pagerClass
     * @param bundle
     */
    public void gotoPager(final Class<?> pagerClass, final Bundle bundle) {
        if (mIsToAnotherPage) {
            return;
        }
        if (getActivity() instanceof BaseActivity) {
            mIsToAnotherPage = true;
            ((BaseActivity) getActivity()).gotoPager(pagerClass, bundle);
        }
    }


    /**
     * 返回，如果stack中还有Fragment的话，则返回stack中的fragment，否则 finish当前的Activity
     */
    public void goBack() {
        hideKeyBoard(null);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getLayoutId() == 0) {
            ((Activity) mContext).finish();
            return null;
        }
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (view == null) {
            return;
        }
        super.onViewCreated(view, savedInstanceState);
        if (isNeedSetTopStyle()) {
            setTopStatusBarStyle(view);
        }
        onViewCreated(view);
    }

    public void setViewsOnClickListener(int... viewIds) {
        if (viewIds == null || getView() == null) {
            return;
        }
        for (int viewId : viewIds) {
            View view = getView().findViewById(viewId);
            if (view != null)
                view.setOnClickListener(this);
        }
    }

    /**
     * fragment的View创建好后调用
     */
    protected abstract void onViewCreated(View view);

    @Override
    public void onStop() {
        mIsFirstOnStart = true;
        super.onStop();

    }


    @Override
    public void onStart() {
        super.onStart();
        if (getView() == null) {
            return;
        }
        if (mIsFirstOnStart) {
            updateUIText();
            mIsFirstOnStart = false;
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        mIsToAnotherPage = false;
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected boolean isNeedSetTopStyle() {
        return true;
    }


    /**
     * 接受到更改语言设置后执行的方法
     */
    public abstract void updateUIText();


    public DisplayMetrics getDisplaymetrics() {
        if (mDisplaymetrics == null) {
            mDisplaymetrics = new DisplayMetrics();
            ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mDisplaymetrics);
        }
        return mDisplaymetrics;
    }

    public void onEditKeyListener(EditText et) {
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                        case KeyEvent.KEYCODE_DPAD_UP:
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    public void onReturnResult(int requestCode, int resultCode, Intent data) {
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    public void showToast(int textId) {
        Toast.makeText(getActivity(), getString(textId), Toast.LENGTH_LONG).show();
    }

}
