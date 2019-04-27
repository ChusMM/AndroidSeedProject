package com.iecisa.androidseed.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MarvelApi {
    @GET("bins/bvyob")
    Observable<HeroListWrapper> getHeroes();
}
