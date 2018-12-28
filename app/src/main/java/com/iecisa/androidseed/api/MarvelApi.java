package com.iecisa.androidseed.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MarvelApi {
    @GET("/bins/bvyob")
    Call<HeroListWrapper> getHeroes();
}
