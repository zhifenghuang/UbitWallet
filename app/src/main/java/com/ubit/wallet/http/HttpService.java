package com.ubit.wallet.http;

import com.ubit.wallet.bean.AssetsBean;
import com.ubit.wallet.bean.BasicResponse;
import com.ubit.wallet.bean.PhoneCodeBean;
import com.ubit.wallet.bean.PicCodeResultBean;
import com.ubit.wallet.bean.UserInfoBean;

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
    Observable<BasicResponse<HashMap<String, String>>> register2(@Field("uid") String uid,
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
    Observable<BasicResponse<HashMap<String, String>>> login(@Field("name") String name,
                                                             @Field("password") String password,
                                                             @Field("code") String code,
                                                             @Field("sid") String sid,
                                                             @Field("phone_code") String phone_code);

    /**
     * 资产页面
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/assets/v1/info")
    Observable<BasicResponse<AssetsBean>> get_assets(@Field("token") String token);

    /**
     * 获取币种手续费
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/assets/v1/get_fee")
    Observable<BasicResponse<HashMap<String, String>>> get_fee(@Field("token") String token);

    /**
     * 获取用户个人信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/assets/v1/user")
    Observable<BasicResponse<UserInfoBean>> get_user(@Field("token") String token);

    /**
     * 转账
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/assets/v1/transfer")
    Observable<BasicResponse> transfer(@Field("token") String token,
                                       @Field("amount") String amount,
                                       @Field("to_address") String to_address,
                                       @Field("from_address") String from_address,
                                       @Field("symbol") String symbol,
                                       @Field("fee") String fee);

    /**
     * 绑定或更换手机号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/v1/bang_phone")
    Observable<BasicResponse> bind_phone(@Field("token") String token,
                                         @Field("phone") String phone,
                                         @Field("phone_code") String phone_code,
                                         @Field("code") String code);

    /**
     * 更换或绑定邮箱
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/v1/bang_email")
    Observable<BasicResponse> bind_email(@Field("token") String token,
                                         @Field("email") String email,
                                         @Field("sid") String sid,
                                         @Field("code") String code);

    /**
     * 修改登录密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/v1/save_pwd")
    Observable<BasicResponse> modify_login_password(@Field("token") String token,
                                                    @Field("old_pwd") String old_pwd,
                                                    @Field("new_pwd") String new_pwd,
                                                    @Field("new_pwd2") String new_pwd2);

    /**
     * 设置支付密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/v1/save_pay")
    Observable<BasicResponse> modify_pay_password(@Field("token") String token,
                                                  @Field("pay_pwd") String pay_pwd,
                                                  @Field("pay_pwd2") String pay_pwd2);


    /**
     * 校验支付密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/v1/check_pay")
    Observable<BasicResponse> check_pay_password(@Field("token") String token,
                                                  @Field("pwd") String pwd);

}
