package com.example.x552m.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.x552m.coolweather.db.City;
import com.example.x552m.coolweather.db.County;
import com.example.x552m.coolweather.db.Province;
import com.example.x552m.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by x552m on 2017/7/27.
 */

public class Utility {
    public static boolean handProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonObject = new JSONArray(response);
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject j = jsonObject.getJSONObject(i);
                    Province p = new Province();
                    p.setProvinceCode(j.getInt("id"));
                    p.setProvinceName(j.getString("name"));
                    p.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean handCityResponse(String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonObject = new JSONArray(response);
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject j = jsonObject.getJSONObject(i);
                    City p = new City();
                    p.setCityCode(j.getInt("id"));
                    p.setCityName(j.getString("name"));
                    p.setProvinceId(provinceId);
                    p.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handCountyResponse(String response, int cityId) {
        try {
            JSONArray j = new JSONArray(response);
            for (int i = 0; i < j.length(); i++) {
                JSONObject jobject = j.getJSONObject(i);
                County c = new County();
                c.setCountyName(jobject.getString("name"));
                c.setCityId(cityId);
                c.setWeatherId(jobject.getString("weather_id"));
                c.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();

            return new Gson().fromJson(weatherContent, Weather.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
