package com.iecisa.androidseed.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeroListWrapper {
    @SerializedName("superheroes")
    public List<HeroWrapper> superheroes;

    public HeroListWrapper() { }

    public HeroListWrapper(List<HeroWrapper> superheroes) {
        this.superheroes = superheroes;
    }

    public static HeroListWrapper fromJson(String json) {
        return new Gson().fromJson(json, HeroListWrapper.class);
    }
}
