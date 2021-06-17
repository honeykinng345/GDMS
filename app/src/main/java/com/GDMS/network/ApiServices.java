package com.GDMS.network;



import com.GDMS.models.CheckLocation.LocationResponse;
import com.GDMS.models.GetDc.Dch;
import com.GDMS.models.GetDc.GetDcResponse;
import com.GDMS.models.ReasonList.ReasonListResponse;
import com.GDMS.models.Userlogin.Users;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {




    @POST("Account/Login")
    @FormUrlEncoded
    Call<Users> userLogin(@Field("USERID") String userId, @Field("PASSWORD") String password);

    @POST("List/CheckLocation")
    @FormUrlEncoded
    Call<LocationResponse> CheckLocation(@Field("WH_ID") String WH_ID);

    @POST("Trip/GetDC")
    @FormUrlEncoded
    Call<GetDcResponse> GetDC(@Field("USERID") String USERID);


    @GET("List/RReasonList")
    Call<ReasonListResponse> RReasonList();

    @POST("Trip/UpdateDC")
    @FormUrlEncoded
    Call<GetDcResponse> UpdateDC(@Field("DOCNUMBER") String DOCNUMBER,@Field("DOCSERIAL")
            int DOCSERIAL,@Field("STATUS") int STATUS,@Field("DELIVERYDATETIME") String DELIVERYDATETIME,@Field("DELIVERYLAT") String DELIVERYLAT,
                                 @Field("DELIVERYLONG") String DELIVERYLONG,@Field("DELIVERYLOCATION") String DELIVERYLOCATION,
                                 @Field("RREASONID") int RREASONID,@Field("REMARKS") String REMARKS);


}
