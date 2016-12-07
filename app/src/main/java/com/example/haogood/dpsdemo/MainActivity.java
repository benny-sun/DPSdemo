package com.example.haogood.dpsdemo;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText editTextEmail, editTextPassword;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "請輸入信箱", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "請輸入密碼", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("請稍候");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "註冊成功", Toast.LENGTH_LONG);
                        }else{
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "發生錯誤", Toast.LENGTH_LONG);
                        }
                    }
                });
    }

    public void btnRegisterOnClick(View view){
        registerUser();
    }
}
