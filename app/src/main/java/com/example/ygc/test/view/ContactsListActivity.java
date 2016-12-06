package com.example.ygc.test.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.ygc.test.R;
import com.example.ygc.test.adapter.ContactsRecAdapter;
import com.example.ygc.test.entity.Contacts;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactsListActivity extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.btnUpdate)
    Button mBtnUpdate;
    private List<Contacts> mContactsList;
    private List<String> mContactsDemo;
    private ContactsRecAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initdata();
        mRv.setLayoutManager(new LinearLayoutManager(ContactsListActivity.this));
        mRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ContactsRecAdapter(ContactsListActivity.this);
        mAdapter.changeDataList(mContactsDemo);
        mRv.setAdapter(mAdapter);

    }

    private void initdata() {
        String[] contacts = getResources().getStringArray(R.array.contacts_array);
        mContactsDemo = Arrays.asList(contacts);
    }

    @OnClick(R.id.btnUpdate)
    public void onClick() {
        ArrayList<String> dataList = new ArrayList<>(mContactsDemo);
        dataList.add("heheh");
        mAdapter.changeDataList(dataList);
        mAdapter.notifyDataSetChanged();
    }
}
