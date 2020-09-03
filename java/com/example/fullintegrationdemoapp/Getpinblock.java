package com.example.fullintegrationdemoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.pax.poslink.CommSetting;
import com.pax.poslink.aidl.BasePOSLinkCallback;
import com.pax.poslink.fullIntegration.GetPINBlock;

public class Getpinblock extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String edc,trans;
    String acctnum, encryptType, keyslot, timeout, pinpad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpinblock);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.edc_type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.trans_type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        edc = spinner.getSelectedItem().toString();

        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        trans = spinner2.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        edc = "CREDIT";
        trans = "SALE";
    }

    private CommSetting commset(){
        CommSetting commSetting = new CommSetting();
        commSetting.setType(CommSetting.AIDL);
        commSetting.setTimeOut("-1");
        return commSetting;
    }

    public GetPINBlock.GetPINBlockRequest doGetPINBlockRequest(){
        GetPINBlock.GetPINBlockRequest getpinblockreq = new GetPINBlock.GetPINBlockRequest();
        EditText acct = findViewById(R.id.editTextTextPersonName1);
        acctnum = acct.getText().toString();
        EditText encrypt = findViewById(R.id.editTextTextPersonName);
        encryptType = encrypt.getText().toString();
        EditText key = findViewById(R.id.editTextTextPersonName2);
        keyslot = key.getText().toString();
        EditText pinpadtype = findViewById(R.id.editTextTextPersonName3);
        pinpad = pinpadtype.getText().toString();
        EditText time = findViewById(R.id.editTextTextPersonName4);
        timeout = time.getText().toString();

        getpinblockreq.setEdcType(edc);
        getpinblockreq.setTransType(trans);
        getpinblockreq.setAccountNumber(acctnum);
        getpinblockreq.setEncryptionType(encryptType);
        getpinblockreq.setKeySlot(keyslot);
        getpinblockreq.setPinpadType(pinpad);
        getpinblockreq.setTimeOut100ms(Integer.parseInt(timeout));

        return getpinblockreq;
    }

    public void processtrans(View view){
        GetPINBlock.getPinBlock(this, doGetPINBlockRequest(), commset(), new BasePOSLinkCallback<GetPINBlock.GetPINBlockResponse>(){
            @Override
            public void onFinish(GetPINBlock.GetPINBlockResponse getPINBlockResponse) {
                String resultcode = "Result Code: " + getPINBlockResponse.getResultCode();
                TextView textView = findViewById(R.id.textView);
                textView.setText(resultcode);

                String resulttxt = "Result Text: " + getPINBlockResponse.getResultTxt();
                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText(resulttxt);

                String pinblock = "PIN Block: " + getPINBlockResponse.getPinBlock();
                TextView textView3 = findViewById(R.id.textView3);
                textView3.setText(pinblock);

                String ksn = "KSN: " + getPINBlockResponse.getKSN();
                TextView textView4 = findViewById(R.id.textView4);
                textView4.setText(ksn);
            }
        },null);
    }
}