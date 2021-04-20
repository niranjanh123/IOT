package com.example.iot;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ImageView imageView;
    TextView text1,text2;
    Animation top,bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();
        imageView = (ImageView) findViewById(R.id.splash_image);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);
        imageView.setAnimation(top);
        text1.setAnimation(bottom);
        text2.setAnimation(bottom);
        final Intent loginIntent  = new Intent(SplashActivity.this, RegisterActivity.class);

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(3000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    FirebaseUser currentuser = firebaseAuth.getCurrentUser();
                    if(currentuser==null){
                        Intent loginIntent  = new Intent(SplashActivity.this, RegisterActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }

                    else {
                        Intent mainIntent  = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
            }
        };timer.start();



    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
