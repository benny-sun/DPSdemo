package com.example.haogood.dpsdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.net.InetAddress;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText editTextEmail, editTextPassword;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin = (Button)findViewById(R.id.btnLogin);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        progressDialog = new ProgressDialog(this);
    }

    public void loginUser(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            editTextEmail.setError("請輸入信箱");
            Toast.makeText(this, "請輸入信箱", Toast.LENGTH_LONG).show();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("email格式有誤，請重新輸入");
            Toast.makeText(this, "email格式有誤，請重新輸入", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            editTextPassword.setError("請輸入密碼");
            Toast.makeText(this, "請輸入密碼", Toast.LENGTH_LONG).show();
            return;
        }else if(password.length() < 6){
            editTextPassword.setError("請輸入6~20位字元");
            Toast.makeText(this, "請輸入6~20位字元", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("登入中");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), UserNavigation.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "信箱或密碼有錯誤", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void btnLoginOnClick(View view){
        Snackbar.make(view, getNetWorkType(), Snackbar.LENGTH_LONG).show();
//        loginUser();
    }

    public void btnIntentRegisterOnClick(View view){
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null;
    }

    private String getNetWorkType(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
//        boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        String result = "HELLO!";

        if (isConnected){
//            if (isWiFi){
//                result = "isWiFi";
//            }else if(isMobile){
//                result = "isMobile";
//            }
//        } else{
            result = "No Network";
        }
        return result;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }
}
