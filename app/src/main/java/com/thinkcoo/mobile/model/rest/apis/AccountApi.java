package com.thinkcoo.mobile.model.rest.apis;

import com.thinkcoo.mobile.model.entity.serverresponse.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.CheckVcodeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.LoginResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.VcodeResponse;

import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface AccountApi {

    @POST("applogin.json")
    Observable<BaseResponse<LoginResponse>> login(@Query("phoneNumber") String phoneNumber, @Query("password") String passWord);
    @POST("appgetcode.json")
    Observable<BaseResponse<VcodeResponse>> loadVcode(@Query("phoneNumber") String phoneNumber, @Query("isValidate") String isValidate, @Query("moduleType") String moduleType);
    @POST("appinfovalidate.json")
    Observable<BaseResponse> register(@Query("phoneNumber") String phoneNumber, @Query("checkCode") String checkCode, @Query("cert") String moduleType, @Query("isValidate")String isValidate);
    @POST("appreg.json")
    Observable<BaseResponse> completeRegister(@Query("phoneNumber") String phoneNumber, @Query("password") String password, @Query("areaCode") String areaCode, @Query("areaCode") String realName);
    @POST("appinfovalidate.json")
    Observable<BaseResponse<CheckVcodeResponse>> forGetPassword(@Query("phoneNumber") String phoneNumber, @Query("checkCode") String checkCode, @Query("cert") String moduleType, @Query("isValidate")String isValidate);
    @POST("appresetpwd.json")
    Observable<BaseResponse> setupPassword(@Query("cert") String cert,@Query("userId") String userId, @Query("password") String newPassword);



}
