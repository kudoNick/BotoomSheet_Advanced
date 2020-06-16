package com.example.androidtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class DataAdapter extends BaseAdapter {

    Context context;
    List<Data> dataList;

    public DataAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DataViewHolder dataViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.data, parent, false);
            dataViewHolder = new DataViewHolder(convertView);
            convertView.setTag(dataViewHolder);
        } else {
            dataViewHolder = (DataViewHolder) convertView.getTag();
        }

        Data data = (Data) getItem(position);

        dataViewHolder.tvName.setText(data.getName());
        return convertView;
    }

    public class DataViewHolder{
        TextView tvName;
        public DataViewHolder(View convertView) {
            tvName = convertView.findViewById(R.id.tvName);
        }
    }
}
