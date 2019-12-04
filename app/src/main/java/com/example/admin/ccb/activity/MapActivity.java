package com.example.admin.ccb.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;

import www.ccb.com.common.base.BaseActivity;
import www.ccb.com.common.utils.L;
import www.ccb.com.common.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {

    private BaseQuickAdapter<PoiItem, BaseViewHolder> mAdapter;

    @Override
    public int getContentViewResource() {
        return R.layout.activity_map;
    }

    private MapView mMapView;
    private AMap aMap = null;
    private TextView tvLocation, tvLocationHeader;
    private RecyclerView rv;
    private View rvHeadView;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    protected void initView() {
        UpTitle(null);
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<PoiItem, BaseViewHolder>(R.layout.item_maplocation_msg) {
            @Override
            protected void convert(BaseViewHolder helper, PoiItem item) {
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvContent, item.getSnippet());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(item.getTitle());
                    }
                });
            }

        };
        rv.setAdapter(mAdapter);
        rvHeadView = View.inflate(mContext, R.layout.item_maplocation_head, null);
        tvLocationHeader = rvHeadView.findViewById(R.id.tvTitle);
        mAdapter.setHeaderView(rvHeadView);
    }

    @Override
    protected void initData() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //设置定位蓝点
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        // 控件交互 缩放按钮、指南针、定位按钮、比例尺等
        UiSettings mUiSettings;//定义一个UiSettings对象
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        mUiSettings.setScaleControlsEnabled(true);//控制比例尺控件是否显示
        mUiSettings.setLogoPosition(AMapOptions.LOGO_MARGIN_LEFT);//设置logo位置

        goLocation();
        //地图移动监听
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                L.cc(cameraPosition.toString());
                setMarker(cameraPosition.target);
                getGeocodeSearch(cameraPosition.target);
            }
        });
    }

    private void goLocation() {
        //获取位置信息
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(MapActivity.this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(-1000);
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    protected void initList() {
        tvLocation = findViewById(R.id.tvLocation);
        tvLocation.setOnClickListener(this);
    }

    private Marker centerMarker;

    private void setMarker(LatLng target) {
        if (centerMarker != null) centerMarker.remove();
        centerMarker = aMap.addMarker(new MarkerOptions().position(target).title("").snippet(""));
        Animation animation = new RotateAnimation(centerMarker.getRotateAngle() - 180, centerMarker.getRotateAngle(), 0, 0, 0);
//        Animation animation = new TranslateAnimation(target);
        animation.setDuration(360L);
        animation.setInterpolator(new LinearInterpolator());
        centerMarker.setAnimation(animation);
        centerMarker.startAnimation();
    }

    private GeocodeSearch geocoderSearch;

    private void getGeocodeSearch(LatLng targe) {
        if (geocoderSearch == null) geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(targe.latitude, targe.longitude), 1000, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i != 1000) return;
        mAdapter.setNewData(regeocodeResult.getRegeocodeAddress().getPois());
        tvLocationHeader.setText(regeocodeResult.getRegeocodeAddress().getProvince()
                + (TextUtils.equals(regeocodeResult.getRegeocodeAddress().getCity(), regeocodeResult.getRegeocodeAddress().getProvince()) ? "" : regeocodeResult.getRegeocodeAddress().getCity())
                + regeocodeResult.getRegeocodeAddress().getDistrict());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvLocation:

                break;

        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
            amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
            amapLocation.getLatitude();//获取纬度
            amapLocation.getLongitude();//获取经度
            amapLocation.getAccuracy();//获取精度信息
            amapLocation.getAddress();//地址
            setMapCenter(amapLocation);
        } else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            L.cc("AmapError", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
        }
        mlocationClient.stopLocation();
        mlocationClient.onDestroy();
    }

    private void setMapCenter(AMapLocation amapLocation) {
        L.cc(amapLocation.toStr());
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())
                        , 15, 0, 0)), 300, null); //设置地图中心点
        tvLocation.setText("我的位置：" + amapLocation.getAddress());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

}
