package com.example.logindemojava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {
    ImageView img;
    TextView name,email;
    Button logoutBtn;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        img = (ImageView)findViewById(R.id.pimage);
        name = (TextView)findViewById(R.id.txtName);
        email = (TextView)findViewById(R.id.email);
        logoutBtn = (Button)findViewById(R.id.btn_log_out);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null){
            try {
                Bundle inBundle = getIntent().getExtras();
                String nameTxt = inBundle.getString("name").toString();
                String surName = inBundle.getString("surname").toString();
                String imageUrl = inBundle.getString("imageUrl").toString();
                name.setText("" + nameTxt+ " " +surName);
                Glide.with(getApplicationContext()).load(imageUrl).into(img);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            name.setText(account.getDisplayName());
            email.setText(account.getEmail());
            Glide.with(getApplicationContext()).load(account.getPhotoUrl()).into(img);
        }
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(DashboardActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(DashboardActivity.this,MainActivity.class));
            finish();
        }
    }
}