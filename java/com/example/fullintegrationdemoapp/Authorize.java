package com.example.fullintegrationdemoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.pax.poslink.CommSetting;
import com.pax.poslink.aidl.BasePOSLinkCallback;
import com.pax.poslink.fullIntegration.AuthorizeCard;

public class Authorize extends AppCompatActivity {
    String amt, encryptType, keyslot, timeout, pinpadType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
    }

    private CommSetting commset(){
        CommSetting commSetting = new CommSetting();
        commSetting.setType(CommSetting.AIDL);
        commSetting.setTimeOut("-1");
        return commSetting;
    }

    public AuthorizeCard.AuthorizeRequest doAuthorizeCard(){
        AuthorizeCard.AuthorizeRequest authreq = new AuthorizeCard.AuthorizeRequest();
        EditText amount = findViewById(R.id.editTextTextPersonName);
        amt = amount.getText().toString();
        EditText encr = findViewById(R.id.editTextTextPersonName2);
        encryptType = encr.getText().toString();
        EditText slot = findViewById(R.id.editTextTextPersonName3);
        keyslot = slot.getText().toString();
        EditText time = findViewById(R.id.editTextTextPersonName4);
        timeout = time.getText().toString();
        EditText pinpad = findViewById(R.id.editTextTextPersonName5);
        pinpadType = pinpad.getText().toString();

        authreq.setAmount(amt);
        authreq.setPinEncryptionType(encryptType);
        authreq.setKeySlot(keyslot);
        authreq.setPinpadType(pinpadType);
        authreq.setTimeOut(Integer.parseInt(timeout));

        return authreq;
    }

    public void processtrans(View view){
        AuthorizeCard.authorize(this, doAuthorizeCard(), commset(), new BasePOSLinkCallback<AuthorizeCard.AuthorizeResponse>(){
            @Override
            public void onFinish(AuthorizeCard.AuthorizeResponse authorizeResponse) {
                String resultcode = "Result Code: " + authorizeResponse.getResultCode();
                TextView textView = findViewById(R.id.textView);
                textView.setText(resultcode);

                String resulttxt = "Result Text: " + authorizeResponse.getResultTxt();
                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText(resulttxt);

                String authres = "Authorization Result: " + authorizeResponse.getAuthorizationResult();
                TextView textView3 = findViewById(R.id.textView3);
                textView3.setText(authres);

                String sign = "Signature Flag: " + authorizeResponse.getSignatureFlag();
                TextView textView4 = findViewById(R.id.textView4);
                textView4.setText(sign);

                String pinblock = "PIN Block: " + authorizeResponse.getPinBlock();
                TextView textView5 = findViewById(R.id.textView5);
                textView5.setText(pinblock);

                String pinbypass = "PIN Bypass Status: " + authorizeResponse.getPinBypassStatus();
                TextView textView6 = findViewById(R.id.textView6);
                textView6.setText(pinbypass);

                String ksn = "KSN: " + authorizeResponse.getKSN();
                TextView textView7 = findViewById(R.id.textView7);
                textView7.setText(ksn);

                String emv = "EMVData: " + authorizeResponse.getEmvData();
                TextView textView8 = findViewById(R.id.textView8);
                textView8.setText(emv);
            }
        }, null);
    }
}