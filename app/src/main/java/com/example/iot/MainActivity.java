package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabPageAdapter tabPageAdapter;
    private TabLayout tab;
    private Button start;
    private TextView swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (restorePrefData()) {
            Intent mainActivity = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(mainActivity);
            finish();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        swipe = findViewById(R.id.swipe);
        start = findViewById(R.id.Start);
        tab = findViewById(R.id.Tab);

        final List<Items> content = new ArrayList<>();
        content.add(new Items("", "WELCOME", "HI USER!! ARE YOU EXCITED TO USE THIS APP AND ITS FEATURES.", R.mipmap.grocery_twelve));
        content.add(new Items("", "Login", "Login with your proper credentials to utilize the features of this application.\n If you forget the password you can reset it!!", R.mipmap.grocery_none));
        content.add(new Items("", "Create Account", "Create an account by specifying the details that are asked during registration.", R.mipmap.grocery_six));
        content.add(new Items("", "Select Device", "You can select the device that you want to control and verify its status.", R.mipmap.grocery_five));
        content.add(new Items("", "Multi select", "You can add multiple devices that you want to connect and control.", R.mipmap.grocery_four));

        viewPager = findViewById(R.id.viewPager);
        tabPageAdapter = new TabPageAdapter(this, content);
        viewPager.setAdapter(tabPageAdapter);
        tab.setupWithViewPager(viewPager);
        tab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == content.size() - 1) {
                    animateViewIn();
                } else if (tab.getPosition() == content.size() - 2) {
                    animateViewOut();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrefData();
                Intent main = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(main);
                finish();

            }
        });
    }

    private void animateViewOut() {
        swipe.setVisibility(View.VISIBLE);
        start.setVisibility(View.GONE);
        tab.setVisibility(View.VISIBLE);

    }

    private void animateViewIn() {
        swipe.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
        tab.setVisibility(View.INVISIBLE);

        ViewGroup root = findViewById(R.id.one);
        int count = root.getChildCount();
        float offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        Interpolator interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        for (int i = 0; i < count; i++) {
            View view = root.getChildAt(i);
            view.setVisibility(View.VISIBLE);
            view.setTranslationX(offset);
            view.setAlpha(0.85f);
            view.animate()
                    .translationX(0f)
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1000L)
                    .start();
            offset *= 1.5f;
        }


    }

    private void savePrefData() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("pre", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("opened", true);
        editor.apply();
    }

    private boolean restorePrefData() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("pre", MODE_PRIVATE);
        boolean ActivityOpen;
        ActivityOpen = preferences.getBoolean("opened", false);
        return ActivityOpen;
    }
}

