package com.example.fullintegrationdemoapp;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pax.poslink.constant.CardEvent;
import com.pax.poslink.fullIntegration.FullIntegrationBase;
import com.pax.poslink.fullIntegration.InputAccount;
import java.text.DecimalFormat;
import java.util.Objects;

public class InputAccountStart extends AppCompatActivity {
    InputAccount ia;
    private SparseArray<Light> clssLight = new SparseArray<>();
    private AlphaAnimation blinking;
    LightChanger lightChanger = new LightChanger();
    private LinearLayout llClssLight;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_account_start);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.activity_input_account_start, null);
        llClssLight = (LinearLayout) rootView.findViewById(R.id.clsslight);

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

            blinking = new AlphaAnimation(1, 0);
            blinking.setDuration(500);
            blinking.setRepeatCount(Animation.INFINITE);
            blinking.setRepeatMode(Animation.REVERSE);
            clssLight.clear();
            clssLight.put(R.id.light1, new Light((ImageView) findViewById(R.id.light1), new Integer[]{R.mipmap.blue_off, R.mipmap.blue_on}));
            clssLight.put(R.id.light2, new Light((ImageView) findViewById(R.id.light2), new Integer[]{R.mipmap.yellow_off, R.mipmap.yellow_on}));
            clssLight.put(R.id.light3, new Light((ImageView) findViewById(R.id.light3), new Integer[]{R.mipmap.green_off, R.mipmap.green_on}));
            clssLight.put(R.id.light4, new Light((ImageView) findViewById(R.id.light4), new Integer[]{R.mipmap.red_off, R.mipmap.red_on}));
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
                switch (s) {
                    case CardEvent.CLSS_LIGHT_STATUS_NOT_READY:
                        lightChanger.change(R.id.light1, Light.STATUS_OFF);
                        lightChanger.change(R.id.light2, Light.STATUS_OFF);
                        lightChanger.change(R.id.light3, Light.STATUS_OFF);
                        lightChanger.change(R.id.light4, Light.STATUS_OFF);
                        break;
                    case CardEvent.CLSS_LIGHT_STATUS_IDLE:
                        lightChanger.change(R.id.light1, Light.STATUS_BLINK);
                        lightChanger.change(R.id.light2, Light.STATUS_OFF);
                        lightChanger.change(R.id.light3, Light.STATUS_OFF);
                        lightChanger.change(R.id.light4, Light.STATUS_OFF);
                        break;
                    case CardEvent.CLSS_LIGHT_STATUS_READY_FOR_TXN:
                        lightChanger.change(R.id.light1, Light.STATUS_ON);
                        lightChanger.change(R.id.light2, Light.STATUS_OFF);
                        lightChanger.change(R.id.light3, Light.STATUS_OFF);
                        lightChanger.change(R.id.light4, Light.STATUS_OFF);
                        break;
                    case CardEvent.CLSS_LIGHT_STATUS_PROCESSING:
                        lightChanger.change(R.id.light1, Light.STATUS_ON);
                        lightChanger.change(R.id.light2, Light.STATUS_ON);
                        lightChanger.change(R.id.light3, Light.STATUS_OFF);
                        lightChanger.change(R.id.light4, Light.STATUS_OFF);
                        break;
                    case CardEvent.CLSS_LIGHT_STATUS_REMOVE_CARD:
                        lightChanger.change(R.id.light1, Light.STATUS_ON);
                        lightChanger.change(R.id.light2, Light.STATUS_BLINK);
                        lightChanger.change(R.id.light3, Light.STATUS_BLINK);
                        lightChanger.change(R.id.light4, Light.STATUS_OFF);
                        break;
                    case CardEvent.CLSS_LIGHT_STATUS_COMPLETE:
                        lightChanger.change(R.id.light1, Light.STATUS_OFF);
                        lightChanger.change(R.id.light2, Light.STATUS_OFF);
                        lightChanger.change(R.id.light3, Light.STATUS_BLINK);
                        lightChanger.change(R.id.light4, Light.STATUS_OFF);
                        break;
                    case CardEvent.CLSS_LIGHT_STATUS_ERROR:
                        lightChanger.change(R.id.light1, Light.STATUS_OFF);
                        lightChanger.change(R.id.light2, Light.STATUS_OFF);
                        lightChanger.change(R.id.light3, Light.STATUS_OFF);
                        lightChanger.change(R.id.light4, Light.STATUS_BLINK);
                        break;
                }
            }
        });
    }

    private void setLightImage(int index, int status) {
        Light lightTemp = clssLight.get(index);
        if (lightTemp == null) {
            return;
        }
        if (status == Light.STATUS_BLINK) {
            lightTemp.view.setImageResource(lightTemp.status[Light.STATUS_ON]);
            lightTemp.view.startAnimation(blinking);
        } else {
            lightTemp.view.clearAnimation();
            lightTemp.view.setImageResource(lightTemp.status[status]);
        }
    }

    public static class Light {
        public static final int STATUS_OFF = 0;
        public static final int STATUS_ON = 1;
        public static final int STATUS_BLINK = 2;
        final ImageView view;
        final Integer[] status;

        Light(ImageView view, Integer[] status) {
            this.view = view;
            this.status = status;
        }
    }

    public class LightChanger {
        public void change(int index, int status) {
            setLightImage(index, status);
        }
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