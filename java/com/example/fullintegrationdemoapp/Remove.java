package com.example.fullintegrationdemoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.pax.poslink.CommSetting;
import com.pax.poslink.aidl.BasePOSLinkCallback;
import com.pax.poslink.fullIntegration.RemoveCard;

public class Remove extends AppCompatActivity {
    String message1, message2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
    }

    private CommSetting commset(){
        CommSetting commSetting = new CommSetting();
        commSetting.setType(CommSetting.AIDL);
        commSetting.setTimeOut("-1");
        return commSetting;
    }

    public RemoveCard.RemoveCardRequest doRemoveCardRequest(){
        RemoveCard.RemoveCardRequest remcardreq =  new RemoveCard.RemoveCardRequest();
        EditText msg1 = findViewById(R.id.editTextTextPersonName);
        message1 = msg1.getText().toString();
        EditText msg2 = findViewById(R.id.editTextTextPersonName2);
        message2 = msg2.getText().toString();

        remcardreq.setMessage1(message1);
        remcardreq.setMessage2(message2);

        return remcardreq;
    }

    public void processtrans(View view){
        RemoveCard.removeCard(this, doRemoveCardRequest(), commset(), new BasePOSLinkCallback<RemoveCard.RemoveCardResponse>(){
            @Override
            public void onFinish(RemoveCard.RemoveCardResponse removeCardResponse) {
                String resultcode = "Result Code: " + removeCardResponse.getResultCode();
                TextView textView = findViewById(R.id.textView);
                textView.setText(resultcode);

                String resulttxt = "Result Text: " + removeCardResponse.getResultTxt();
                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText(resulttxt);
            }
        }, null);
    }
}