package com.example.fullintegrationdemoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.pax.poslink.CommSetting;
import com.pax.poslink.fullIntegration.CompleteOnlineEMV;

public class Completeonline extends AppCompatActivity {
    String oauthres, rescode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completeonline);
    }

    private CommSetting commset(){
        CommSetting commSetting = new CommSetting();
        commSetting.setType(CommSetting.AIDL);
        commSetting.setTimeOut("-1");
        return commSetting;
    }

    public CompleteOnlineEMV.CompleteOnlineEMVRequest doCompleteOnlineEMVRequest(){
        CompleteOnlineEMV.CompleteOnlineEMVRequest coemvreq = new CompleteOnlineEMV.CompleteOnlineEMVRequest();
        EditText onlineauthres = findViewById(R.id.editTextTextPersonName);
        oauthres = onlineauthres.getText().toString();
        EditText code = findViewById(R.id.editTextTextPersonName2);
        rescode = code.getText().toString();
        coemvreq.setOnlineAuthorizationResult(oauthres);
        coemvreq.setResponseCode(rescode);
        return coemvreq;
    }

    public void processtrans(View view){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            final CompleteOnlineEMV.CompleteOnlineEMVResponse res = CompleteOnlineEMV.completeOnlineEMV(getApplicationContext(), doCompleteOnlineEMVRequest(), commset());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String resultcode = "Result Code: " + res.getResultCode();
                        TextView textView = findViewById(R.id.textView);
                        textView.setText(resultcode);

                        String resulttxt = "Result Text: " + res.getResultTxt();
                        TextView textView2 = findViewById(R.id.textView2);
                        textView2.setText(resulttxt);

                        String authres = "Authorization Result: " + res.getAuthorizationResult();
                        TextView textView3 = findViewById(R.id.textView3);
                        textView3.setText(authres);

                        String emv = "EMVData: " + res.getEmvData();
                        TextView textView4 = findViewById(R.id.textView4);
                        textView4.setText(emv);
                    }
                });
            }
        });
        thread.start();
    }
}