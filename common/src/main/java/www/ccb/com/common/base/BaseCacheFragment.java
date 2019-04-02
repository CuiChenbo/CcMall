package www.ccb.com.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自带缓存View的Fragment基类，适用于ViewPager+Fragment
 */
public abstract class BaseCacheFragment extends BaseFragment {
    private boolean isAttached;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        mContext = getActivity();
        if (rootView == null)rootView = initContentView(inflater, container, savedInstanceState);
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        if (!isAttached) {
            initView(rootView);
            loadData();
            initListener();
            isAttached = true;
        }
        return rootView;
    }

    public void onRefresh(){

    }
}
