package com.example.iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home Activity";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FloatingActionButton add;
    private ViewGroup viewGroup;
    Adapter madapter;//
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.navigation_drawer);
        drawerLayout =  findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        add =  findViewById(R.id.fab);
        viewGroup = findViewById(android.R.id.content);
        recyclerView = findViewById(R.id.recycler_view);
        list = new ArrayList<String>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");
        list.add("Lights");

        madapter = new Adapter(this,list);
        Log.d(TAG, "onDataChange: new data");
        recyclerView.setAdapter(madapter);
        //toolbar and drawer nav
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.item1:
                        Toast.makeText(HomeActivity.this,"Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item2:
                        Toast.makeText(HomeActivity.this,"Temperature", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item3:
                        Toast.makeText(HomeActivity.this,"Humidity", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item4:
                        Toast.makeText(HomeActivity.this,"About", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item5:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent4 = new Intent(HomeActivity.this,Login_page.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent4);
                        finish();
                        // Toast.makeText(weather_activity.this, "Help", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return  false;
            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //floating button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // CREATING A DIALOG TO ASK FOR USER CONFIRMATION
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                View alertLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_layout_style, viewGroup, false);

                // SET THE VIEW
                builder.setView(alertLayout);

                // SHOW THE DIALOG
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                // TODO: TAKE ACTION WHEN "YES" IS CLICKED
                alertLayout.findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HomeActivity.this, "TODO:// ACTION PENDING", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                // LAUNCH THE ACTIVITY TO CALCULATE THE CAPACITY WHEN "NO" IS CLICKED
                alertLayout.findViewById(R.id.button_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });



            }
        });

    }
}
