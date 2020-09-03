package com.example.fullintegrationdemoapp;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.pax.poslink.fullIntegration.FullIntegrationBase;
import com.pax.poslink.fullIntegration.InputAccount;
import java.text.DecimalFormat;
import java.util.Objects;

public class InputAccountStart extends AppCompatActivity {
    InputAccount ia;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_account_start);

        Intent intent = getIntent();
        DecimalFormat df = new DecimalFormat("#.00");
        double amt = Double.parseDouble(Objects.requireNonNull(intent.getStringExtra("amount"))) / 100;
        String amount = "Amount:                      " + "$ " + df.format(amt);
        TextView textView = findViewById(R.id.textView);
        textView.setText(amount);
        if(intent.getStringExtra("manual").equals("1")){
            EditText card = findViewById(R.id.editTextTextPersonName);
            Button btn = findViewById(R.id.button);
            card.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
        }
        if(intent.getStringExtra("swipe").equals("1")){
            ImageView imgswipe = findViewById(R.id.imageView2);
            imgswipe.setAlpha(1.0f);
        }
        if(intent.getStringExtra("insert").equals("1")){
            ImageView imgchip = findViewById(R.id.imageView);
            imgchip.setAlpha(1.0f);
        }
        if(intent.getStringExtra("tap").equals("1")){
            ImageView imgtap = findViewById(R.id.imageView3);
            imgtap.setAlpha(1.0f);
        }


        ia = InputAccount.getInstance();
        ia.handleInputAccount(new FullIntegrationBase.CurrentStepCallback() {
            @Override
            public void onSuccess() {
                onBackPressed();
            }
            @Override
            public void onFail(String s, String s1) {
            }
        }, new InputAccount.CardEventListener() {
            @Override
            public void onEvent(String s) {
            }
        });
    }

    public void confirm(View view){
        EditText card = findViewById(R.id.editTextTextPersonName);
        String cardnum = card.getText().toString();
        ia = InputAccount.getInstance();
        ia.handleInputCardNum(cardnum, new FullIntegrationBase.CurrentStepCallback() {
            @Override
            public void onSuccess() {
                onBackPressed();
            }
            @Override
            public void onFail(String s, String s1) {
            }
        });
    }
}