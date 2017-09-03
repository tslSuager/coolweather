package com.example.x552m.coolweather;

import android.support.v4.app.Fragment;

/**
 * Created by x552m on 2017/7/7.
 */

public abstract class BaseFrament extends Fragment {
    boolean isVisible = false;
    boolean isFirst = true;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }

    protected abstract void lazyLoad();
}
