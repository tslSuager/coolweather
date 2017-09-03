package com.example.x552m.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by x552m on 2017/7/27.
 */

public class City extends DataSupport {

    /**
     * id : 1
     * cityName : 成都
     * cityCode : 267
     * provinceId : 28
     */

    private int id;
    private int cityCode;
    private String cityName;
    private int provinceId;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
