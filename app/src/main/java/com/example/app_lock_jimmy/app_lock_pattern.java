package com.example.app_lock_jimmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.app_lock_jimmy.model.password;
import com.shuhart.stepview.StepView;

import java.util.List;

public class app_lock_pattern extends AppCompatActivity {
    StepView stepView;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    password Mpassword;
    String userPassword;
    TextView stateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock_pattern);

        stepView=findViewById(R.id.stepView);
        linearLayout=findViewById(R.id.LL);
        relativeLayout=findViewById(R.id.main_layout);
        Mpassword=new password(this);
        stateText=findViewById(R.id.state_text);
        stateText.setText(Mpassword.FIRST_USE);
        if(Mpassword.getPASSWORD_KEY()==null){
            linearLayout.setVisibility(View.GONE);
            stepView.setVisibility(View.VISIBLE);
            stepView.setStepsNumber(2);
            stepView.go(0,true);

        }else{
            linearLayout.setVisibility(View.VISIBLE);
            stepView.setVisibility(View.GONE);
            int BackgroundColor= ResourcesCompat.getColor(getResources(),R.color.BLUE,null);
            relativeLayout.setBackgroundColor(BackgroundColor);
            stateText.setTextColor(Color.WHITE);
            
        }
        setUpPatternListner();

    }

    @Override
    public void onBackPressed() {
        if(Mpassword.getPASSWORD_KEY()==null && !Mpassword.isFirst){
            stepView.go(0,true);
            Mpassword.setFirst(true);
            stateText.setText(Mpassword.FIRST_USE);
        }else{
            super.onBackPressed();
        }

    }

    private void setUpPatternListner() {
        final PatternLockView patternLockView = findViewById(R.id.patternView);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Log.d("MainActivity","lockapp_open");

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
//                比對pattern lockct與密碼是否一樣
                String password = PatternLockUtils.patternToString(patternLockView, pattern);
                //不能小於4個字。
                if(password.length()<4){
                    stateText.setText((Mpassword.SCGEMA_FAILED));
                    patternLockView.clearPattern();
                    return;
                }
//              如果設定的Mpassword中的password為空的，代表還沒設定過密碼。
                if(Mpassword.getPASSWORD_KEY() ==null){

                    //第一次設定
                    if(Mpassword.isFirst){
                        userPassword=password;
                        Mpassword.setFirst(false);
                        stateText.setText(Mpassword.CONFIRM_PATTERN);
                        stepView.go(1,true);


                    }else{

                        if(userPassword.equals(password)){
                            Mpassword.setPASSWORD_KEY(password);
                            stateText.setText(Mpassword.PATTERN_SET);
                            stepView.done(true);
                            goToMainActivity();
                        }else{
                            stateText.setText(Mpassword.PATTERN_SET);
                        }
                    }
                }else{
                    //如果密碼對了就進去main。
                    if(Mpassword.isCorrect(password)){
                        stateText.setText(Mpassword.PATTERN_SET);
                        goToMainActivity();
                    //不對就不給進。
                    }else{
                        stateText.setText(Mpassword.INCORRECT_PATTERN);
                    }
                }
                patternLockView.clearPattern();
            }



            @Override
            public void onCleared() {

            }
        });
    }
    private void goToMainActivity() {
        Intent intent = new Intent(app_lock_pattern.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}