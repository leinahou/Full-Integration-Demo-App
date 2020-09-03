package com.example.fullintegrationdemoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.pax.poslink.CommSetting;
import com.pax.poslink.ProcessTransResult;
import com.pax.poslink.fullIntegration.Init;

public class MainActivity extends AppCompatActivity {
    Intent intent1;
    String result, result1, sn, model, os, appname, appversion;
    ProcessTransResult TransResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private CommSetting commset(){
        CommSetting commSetting = new CommSetting();
        commSetting.setType(CommSetting.AIDL);
        commSetting.setTimeOut("-1");
        return commSetting;
    }

    public void manageInit(View view) {
        intent1 = new Intent(this, DisplayInfo.class);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Init.InitResponse res = Init.init(getApplicationContext(), commset());

                TransResult = res.getProcessTransResult();
                result = "POSLink ProcessTrans: " + TransResult.Code + ", " + TransResult.Msg;
                result1 = "Result: " + res.getResultCode() + ", " + res.getResultTxt();
                sn = "SN: " + res.getSn();
                model = "Model Name: " + res.getModelName();
                os = "OS Version: " + res.getOsVersion();
                appname = "App Name: " + res.getAppName();
                appversion = "App Version: " + res.getAppVersion();

                intent1.putExtra("info", result);
                intent1.putExtra("info1", result1);
                intent1.putExtra("infosn", sn);
                intent1.putExtra("infomodel", model);
                intent1.putExtra("infoos", os);
                intent1.putExtra("infoappN", appname);
                intent1.putExtra("infoappV", appversion);

                startActivity(intent1);
            }
        });
        thread.start();
    }

    public void manageInput(View view) {
        intent1 = new Intent(this, Inputaccount.class);
        startActivity(intent1);
    }

    public void manageAuth(View view) {
        intent1 = new Intent(this, Authorize.class);
        startActivity(intent1);
    }

    public void manageComplete(View view) {
        intent1 = new Intent(this, Completeonline.class);
        startActivity(intent1);
    }

    public void manageRemove(View view) {
        intent1 = new Intent(this, Remove.class);
        startActivity(intent1);
    }

    public void managecheckfile(View view) {
        intent1 = new Intent(this, Checkfile.class);
        startActivity(intent1);
    }

    public void managegetpinblock(View view) {
        intent1 = new Intent(this, Getpinblock.class);
        startActivity(intent1);
    }

}