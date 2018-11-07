package com.example.admin.ccb.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.ccb.R;
import com.example.admin.ccb.utils.ResCcb;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import www.ccb.com.common.utils.ToastUtils;

public class SearchActivity extends BaseActivity {
    @Override
    public int getContentViewResource() {
        return R.layout.activity_sraech;
    }
    private TagFlowLayout tagFlowLayout;
    private EditText et;
    @Override
    protected void initView() {
        mImmersionBar.titleBarMarginTop(R.id.home_searchs).statusBarDarkFont(true,0.2f).init();
        et = findViewById(R.id.search_content);
        findViewById(R.id.searchBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tagFlowLayout = (TagFlowLayout) findViewById(R.id.flowlayout);
    }

    private TagAdapter<String> tagAdapter;
    @Override
    protected void initData() {
        //TagFlowLayout流布局的适配器
        tagAdapter = new TagAdapter<String>(ResCcb.searchLists) {
            @Override
            public View getView(FlowLayout parent, int position, String data) {
                TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_flow_tv, tagFlowLayout, false);
                tv.setText(data);
                return tv;
            }
        };
        tagFlowLayout.setAdapter(tagAdapter);
    }

    @Override
    protected void initList() {
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ToastUtils.showToast(mContext,"点击了条目"+position+":"+ResCcb.searchLists[position]);
                et.setText(ResCcb.searchLists[position]);
                return true;
            }
        });
    }
}


