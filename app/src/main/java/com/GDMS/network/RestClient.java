package com.GDMS.network;

import com.GDMS.Utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static RestClient restClient=new RestClient();
    private ApiServices apiServices;

    private RestClient(){
        Retrofit retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(Constants.BASE_URL).build();
        apiServices=retrofit.create(ApiServices.class);
    }

    public static RestClient getInstance(){
        return restClient;
    }
    public ApiServices getApiServices(){
        return apiServices;
    }
}
