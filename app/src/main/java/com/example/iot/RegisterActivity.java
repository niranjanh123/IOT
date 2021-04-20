package com.example.iot;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Sign_in";

    EditText mail, pass, repass;
    TextView skip;
    FloatingActionButton sign_in;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://iotapp-37ada-default-rtdb.firebaseio.com/");
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mail = (EditText) findViewById(R.id.sign_in_mail_id);
        pass = (EditText) findViewById(R.id.sign_in_password);
        repass = (EditText) findViewById(R.id.sign_in_repassword);
        sign_in = findViewById(R.id.fab);
        firebaseAuth = FirebaseAuth.getInstance();
        skip = (TextView) findViewById(R.id.skip);
        reference = firebaseDatabase.getInstance().getReference("Users");

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, Login_page.class);
                startActivity(intent);
                finish();
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mail.getText().toString().trim();
                final String epass = pass.getText().toString().trim();
                final String erepass = repass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mail.setError("Enter your mail");
                }
                if (TextUtils.isEmpty(epass)) {
                    pass.setError("Enter your Password");
                }
                if (TextUtils.isEmpty(erepass)) {
                    repass.setError("Enter your Password");
                } else if (!(email.equals("")) && (!(epass.equals("")) && (!(erepass.equals(""))))) {

                    firebaseAuth.createUserWithEmailAndPassword(email, epass)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        Data data = new Data(email, epass, erepass);
                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                sendEmailVerification();
                                            }
                                        });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Account successfully created.Please check your mail to verify your account.", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(RegisterActivity.this, Login_page.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Account Creation Failed.Please create a new Account.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, "Was not able to send a verfication mail!! Please try again!!", Toast.LENGTH_SHORT).show();
        }
    }
}

