package com.example.x552m.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by x552m on 2017/7/30.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
