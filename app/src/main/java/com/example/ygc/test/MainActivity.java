package com.example.ygc.test;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.my_library.Test;
import com.example.ygc.test.view.ContactsListActivity;
import com.example.ygc.test.view.MyProgressDialog;
import com.example.ygc.test.adapter.MyAdapter;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    @Bind(R.id.imageView)
    SubsamplingScaleImageView mImageView;
    @Bind(R.id.in)
    LinearLayout mIn;
    @Bind(R.id.out)
    LinearLayout mOut;
    @Bind(R.id.lv)
    ListView mLv;
    @Bind(R.id.update)
    Button mUpdate;
    @Bind(R.id.hello)
    TextView mHello;
    @Bind(R.id.btnContacts)
    Button mBtnContacts;
    @Bind(R.id.btnCamera)
    Button mBtnCamera;
    @Bind(R.id.toolBar)
    Toolbar mToolBar;

    private EditText mEt;
    private Button mBtn;
    private boolean isShowToast = false;
    public static final String TAG = "MainActivity";
    private String[] datas = {"1001", "1002", "1003", "1004"};
    private String[] datas2 = {"1001", "1002", "1003", "1004", "1001", "1002", "1003", "1004"};
    private MyAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private MyProgressDialog mSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: "+ Test.hello());
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mEt = (EditText) findViewById(R.id.et);

        mBtn = (Button) findViewById(R.id.commit);
        initView();
        initListener();
//        Log.d(TAG, "onCreate-mBtn-width: " + mBtn.getWidth());
//        Log.d(TAG, "onCreate-mBtn-height: " + mBtn.getHeight());
        // 给控件mBtn添加监听
        mBtn.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mAdapter = new MyAdapter();
        mAdapter.updateDatas(datas);
        mLv.setAdapter(mAdapter);
        mHello.setText(addColor("hahah", Color.parseColor("#000000")));

        setSupportActionBar(mToolBar);
        mToolBar.setTitle("ToolBarTest");
        mToolBar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onGlobalLayout() {
        Log.d(TAG, "onGlobalLayout-mBtn-width: " + mBtn.getWidth());
        Log.d(TAG, "onGlobalLayout-mBtn-height: " + mBtn.getHeight());
        mBtn.getViewTreeObserver().removeOnGlobalLayoutListener(MainActivity.this);

    }


    private void initView() {
        SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
        Log.e("chao", sp.getString("et", "default"));
        mEt.setText(sp.getString("et", "default"));

        mImageView.setImage(ImageSource.resource(R.drawable.squirrel));
    }

    private SpannableString addColor(String text, int color) {
        SpannableString spannableString = new SpannableString(text);
//        spannableString.setSpan(new ForegroundColorSpan(color), 0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(color), 0, text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    void startProgressBar(Context context, String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(msg);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    void startMyProgressBar() {
        //创建Dialog并设置样式主题
        mSelectDialog = new MyProgressDialog(this, R.style.dialog);
        Window win = mSelectDialog.getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = -80;//设置x坐标
        params.y = -60;//设置y坐标
        win.setAttributes(params);
        mSelectDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        mSelectDialog.show();
    }

    void closeProgressBar() {
        if (mProgressDialog != null) {

            mProgressDialog.dismiss();
        }
    }

    void closeMyProgressBar() {
        if (mSelectDialog != null) {

            mSelectDialog.dismiss();
        }
    }

    private void initListener() {
        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent,100);
            }
        });

        mBtnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContactsListActivity.class));
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyProgressBar();
                mAdapter.updateDatas(datas2);
                Toast.makeText(MainActivity.this, "update", Toast.LENGTH_SHORT).show();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
////                                closeProgressBar();
////                                startActivity(new Intent(MainActivity.this, PhotoViewActivity.class));
//                            }
//                        });
//                    }
//                }.start();

            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String s = mEt.getText().toString().trim();
//                save(s);
//                startActivity(new Intent(MainActivity.this, PhotoViewActivity.class));
////                show(MainActivity.this, "---", 500);

                Log.d(TAG, "onClick: mBtn");
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Sure ?")
//                        .setMessage("Are yout sure ?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(MainActivity.this, PhotoViewActivity.class));
//
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        })
//                        .setCancelable(false)
//                        .show();


            }
        });

//        mIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: mIn");
//            }
//        });

//        mBtn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                Log.d(TAG, "onTouch: mBtn");
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.d(TAG, "onTouch: DOWN");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.d(TAG, "onTouch: MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.d(TAG, "onTouch: UP");
//                        break;
//                }
//                return true;
//            }
//        });

//        mIn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "onTouch: mIn");
//                return true;
//            }
//        });
//
//        mOut.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////
//                Log.d(TAG, "onTouch: mOut");
//                return false;
//            }
//        });
    }

    public static void show(final Context context, final CharSequence msg, final int len) {

        final Toast toast = Toast.makeText(context.getApplicationContext(), msg, len);
        toast.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, len);

    }

    private void save(String str) {

        SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("et", str);
        edit.commit();
    }

}
