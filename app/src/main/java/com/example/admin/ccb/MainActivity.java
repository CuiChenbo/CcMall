package com.example.admin.ccb;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.admin.ccb.base.BaseActivity;
import com.example.admin.ccb.fragment.HomeFragment;
import com.example.admin.ccb.fragment.MyFragment;
import com.example.admin.ccb.fragment.ClassifyFragment;
import com.example.admin.ccb.fragment.SpcFragment;

public class MainActivity extends BaseActivity {

    private FrameLayout fl;
    private RadioGroup rg;
    private Fragment HomeFm = null,SpcFm = null, OrderFm = null, MyFm = null;

    private final class RadioGroupOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (HomeFm != null) {
                getSupportFragmentManager().beginTransaction().hide(HomeFm).commit();
            }
            if (SpcFm != null) {
                getSupportFragmentManager().beginTransaction().hide(SpcFm).commit();
            }
            if (OrderFm != null) {
                getSupportFragmentManager().beginTransaction().hide(OrderFm).commit();
            }
            if (MyFm != null) {
                getSupportFragmentManager().beginTransaction().hide(MyFm).commit();
            }
            switch (checkedId) {
                case R.id.rb_home:
                    if (HomeFm == null) {
                        HomeFm = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fl, HomeFm).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(HomeFm).commit();
                    }
                    break;
                case R.id.rb_shoppingcart:
                    if (SpcFm == null) {
                        SpcFm = new SpcFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fl, SpcFm).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(SpcFm).commit();
                    }
                    break;

                case R.id.rb_orderfrom:
                    if (OrderFm == null) {
                        OrderFm = new ClassifyFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fl, OrderFm).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(OrderFm).commit();
                    }
                    break;
                case R.id.rb_my:
                    if (MyFm == null) {
                        MyFm = new MyFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.fl, MyFm).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().show(MyFm).commit();
                    }
                    break;
            }
            /**
             * 替换fragment
             */
//            switch (checkedId) {
//                case R.id.rb_home:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, fmHome).commit();
//                    break;
//                case R.id.rb_shoppingcart:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, fmShoppingCart).commit();
//                    break;
//
//                case R.id.rb_orderfrom:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, fmOrderfrom).commit();
//                    break;
//                case R.id.rb_my:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, fmMy).commit();
//                    break;
//            }
        }
    }

    @Override
    public int getContentViewResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
     fl = findViewById(R.id.fl);
     rg = findViewById(R.id.rg);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initList() {
        rg.setOnCheckedChangeListener(new RadioGroupOnCheckedChangeListener());
        rg.check(R.id.rb_home);
    }

}
