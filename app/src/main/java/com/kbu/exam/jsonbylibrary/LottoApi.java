package com.kbu.exam.jsonbylibrary;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LottoApi {
    @GET("?method=getLottoNumber")
    Call<LottoDTO> getLotto(@Query("drwNo") String drwNo);

}
