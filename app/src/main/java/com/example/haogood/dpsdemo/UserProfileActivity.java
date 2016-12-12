package com.example.haogood.dpsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView userName;

    //github commit testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        userName = (TextView)findViewById(R.id.userName);
        userName.setText(firebaseAuth.getCurrentUser().getEmail());
    }

    public void btnLogoutOnClick(View view){
        firebaseAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void btnMapOnClick(View view){
        startActivity(new Intent(this, MapsActivity.class));
        finish();
    }
}
