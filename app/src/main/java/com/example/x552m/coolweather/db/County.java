package com.example.x552m.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by x552m on 2017/7/27.
 */

public class County extends DataSupport {
    /**
     * id : 1
     * countyName : 麻柳
     * countyCode : 454
     * cityId : 234
     */
    private int id;
    private String countyName;
    private String weatherId;
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getwWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
