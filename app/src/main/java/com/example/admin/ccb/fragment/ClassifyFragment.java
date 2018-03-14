package com.example.admin.ccb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.base.BaseFragment;
import com.example.admin.ccb.utils.ResCcb;

/**
 * ccb simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends BaseFragment {

    private LeftAdapter leftAdapter;
    private ClassifyRightFragment cf;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
     private RecyclerView rv;
    private FrameLayout fm;
    @Override
    public void initView(View view) {
      rv = view.findViewById(R.id.rvLeft);
      fm = view.findViewById(R.id.fmRight);
      rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        leftAdapter = new LeftAdapter(R.layout.item_classify_left);
        rv.setAdapter(leftAdapter);
    }

    @Override
    public void loadData() {
        leftAdapter.setNewData(ResCcb.getClassifys());

        //创建Fragment对象
        cf = new ClassifyRightFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.fmRight, cf);
        //通过bundle传值给Fragment
        Bundle bundle = new Bundle();
        bundle.putString("Name",ResCcb.getClassifys().get(0));
        cf.setArguments(bundle);
        fragmentTransaction.commit();
    }

    @Override
    public void initListener() {

    }

    class LeftAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public LeftAdapter(int layoutResId) {
            super(layoutResId);
        }
        private int mPosition;
        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            TextView tv = helper.getView(R.id.tv_commclass);
           helper.setText(R.id.tv_commclass,item);
           if (mPosition == helper.getAdapterPosition()){
               tv.setTextColor(getResources().getColor(R.color.mainColor));
               tv.setBackgroundColor(getResources().getColor(R.color.colorWhite));
           }else{
               tv.setTextColor(getResources().getColor(R.color.defaultTextview));
               tv.setBackgroundColor(getResources().getColor(R.color.defaultBackground));
           }
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPosition != helper.getAdapterPosition()) {
                            mPosition = helper.getAdapterPosition();
                            notifyDataSetChanged();
                            Bundle bundle = new Bundle();
                            bundle.putString("Name", ResCcb.getClassifys().get(mPosition));
                            cf.setArguments(bundle);
                            onClickListeners.onClick();
                    }
                }
            });
        }
    }

    interface Listeners{
        void onClick();
    }
    private static Listeners onClickListeners;
    public static void setonListeners(Listeners ls){
        onClickListeners = ls;
    }
}
