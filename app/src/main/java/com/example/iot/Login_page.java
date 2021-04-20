package com.example.iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_page extends AppCompatActivity {
    EditText login_mail,login_password;
    TextView forgot_password,create_account,skip;
    FloatingActionButton login_button;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login_mail = (EditText) findViewById(R.id.login_mail_id);
        login_password = (EditText)findViewById(R.id.login_password);
        forgot_password = findViewById(R.id.forgot_password);
        login_button = findViewById(R.id.login_fab);
        create_account = findViewById(R.id.login_create_account);
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");


        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_page.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String forgot_mail = login_mail.getText().toString().trim();
                if(!forgot_mail.equals("")){
                    firebaseAuth.sendPasswordResetEmail(forgot_mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login_page.this,"Check your mail to reset the password",Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(Login_page.this,"Error to reset the password",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Login_page.this,"Enter your mail",Toast.LENGTH_SHORT).show();
                }
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_mail.getText().toString().trim();
                String pass = login_password.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    login_mail.setError("Enter Your Email");
                }
                if(TextUtils.isEmpty(pass)){
                    login_password.setError("Enter Your Password");
                }
                else if(!(email.equals(""))&&(!(pass.equals("")))){
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkEmailVerification();
                            }
                            else
                            {
                                Toast.makeText(Login_page.this,"Login failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }





    private void checkEmailVerification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String uname = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        boolean flag = user.isEmailVerified();
        if(flag){
            Toast.makeText(Login_page.this,"Login successfull",Toast.LENGTH_SHORT).show();
            //  put an intent
            Intent intent = new Intent(Login_page.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else
        {
            Toast.makeText(Login_page.this,"Verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
