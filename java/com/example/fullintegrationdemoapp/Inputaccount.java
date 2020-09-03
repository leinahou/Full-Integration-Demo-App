package com.example.fullintegrationdemoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.pax.poslink.CommSetting;
import com.pax.poslink.aidl.BasePOSLinkCallback;
import com.pax.poslink.fullIntegration.InputAccount;
import java.util.List;

public class Inputaccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener, InputAccount.InputAccountCallback {
    String edc,trans;
    String amt, swipeFlag, manualFlag, tapFlag, chipFlag, encryptFlag, keyslot, timeout;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputaccount);

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

    public InputAccount.InputAccountRequest doInputAccount(){
        InputAccount.InputAccountRequest inputreq = new InputAccount.InputAccountRequest();
        EditText amount = findViewById(R.id.editTextTextPersonName);
        amt = amount.getText().toString();
        EditText swipe = findViewById(R.id.editTextTextPersonName2);
        swipeFlag = swipe.getText().toString();
        EditText manual = findViewById(R.id.editTextTextPersonName4);
        manualFlag = manual.getText().toString();
        EditText tap = findViewById(R.id.editTextTextPersonName3);
        tapFlag = tap.getText().toString();
        EditText chip = findViewById(R.id.editTextTextPersonName5);
        chipFlag = chip.getText().toString();
        EditText encr = findViewById(R.id.editTextTextPersonName6);
        encryptFlag = encr.getText().toString();
        EditText slot = findViewById(R.id.editTextTextPersonName7);
        keyslot = slot.getText().toString();
        EditText time = findViewById(R.id.editTextTextPersonName8);
        timeout = time.getText().toString();

        inputreq.setEdcType(edc);
        inputreq.setTransType(trans);
        inputreq.setAmount(amt);
        inputreq.setMagneticSwipeEntryFlag(swipeFlag);
        inputreq.setManualEntryFlag(manualFlag);
        inputreq.setContactlessEntryFlag(tapFlag);
        inputreq.setContactEMVEntryFlag(chipFlag);
        inputreq.setEncryptionFlag(encryptFlag);
        inputreq.setKeySLot(keyslot);
        inputreq.setTimeOut(timeout);

        return inputreq;
    }

    public void processtrans(View view){
        InputAccount.inputAccountWithEMV(this, doInputAccount(), commset(), new BasePOSLinkCallback<InputAccount.InputAccountResponse>() {
            @Override
            public void onFinish(InputAccount.InputAccountResponse inputAccountResponse) {
                String resultcode = "Result Code: " + inputAccountResponse.getResultCode();
                TextView textView = findViewById(R.id.textView);
                textView.setText(resultcode);

                String resulttxt = "Result Text: " + inputAccountResponse.getResultTxt();
                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText(resulttxt);

                String entry = "Entry Mode: " + inputAccountResponse.getEntryMode();
                TextView textView3 = findViewById(R.id.textView3);
                textView3.setText(entry);

                String track1 = "Track1 Data: " + inputAccountResponse.getTrack1Data();
                TextView textView4 = findViewById(R.id.textView4);
                textView4.setText(track1);

                String track2 = "Track2 Data: " + inputAccountResponse.getTrack2Data();
                TextView textView5 = findViewById(R.id.textView5);
                textView5.setText(track2);

                String track3 = "Track3 Data: " + inputAccountResponse.getTrack3Data();
                TextView textView6 = findViewById(R.id.textView6);
                textView6.setText(track3);

                String pan = "PAN: " + inputAccountResponse.getPan();
                TextView textView7 = findViewById(R.id.textView7);
                textView7.setText(pan);

                String mpan = "MaskedPAN: " + inputAccountResponse.getMaskedPAN();
                TextView textView8 = findViewById(R.id.textView8);
                textView8.setText(mpan);

                String ksn = "KSN: " + inputAccountResponse.getKsn();
                TextView textView9 = findViewById(R.id.textView9);
                textView9.setText(ksn);

                String clsstranspath = "ContactlessTransactionPath: " + inputAccountResponse.getContactlessTransactionPath();
                TextView textView14 = findViewById(R.id.textView14);
                textView14.setText(clsstranspath);

                String clssauthres = "ContactlessAuthorizeResult: " + inputAccountResponse.getAuthorizationResult();
                TextView textView15 = findViewById(R.id.textView15);
                textView15.setText(clssauthres);

                String emv = "EMVData: " + inputAccountResponse.getEmvData();
                TextView textView10 = findViewById(R.id.textView10);
                textView10.setText(emv);

                String date = "Expiry Date: " + inputAccountResponse.getExpiry();
                TextView textView11 = findViewById(R.id.textView11);
                textView11.setText(date);

                String card = "CardHolder: " + inputAccountResponse.getCardholder();
                TextView textView12 = findViewById(R.id.textView12);
                textView12.setText(card);

                String servicecode = "ServiceCode: " + inputAccountResponse.getServiceCode();
                TextView textView13 = findViewById(R.id.textView13);
                textView13.setText(servicecode);
            }
        }, this);
    }

    @Override
    public void onInputAccountStart() {
        intent1 = new Intent(this, InputAccountStart.class);
        intent1.putExtra("amount", amt);
        intent1.putExtra("manual", manualFlag);
        intent1.putExtra("swipe", swipeFlag);
        intent1.putExtra("insert", chipFlag);
        intent1.putExtra("tap", tapFlag);
        startActivity(intent1);
    }

    @Override
    public void onEnterExpiryDate() {

    }

    @Override
    public void onEnterZip() {

    }

    @Override
    public void onEnterCVV() {

    }

    @Override
    public void onSelectEMVApp(List<String> list) {

    }

    @Override
    public void onProcessing(String s, String s1) {
       Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWarnRemoveCard() {

    }
}