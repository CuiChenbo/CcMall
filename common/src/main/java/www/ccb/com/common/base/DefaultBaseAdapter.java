package www.ccb.com.common.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 */

public abstract class DefaultBaseAdapter<T> extends BaseAdapter {

    public Context mContext;
    public List<T> dataList;

    public DefaultBaseAdapter(Context context, List<T> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ?  0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public void setDataList(List<T> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    public void setItmeData(int index,T dataList){
        this.dataList.set(index,dataList);
        notifyDataSetChanged();
    }

    public void addDataList(List<T> addList){
        if (addList != null && addList.size()>0){
            dataList.addAll(addList);
            notifyDataSetChanged();
        }
    }

    public void refreshDataList(List<T> newList){
        dataList.clear();
        dataList.addAll(newList);
        notifyDataSetChanged();
    }

    public void clearDataList(){
        if (dataList.size()!=0)
            dataList.clear();
        notifyDataSetChanged();
    }

}
