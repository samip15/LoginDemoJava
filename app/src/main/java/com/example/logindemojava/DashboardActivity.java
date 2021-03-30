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
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        img = (ImageView)findViewById(R.id.pimage);
        name = (TextView)findViewById(R.id.txtName);
        email = (TextView)findViewById(R.id.email);
        logoutBtn = (Button)findViewById(R.id.btn_log_out);
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
        if (user!=null){
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            Glide.with(getApplicationContext()).load(user.getPhotoUrl()).into(img);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null){
            startActivity(new Intent(DashboardActivity.this,MainActivity.class));
            finish();
        }
    }
}