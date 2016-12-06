package com.example.ygc.test.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ygc.test.R;
import com.example.ygc.test.view.MyApp;

public class MyAdapter extends BaseAdapter {
    private String[] datas = new String[]{};

    public MyAdapter(String[] data){
        this.datas = data;
    }
    public MyAdapter(){}
    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//            View view = null;
//            if (convertView != null) {
//                view = convertView;
//            } else {
//                view = View.inflate(MainActivity.this, R.layout.lv_item, null);
//            }
//            TextView tv = (TextView) view.findViewById(numTv);
//            tv.setText(datas[position]);
//            return view;

        View view = null;
        ViewHolder viewHolder ;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(MyApp.sContext,R.layout.lv_item, null);
//            view = LayoutInflater.from(MyApp.sContext).inflate(R.layout.lv_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.numTv.setText(datas[position]);
        return view;

    }

    class ViewHolder {
        private TextView numTv;
        public ViewHolder(View view){
            numTv = (TextView) view.findViewById(R.id.numTv);
        }
    }


    public void updateDatas(String[] data){
        this.datas = data;
        notifyDataSetChanged();
    }

}

