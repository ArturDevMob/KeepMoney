package com.arturdevmob.keepmoney.data.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CourseApi {
    @GET("?get=rates&key=c248b2d0316317f39caf4aef1b6e769f")
    Call<ResponseBody> getRatePairCurrency(@Query("pairs") String pairCurrency);
}
