package com.example.x552m.coolweather.gson;

/**
 * Created by x552m on 2017/7/30.
 */

public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
