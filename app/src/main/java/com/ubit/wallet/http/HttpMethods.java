package com.ubit.wallet.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ubit.wallet.BaseApp;
import com.ubit.wallet.utils.NetUtil;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

/**
 * Created by gigabud on 17-5-3.
 */

public class HttpMethods {

    private static final String TAG = "HttpMethods";
    private Retrofit mRetrofit;
    private static final int DEFAULT_TIMEOUT = 20;
    private static HttpMethods INSTANCE;
    private Context mContext;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //    if (SPConstants.DEBUG_MODE) {
                Log.i(TAG, message);
                //     }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .build();
    }

    public static HttpMethods getInstance() {
        if (INSTANCE == null) {
            synchronized (TAG) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpMethods();
                }
            }
        }
        return INSTANCE;
    }

    public static void clearHotService() {
        INSTANCE = null;
        getInstance();
    }

    public void setContent(Context content) {
        this.mContext = content;
    }

    private String getBaseUrl() {
        return "http://47.75.56.236/";
    }

    /**
     * @param observer
     */
    public void captcha(HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.captcha();
        toSubscribe(observable, observer);
    }

    /**
     * @param lan      语言 1英语 2中文 3日语  4 韩语 5越南语
     * @param observer
     */
    public void get_codes(String lan, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.get_codes(lan);
        toSubscribe(observable, observer);
    }


    /**
     * @param email
     * @param observer
     */
    public void get_email_code(String email, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.get_email_code(email);
        toSubscribe(observable, observer);
    }

    /**
     * @param phone
     * @param phoneCode
     * @param observer
     */
    public void get_mobile_code(String phone, String phoneCode, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.get_mobile_code(phone, phoneCode);
        toSubscribe(observable, observer);
    }

    /**
     * @param type       注册类型1手机注册  2邮箱注册
     * @param account    手机号或邮箱
     * @param captcha    手机或邮箱验证码
     * @param sid        获取邮箱验证码接口返回的sid（手机注册不需要）
     * @param phone_area 手机区号
     * @param observer
     */
    public void register1(String type, String account, String captcha, String sid, String phone_area, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.register1(type, account, captcha, sid, phone_area);
        toSubscribe(observable, observer);
    }

    /**
     * @param uid        用户id（注册1接口返回）
     * @param pwd        密码（md5加密）
     * @param pwd2       确认密码（md5加密）
     * @param invit_code 邀请码
     * @param code       图片验证码
     * @param sid        图片验证码返回的sid
     * @param observer
     */
    public void register2(String uid, String pwd, String pwd2, String invit_code, String code, String sid, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.register2(uid, pwd, pwd2, invit_code, code, sid);
        toSubscribe(observable, observer);
    }

    /**
     * @param account    手机号或邮箱
     * @param type       类型 1手机  2邮箱
     * @param phone_code type=1时才有，区号
     * @param code       手机或邮箱验证码
     * @param sid        邮箱找回时才有，邮箱验证码接口返回的
     * @param pwd        新密码
     * @param pwd2       确认新密码
     * @param observer
     */
    public void find_pwd(String account, String type, String phone_code, String code, String sid,
                         String pwd, String pwd2,
                         HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.find_pwd(account, type, phone_code, code, sid, pwd, pwd2);
        toSubscribe(observable, observer);
    }

    /**
     * @param name       手机号或邮箱
     * @param pwd        密码（md5加密）
     * @param code       图片验证码
     * @param sid        图片验证码返回的sid
     * @param phone_code 手机区号（手机号登录才有）
     * @param observer
     */
    public void login(String name, String pwd, String code, String sid, String phone_code, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.login(name, pwd, code, sid, phone_code);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param observer
     */
    public void get_assets(String token, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.get_assets(token);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param observer
     */
    public void get_fee(String token, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.get_fee(token);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param observer
     */
    public void get_user(String token, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.get_user(token);
        toSubscribe(observable, observer);
    }


    /**
     * @param token
     * @param amount
     * @param to_address
     * @param from_address
     * @param symbol       币种类型（1.tth 2.eth 3.erc20 4.omni 5.btc）
     * @param fee
     * @param observer
     */
    public void transfer(String token, String amount, String to_address, String from_address,
                         String symbol, String fee, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.transfer(token, amount, to_address, from_address, symbol, fee);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param phone
     * @param phone_code
     * @param code
     * @param observer
     */
    public void bind_phone(String token, String phone, String phone_code, String code, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.bind_phone(token, phone, phone_code, code);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param email
     * @param sid
     * @param code
     * @param observer
     */
    public void bind_email(String token, String email, String sid, String code, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.bind_email(token, email, sid, code);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param oldPsw
     * @param newPaw
     * @param newPaw2
     * @param observer
     */
    public void modify_login_password(String token, String oldPsw, String newPaw, String newPaw2, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.modify_login_password(token, oldPsw, newPaw, newPaw2);
        toSubscribe(observable, observer);
    }

    /**
     * @param token
     * @param newPaw
     * @param newPaw2
     * @param observer
     */
    public void modify_pay_password(String token, String newPaw, String newPaw2, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.modify_pay_password(token, newPaw, newPaw2);
        toSubscribe(observable, observer);
    }

    /**
     *
     * @param token
     * @param psw
     * @param observer
     */
    public void check_pay_password(String token, String psw, HttpObserver observer) {
        HttpService httpService = mRetrofit.create(HttpService.class);
        Observable observable = httpService.check_pay_password(token, psw);
        toSubscribe(observable, observer);
    }

    private <T> void toSubscribe(Observable<T> o, HttpObserver s) {
        o.retry(2, new Predicate<Throwable>() {
            @Override
            public boolean test(@NonNull Throwable throwable) throws Exception {
                return NetUtil.isConnected(BaseApp.getContext()) &&
                        (throwable instanceof SocketTimeoutException ||
                                throwable instanceof ConnectException ||
                                throwable instanceof ConnectTimeoutException ||
                                throwable instanceof TimeoutException);
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
