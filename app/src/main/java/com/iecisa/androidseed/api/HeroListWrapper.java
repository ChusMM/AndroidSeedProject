package com.iecisa.androidseed.api;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HeroListWrapper {
    private static final String TAG = HeroListWrapper.class.getSimpleName();

    @SerializedName("superheroes")
    public List<HeroWrapper> superheroes;

    public HeroListWrapper() {
        this.superheroes = new ArrayList<>();
    }

    public HeroListWrapper(@NonNull List<HeroWrapper> superheroes) {
        this.superheroes = superheroes;
    }

    public static HeroListWrapper fromJson(String json) {
        try {
            return new Gson().fromJson(json, HeroListWrapper.class);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.toString());
            return new HeroListWrapper();
        }
    }
}
