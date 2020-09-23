package com.example.admin.ccb.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.Text;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.MainActivity;
import com.example.admin.ccb.R;

import www.ccb.com.common.base.BaseActivity;

public class SplashActivity extends BaseActivity implements AMapLocationListener, WeatherSearch.OnWeatherSearchListener {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private TextView tvAddress , tvTime , tvTianQi ,tvWendu , tvFengli;
    @Override
    public int getContentViewResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

        imageView = findViewById(R.id.iv);
        recyclerView = findViewById(R.id.rv);
        tvAddress = findViewById(R.id.tvAddress);
        tvTime = findViewById(R.id.tvTime);
        tvTianQi = findViewById(R.id.tvTianQi);
        tvWendu = findViewById(R.id.tvWendu);
        tvFengli = findViewById(R.id.tvFengli);

    }

    @Override
    protected void initList() {


    }

   private BaseQuickAdapter WeatherAdapter = new BaseQuickAdapter<LocalDayWeatherForecast, BaseViewHolder>(R.layout.item_weather) {
        @Override
        protected void convert(BaseViewHolder helper, LocalDayWeatherForecast item) {
             helper.setText(R.id.tvTime , item.getDate()+"\t周"+item.getWeek())
                     .setText(R.id.tvTianQi , item.getDayWeather()
                             + "\t"+item.getNightTemp()+ "°\t/"+item.getDayTemp()+ "°");
        }
    };

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(WeatherAdapter);
        goLocation();
   imageView.postDelayed(new Runnable() {
       @Override
       public void run() {
           startActivity(new Intent(SplashActivity.this , MainActivity.class));
           finish();
       }
   }, 3500L);
    }

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private void goLocation() {
        //获取位置信息
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(SplashActivity.this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(-1000);
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();
    }



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            getWeather(aMapLocation.getCity());
        }
        mlocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      if (mlocationClient != null) mlocationClient.onDestroy();
    }


    private void getWeather(String city){
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        WeatherSearchQuery mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch mweathersearch=new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索

        WeatherSearchQuery mquery2 = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        WeatherSearch mweathersearch2=new WeatherSearch(this);
        mweathersearch2.setOnWeatherSearchListener(this);
        mweathersearch2.setQuery(mquery2);
        mweathersearch2.searchWeatherAsyn(); //异步搜索
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        if (i == 1000){
            tvAddress.setText(localWeatherLiveResult.getWeatherLiveQuery().getCity());
            tvTime.setText(localWeatherLiveResult.getLiveResult().getReportTime()+"发布");
            tvTianQi.setText(localWeatherLiveResult.getLiveResult().getWeather());
            tvWendu.setText(localWeatherLiveResult.getLiveResult().getTemperature()+"°");
            tvFengli.setText(localWeatherLiveResult.getLiveResult().getWindDirection()+"风\t\t"+localWeatherLiveResult.getLiveResult().getWindPower()
            +"\n湿度\t\t"+localWeatherLiveResult.getLiveResult().getHumidity()+"%");
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
        if (i == 1000){
            WeatherAdapter.addData(localWeatherForecastResult.getForecastResult().getWeatherForecast());
        }
    }
}
