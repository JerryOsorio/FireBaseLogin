package com.example.jerry.firebaselogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_register;
    private EditText edt_email;
    private EditText edt_password;
    private TextView txt_sign_in;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),profileActivity.class));
        }

        progressBar = new ProgressBar(this);
        btn_register = (Button) findViewById(R.id.asu_btn_register);
        edt_email = (EditText) findViewById(R.id.asu_edt_email);
        edt_password = (EditText) findViewById(R.id.asu_edt_password);
        txt_sign_in = (TextView) findViewById(R.id.asu_txt_sign_in);

        btn_register.setOnClickListener(this);
        txt_sign_in.setOnClickListener(this);
    }


    private void registerUser(){
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
            //email is empty
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
            //password is empty
        }

        //if all goes well
        progressBar.isShown();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user successfully registered
                            Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),profileActivity.class));
                        }
                        else{

                            Toast.makeText(SignUpActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view == btn_register){
            registerUser();
        }

        if(view == txt_sign_in){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
