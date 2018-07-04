package com.example.admin.ccb.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.example.admin.ccb.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MapActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener {

    @Override
    public int getContentViewResource() {
        return R.layout.activity_map;
    }

    private MapView mMapView;
    private AMap aMap = null;
    private TextView tvSwitchMode,tvSwitchState,tvLocation;
    @Override
    protected void initView() {
        UpTitle(null);
        mMapView =  findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        tvSwitchMode = findViewById(R.id.tvSwitchMode);
        tvSwitchState = findViewById(R.id.tvSwitchState);
        tvLocation = findViewById(R.id.tvLocation);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initList() {
        tvSwitchMode.setOnClickListener(this);
        tvSwitchState.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
    }

//        MAP_TYPE_NAVI 导航地图
//        MAP_TYPE_NIGHT 夜景地图
//        MAP_TYPE_NORMAL 白昼地图（即普通地图）
//        MAP_TYPE_SATELLITE 卫星图
//        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式，aMap是地图控制器对象。
//        aMap.setTrafficEnabled(true);//显示实时路况图层，aMap是地图控制器对象。

    private int mode = 1;
    private String modeList[] = {"普通地图","卫星地图","夜景地图","导航地图"};
    private boolean state = false;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSwitchMode:
                if (mode < 4){
                    ++mode;
                }else{
                    mode = 1;
                }
                aMap.setMapType(mode);
                tvSwitchMode.setText(modeList[mode-1]);
                break;
            case R.id.tvSwitchState:
                state = !state;
                aMap.setTrafficEnabled(state);
                break;
            case R.id.tvLocation:
                mlocationClient = new AMapLocationClient(this);
               //初始化定位参数
                mLocationOption = new AMapLocationClientOption();
               //设置定位监听
                mlocationClient.setLocationListener(MapActivity.this);
               //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                //设置定位间隔,单位毫秒,默认为2000ms
                mLocationOption.setInterval(2000);
                //设置定位参数
                mlocationClient.setLocationOption(mLocationOption);
                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                //启动定位
                mlocationClient.startLocation();
                break;

        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
            }
        }
    }
}
