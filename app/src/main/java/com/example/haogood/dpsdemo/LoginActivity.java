package com.example.haogood.dpsdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        final String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            editTextEmail.setError("請輸入信箱");
            Toast.makeText(this, "請輸入信箱", Toast.LENGTH_LONG).show();
            return;
        }else if(!email.matches(emailRegex)){
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
        loginUser();
    }

    public void btnIntentRegisterOnClick(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
