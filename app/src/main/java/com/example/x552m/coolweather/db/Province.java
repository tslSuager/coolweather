package com.example.x552m.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by x552m on 2017/7/27.
 */

public class Province extends DataSupport {

    /**
     * id : 1
     * provinceName : 北京
     * provinceCode : 1
     */

    private int id;
    private int provinceCode;
    private String provinceName;

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
