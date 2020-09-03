package com.example.fullintegrationdemoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.pax.poslink.CommSetting;
import com.pax.poslink.fullIntegration.CheckFile;

public class Checkfile extends AppCompatActivity {
    String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkfile);
    }

    private CommSetting commset(){
        CommSetting commSetting = new CommSetting();
        commSetting.setType(CommSetting.AIDL);
        commSetting.setTimeOut("-1");
        return commSetting;
    }

    public CheckFile.CheckFileRequest doCheckFileRequest(){
        CheckFile.CheckFileRequest checkfilereq = new CheckFile.CheckFileRequest();
        EditText filename = findViewById(R.id.editTextTextPersonName);
        file = filename.getText().toString();
        checkfilereq.setFileName(file);
        return checkfilereq;
    }

    public void processtrans(View view){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final CheckFile.CheckFileResponse res = CheckFile.checkFile(getApplicationContext(),doCheckFileRequest(),commset());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String resultcode = "Result Code: " + res.getResultCode();
                        TextView textView = findViewById(R.id.textView);
                        textView.setText(resultcode);

                        String resulttxt = "Result Text: " + res.getResultTxt();
                        TextView textView2 = findViewById(R.id.textView2);
                        textView2.setText(resulttxt);

                        String checksum = "Checksum: " + res.getChecksum();
                        TextView textView3 = findViewById(R.id.textView3);
                        textView3.setText(checksum);
                    }
                });
            }
        });
        thread.start();
    }
}