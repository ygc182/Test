package com.example.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.occall.nuts.codes.VerifyCodeManager;
import com.occall.nuts.net.http.callback.TextHttpResponseCallback;
import com.occall.nuts.net.http.client.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.etPhoneNum)
    EditText mEtPhoneNum;
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.etSMSCode)
    EditText mEtSMSCode;
    @Bind(R.id.etGraphCode)
    EditText mEtGraphCode;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.graphCodeContainer)
    LinearLayout mGraphCodeContainer;
    @Bind(R.id.btnRequestCode)
    Button mBtnRequestCode;
    @Bind(R.id.verifySMSCode)
    Button mVerifySMSCode;
    @Bind(R.id.verifyGraphCode)
    Button mVerifyGraphCode;
    private VerifyCodeManager mVerifyCodeManager;
    private String mCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mVerifyCodeManager = new VerifyCodeManager();
        mGraphCodeContainer.setVisibility(View.GONE);

    }

    @OnClick({R.id.btnRequestCode, R.id.verifySMSCode, R.id.verifyGraphCode})
    public void onClick(View view) {
        String phone = mEtPhoneNum.getText().toString().trim();
        String smsCode = mEtSMSCode.getText().toString().trim();
        String graphCode = mEtGraphCode.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btnRequestCode:
                if (!TextUtils.isEmpty(phone)) {
                    mVerifyCodeManager.sendSmsCode(phone, 1001, new TextHttpResponseCallback() {
                        @Override
                        public void onSuccess(Response response, String responseString) {
                            Log.d(TAG, "获取短信验证码－－onSuccess: " + responseString);
                        }

                        @Override
                        public void onFailure(Response response, String responseString, Throwable error) {
                            Log.e(TAG, "获取短信验证码－－onFailure: " + responseString);

                            if (response.getStatus() == 477) {
                                mGraphCodeContainer.setVisibility(View.VISIBLE);
                            } else {
                                mGraphCodeContainer.setVisibility(View.GONE);
                            }
                            try {
                                JSONObject object = new JSONObject(responseString);
                                mCaptcha = object.getString("captcha");
                                Log.d(TAG, "captcha: " + mCaptcha);
                                String captchaRefresh = object.getString("captchaRefresh");
                                if (!TextUtils.isEmpty(captchaRefresh)) {
                                    Log.d(TAG, "captchaRefresh: " + captchaRefresh);
                                    String url = "http://beta.nuts.occall.com" + captchaRefresh;
                                    Log.d(TAG, "url---->" + url);
                                    new LoadImagesTask(mIv).execute(url);
//                                    mIv.setImageURI(Uri.parse(url));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.verifySMSCode:
                if (!TextUtils.isEmpty(smsCode)) {
                    mVerifyCodeManager.verifySmsCode(smsCode, new TextHttpResponseCallback() {
                        @Override
                        public void onSuccess(Response response, String responseString) {
                            Log.d(TAG, "验证短信验证码－－onSuccess: " + responseString);
                        }

                        @Override
                        public void onFailure(Response response, String responseString, Throwable error) {
                            Log.e(TAG, "验证短信验证码－－onFailure: " + responseString);
                        }
                    });
                }
                break;
            case R.id.verifyGraphCode:
                Log.d(TAG, "onClick---graphCode: " + graphCode);
                Log.d(TAG, "onClick---mCaptcha: " + mCaptcha);
                if (!TextUtils.isEmpty(graphCode) && !TextUtils.isEmpty(mCaptcha)) {
                    mVerifyCodeManager.verifyCaptchaSmsCode(mCaptcha, graphCode, new TextHttpResponseCallback() {
                        @Override
                        public void onSuccess(Response response, String responseString) {
                            Log.d(TAG, "验证图形验证码－－onSuccess: " + responseString);

                        }

                        @Override
                        public void onFailure(Response response, String responseString, Throwable error) {
                            Log.d(TAG, "验证图形验证码－－onFailure: " + responseString);

                        }
                    });
                }
                break;
        }
    }
}
