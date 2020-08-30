package com.ubit.wallet.http;

import com.ubit.wallet.bean.BasicResponse;
import com.ubit.wallet.bean.PhoneCodeBean;
import com.ubit.wallet.bean.PicCodeResultBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpService {

    /**
     * 获取图片验证码
     *
     * @return
     */
    @GET("api/v1/captcha")
    Observable<BasicResponse<PicCodeResultBean>> captcha();

    /**
     * 注册1
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/register1")
    Observable<BasicResponse<HashMap<String, String>>> register1(@Field("type") String type,
                                                                 @Field("account") String account,
                                                                 @Field("captcha") String captcha,
                                                                 @Field("sid") String sid,
                                                                 @Field("phone_area") String phone_area);

    /**
     * 注册2
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/register2")
    Observable<BasicResponse> register2(@Field("uid") String uid,
                                                           @Field("pwd") String pwd,
                                                           @Field("pwd2") String pwd2,
                                                           @Field("invit_code") String invit_code,
                                                           @Field("code") String code,
                                                           @Field("sid") String sid);

    /**
     * 获取邮箱验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/get_email_code")
    Observable<BasicResponse<HashMap<String, String>>> get_email_code(@Field("email") String email);

    /**
     * 获取短信验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/get_mobile_code")
    Observable<BasicResponse> get_mobile_code(@Field("phone") String phone, @Field("phone_code") String phone_code);

    /**
     * 忘记密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/find_pwd")
    Observable<BasicResponse> find_pwd(@Field("account") String account,
                                                          @Field("type") String type,
                                                          @Field("phone_code") String phone_code,
                                                          @Field("code") String code,
                                                          @Field("sid") String sid,
                                                          @Field("pwd") String pwd,
                                                          @Field("pwd2") String pwd2
    );

    /**
     * 获取手机区号列表
     *
     * @return
     */
    @GET("api/v1/get_codes")
    Observable<BasicResponse<PhoneCodeBean>> get_codes(@Query("lan") String lan);

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/login")
    Observable<BasicResponse> login(@Field("name") String name,
                                                       @Field("password") String password,
                                                       @Field("code") String code,
                                                       @Field("sid") String sid,
                                                       @Field("phone_code") String phone_code);
}
