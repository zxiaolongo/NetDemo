package com.llv.chapiion.netdemo.net;

import android.database.Observable;

import com.llv.chapiion.netdemo.net.bean.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
public interface ResponseApi {
    /**
     * 验证码登录
     */
    @FormUrlEncoded
    @POST(UrlConstants.Login.LOGIN_IN)
    Call<ServerResponse> appLoginCode(@Field("vcMobile") String vcMobile,//手机号
                                      @Field("CAPTCHA") String CAPTCHA,//验证码
                                      @Field("vcDeviceType") String vcDeviceType//手机类型 Android /iphone

    );

    /**
     * 退出登陆
     *
     * @param tokenId
     */
    @POST(UrlConstants.Login.LOGIN_OUT)
    Call<ServerResponse> appLogout(@Header("tokenId") String tokenId);

    /**
     * 退出登陆
     *
     * @param tokenId
     */
    @POST(UrlConstants.Login.LOGIN_OUT)
    io.reactivex.Observable<ServerResponse> appLogoutB(@Header("tokenId") String tokenId);

}
