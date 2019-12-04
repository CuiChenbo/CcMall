package com.example.admin.ccb.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import www.ccb.com.common.utils.L;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
     //实现IWXAPIEventHandler接口，微信发送的请求将回调到onReq方法
	@Override
	public void onReq(BaseReq req) {
        L.i("BaseReq————————————",req.getType()+"");
	}

 //发送到微信请求的响应结果将回调到onResp方法
	@Override
	public void onResp(BaseResp resp) {

	}
}