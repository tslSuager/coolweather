package com.example.x552m.coolweather.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x552m.coolweather.MainActivity;
import com.example.x552m.coolweather.R;
import com.example.x552m.coolweather.WeatherActivity;
import com.example.x552m.coolweather.db.City;
import com.example.x552m.coolweather.db.County;
import com.example.x552m.coolweather.db.Province;
import com.example.x552m.coolweather.util.HttpUtil;
import com.example.x552m.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.R.attr.id;
import static android.R.attr.onClick;
import static android.R.attr.packageNames;
import static android.R.attr.tunerCount;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.x552m.coolweather.R.id.back_button;

/**
 * Created by x552m on 2017/7/27.
 */

public class ChooseAreaFragmenr extends Fragment {

    public static final int LEVEl_PRONVINCE = 0;
    public static final int LEVEl_CITY = 1;
    public static final int LEVEl_COUNTY = 2;

    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;
    /**
     * 选中的省
     */
    private Province selectProvince;
    /**
     * 选中的市
     */
    private City selectCity;
    /**
     * 选中的县
     */
    private County selectCounty;
    /**
     * 当前级别
     */
    private int currentLevel;

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(back_button)
    Button backButton;
    @BindView(R.id.list_view)
    ListView listView;


    private ProgressDialog progressDialog;
    private ArrayAdapter<String> adapter;
    private List<String> datalist = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        ButterKnife.bind(this, view);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, datalist);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEl_PRONVINCE) {
                    selectProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEl_CITY) {
                    selectCity = cityList.get(position);
                    queryCounty();
                } else if (currentLevel == LEVEl_COUNTY) {
                    String weatherId = countyList.get(position).getwWeatherId();
                    if (getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity() instanceof WeatherActivity) {
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefresh.setRefreshing(true);
                        activity.requestWeather(weatherId);
                    }

                }
            }
        });

        queryProvince();
    }

    @OnClick({back_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case back_button:
                if (currentLevel == LEVEl_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEl_CITY) {
                    queryProvince();
                }
                break;
        }
    }

    private void queryProvince() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            datalist.clear();
            for (int i = 0; i < provinceList.size(); i++) {
                datalist.add(provinceList.get(i).getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEl_PRONVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address,"province");
        }

    }



    private void queryCities() {
        titleText.setText(selectProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceId = ?", String.valueOf(selectProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            datalist.clear();
            for (int i = 0; i < cityList.size(); i++) {
                datalist.add(cityList.get(i).getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEl_CITY;
        } else {
            int provinceCode = selectProvince.getProvinceCode();
            String adress = "http://guolin.tech/api/china/"+provinceCode;
            queryFromServer(adress,"city");
        }
    }


    private void queryCounty() {
        titleText.setText(selectCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityId = ?", String.valueOf(selectCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            datalist.clear();
            for (int i = 0; i < countyList.size(); i++) {
                datalist.add(countyList.get(i).getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEl_COUNTY;
        } else {
            int provinceCode = selectProvince.getProvinceCode();
            int cityCode = selectCity.getCityCode();
            String adress = "http://guolin.tech/api/china/" + provinceCode + "/"+cityCode;
            queryFromServer(adress,"county");
        }

    }

    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handCityResponse(responseText, selectProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handCountyResponse(responseText, selectCity.getId());
                }
                if (result)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        if ("province".equals(type)) {
                            queryProvince();
                        } else if ("city".equals(type)) {
                            queryCities();
                        } else if ("county".equals(type)) {
                            queryCounty();
                        }
                    }
                });
            }
        });

    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
