package com.example.admin.ccb.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import www.ccb.com.common.utils.NumberFormatUtil;
import www.ccb.com.common.utils.UiUtils;

/**
 * 闪屏页 + 天气页
 * isForecast ： false 闪屏页
 * isForecast ： true 天气页
 */
public class SplashActivity extends BaseActivity implements AMapLocationListener, WeatherSearch.OnWeatherSearchListener {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private TextView tvCountTime, tvAddress, tvTime, tvTianQi, tvWendu, tvFengli , tvTre;
    private LinearLayout layoutForecast, layouTrea , rootView;
    private CardView cv;
    private boolean isWeather; //是否显示预报
    public static final String WEATHER = "WEATHER";
    private CountDownTimer countDownTimer;

    @Override
    public int getContentViewResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

        rootView = findViewById(R.id.rootView);
        imageView = findViewById(R.id.iv);
        recyclerView = findViewById(R.id.rv);
        tvAddress = findViewById(R.id.tvAddress);
        tvTime = findViewById(R.id.tvTime);
        tvTianQi = findViewById(R.id.tvTianQi);
        tvWendu = findViewById(R.id.tvWendu);
        tvFengli = findViewById(R.id.tvFengli);
        tvTre = findViewById(R.id.tvTre);
        tvCountTime = findViewById(R.id.tvCountTime);
        layoutForecast = findViewById(R.id.layoutForecast);
        layouTrea = findViewById(R.id.layouTrea);
        cv = findViewById(R.id.cv);

    }

    @Override
    protected void initList() {


    }

    @Override
    protected void initData() {
        isWeather = getIntent().getBooleanExtra(WEATHER, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(WeatherAdapter);
        setViewColor();
        goLocation();
        startSplash();
    }

    private void setViewColor() {
        if (isWeather){
            imageView.setVisibility(View.GONE);
            rootView.setBackgroundResource(R.color.ali_color);
            tvAddress.setPadding(0,0,0, UiUtils.dp2px(80));
            tvAddress.setTextSize(36);
            tvAddress.setTextColor(getResources().getColor(R.color.white));
            tvTre.setTextColor(getResources().getColor(R.color.white));
            tvTime.setTextColor(getResources().getColor(R.color.white));
            tvTianQi.setTextColor(getResources().getColor(R.color.white));
            tvWendu.setTextColor(getResources().getColor(R.color.white));
            tvFengli.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void startSplash() {
        if (isWeather) return;

        countDownTimer = new CountDownTimer(6 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountTime.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        countDownTimer.start();

        cv.setVisibility(View.VISIBLE);
        tvCountTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                countDownTimer.cancel();
                countDownTimer = null;
            }
        });
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
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


    private void getWeather(String city) {
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        WeatherSearchQuery mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch mweathersearch = new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索

        if (isWeather) {
            WeatherSearchQuery mquery2 = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
            WeatherSearch mweathersearch2 = new WeatherSearch(this);
            mweathersearch2.setOnWeatherSearchListener(this);
            mweathersearch2.setQuery(mquery2);
            mweathersearch2.searchWeatherAsyn(); //异步搜索
        }
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        if (i == 1000) {
            tvAddress.setText(localWeatherLiveResult.getWeatherLiveQuery().getCity());
            tvTime.setText(localWeatherLiveResult.getLiveResult().getReportTime() + "\t发布");
            tvTianQi.setText(localWeatherLiveResult.getLiveResult().getWeather());
            tvWendu.setText(localWeatherLiveResult.getLiveResult().getTemperature() + "°");
            tvFengli.setText(localWeatherLiveResult.getLiveResult().getWindDirection() + "风\t\t" + localWeatherLiveResult.getLiveResult().getWindPower()
                    + "\n湿度\t\t" + localWeatherLiveResult.getLiveResult().getHumidity() + "%");

            layouTrea.setVisibility(View.VISIBLE);
            LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.layout_up_anim));
            layouTrea.setLayoutAnimation(controller);
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
        if (i == 1000) {
            WeatherAdapter.addData(localWeatherForecastResult.getForecastResult().getWeatherForecast());
            layoutForecast.setVisibility(View.VISIBLE);
            LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.layout_up_anim));
            recyclerView.setLayoutAnimation(controller);
        }
    }

    private BaseQuickAdapter WeatherAdapter = new BaseQuickAdapter<LocalDayWeatherForecast, BaseViewHolder>(R.layout.item_weather) {
        @Override
        protected void convert(BaseViewHolder helper, LocalDayWeatherForecast item) {
            String weather;
            if (TextUtils.equals(item.getDayWeather() , item.getNightWeather())){
                weather = item.getDayWeather();
            }else {
                weather = item.getDayWeather() +"转"+item.getNightWeather();
            }
            helper.setText(R.id.tvTime, item.getDate() + "\t周" + NumberFormatUtil.formatInteger(Integer.parseInt(item.getWeek())))
                    .setText(R.id.tvTianQi, weather
                            + "\t\t" + item.getNightTemp() + "°\t/\t" + item.getDayTemp() + "°");
        }
    };
}
