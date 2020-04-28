package com.arturdevmob.keepmoney.data.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppNetworkHelper implements NetworkHelper {
    public static final String BASE_URL = "https://currate.ru/api/";

    private Retrofit mRetrofit;
    private CourseApi mCourseApi;

    @Inject
    public AppNetworkHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mCourseApi = mRetrofit.create(CourseApi.class);
    }

    @Override
    public double getRatePairCurrencyFromNetwork(String firstCurrency, String secondCurrency) throws IOException {
        String pairCurrency = firstCurrency + secondCurrency;

        Response<ResponseBody> response = mCourseApi.getRatePairCurrency(pairCurrency).execute();

        try {
            JSONObject jsonResponse = new JSONObject(response.body().string());
            JSONObject jsonObjectRateCurrency = new JSONObject(jsonResponse.getString("data"));
            String rateCurrency = jsonObjectRateCurrency.getString(pairCurrency);

            return Double.parseDouble(rateCurrency);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        throw new IOException("Не удалось получить курс валют");
    }
}
