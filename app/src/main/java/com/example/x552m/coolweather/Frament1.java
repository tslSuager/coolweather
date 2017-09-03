package com.example.x552m.coolweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by x552m on 2017/7/8.
 */

public class Frament1 extends BaseFrament {

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            lazyLoad();
        }
        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible&&!isFirst) {
            return;
        }
        isFirst = false;
        //加载数据


    }
}
