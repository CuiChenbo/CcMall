package com.example.admin.ccb.activity;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.admin.ccb.R;

public class SetActivity extends BaseActivity {

    @Override
    public int getContentViewResource() {
        return R.layout.activity_set;
    }
   private TextView tv,et;
    @Override
    protected void initView() {
     tv = findViewById(R.id.tv);
     et = findViewById(R.id.et);
     tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
      et.setText("http://mp.blog.csdn.net/postedit/79460617");
    }

    @Override
    protected void initList() {

    }

    public void click(View view) {
        okGetRequest(et.getText().toString().trim());
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        tv.setText(t.toString());
    }
}
