package com.example.ygc.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ygc.test.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactsRecAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //    @Bind(R.id.nameTv)
//    TextView mNameTv;
    private List<T> mDataList;
    private Context mContext;

    public ContactsRecAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_contacts_adapter, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).mNameTv.setText((String) mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void changeDataList(List<T> dataList) {
        if (dataList.size() == 0 || dataList == null) {
            return;
        }
        this.mDataList = dataList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //        @Bind(R.id.nameTv)
        TextView mNameTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mNameTv = (TextView) itemView.findViewById(R.id.nameTv);
//            ButterKnife.bind(itemView);
        }
    }
}
