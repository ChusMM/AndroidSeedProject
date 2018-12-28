package com.iecisa.androidseed.api;

import android.os.Build;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class HeroWrapper {
    private static final String EMPTY_NAME = "no_name";

    @SerializedName("name")
    public String name = EMPTY_NAME;

    @SerializedName("photo")
    public String photo;

    @SerializedName("realName")
    public String realName;

    @SerializedName("height")
    public String height;

    @SerializedName("power")
    public String power;

    @SerializedName("abilities")
    public String abilities;

    @SerializedName("groups")
    public String groups;

    public HeroWrapper() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroWrapper that = (HeroWrapper) o;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(name, that.name);
        } else {
            return name.equals(that.name);
        }
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(name);
        } else {
            return name.hashCode();
        }
    }
}
