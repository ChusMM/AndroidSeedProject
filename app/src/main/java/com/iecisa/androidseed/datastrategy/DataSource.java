package com.iecisa.androidseed.datastrategy;

import android.support.annotation.NonNull;

import com.iecisa.androidseed.BuildConfig;

public enum DataSource {
    DATA_WS("ws"),
    DATA_DB("db"),
    DATA_MOCK("mock");

    public static final String WS_DATA_ORIGIN = "WS";
    public static final String DB_DATA_ORIGIN = "DB";
    public static final String MOCK_DATA_ORIGIN = "MOCK";

    private final String dataSource;

    DataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public String toString() {
        return dataSource;
    }

    public static DataSource getDefaulDataSource() {
        return buildFromString(BuildConfig.DATA_ORIGIN);
    }

    public static DataSource buildFromString(String source) {
        switch (source) {
            case WS_DATA_ORIGIN:
                return DATA_WS;
            case DB_DATA_ORIGIN:
                return DATA_DB;
            case MOCK_DATA_ORIGIN:
                return DATA_MOCK;
            default:
                return DATA_WS;
        }
    }
}
