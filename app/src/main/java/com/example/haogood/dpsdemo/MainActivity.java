package com.example.haogood.dpsdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText editTextEmail, editTextPassword, editTextUserName;
    private TextView textViewTemp;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), UserNavigation.class));
            finish();
        }

        btnRegister = (Button) findViewById(R.id.btnRegister);
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        textViewTemp = (TextView) findViewById(R.id.textViewTemp);

        progressDialog = new ProgressDialog(this);
    }


    public void registerUser(){

        final String userName = editTextUserName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            editTextUserName.setError("請輸入姓名");
            Toast.makeText(this, "請輸入姓名", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "請輸入信箱", Toast.LENGTH_LONG).show();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("email格式有誤，請重新輸入");
            Toast.makeText(this, "email格式有誤，請重新輸入", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "請輸入密碼", Toast.LENGTH_LONG).show();
            return;
        }else if(password.length() < 6){
            editTextPassword.setError("請輸入6~20位字元");
            Toast.makeText(this, "請輸入6~20位字元", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("請稍候");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            alertDialogToLogin();
                            final FirebaseUser firebaseUser = task.getResult().getUser();
                            firebaseUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(userName).setPhotoUri(Uri.EMPTY).build());
                        }else{
                            Toast.makeText(MainActivity.this, "發生錯誤", Toast.LENGTH_LONG);
                        }
                    }
                });
    }

    private void alertDialogToLogin(){
        new AlertDialog.Builder(this)
                .setTitle("註冊成功")
                .setMessage("前往登入")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finishAffinity();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();

    }

    public void btnRegisterOnClick(View view){
        registerUser();
    }

    public void btnIntentLoginOnClick(View view){
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
        finish();
    }
}
