package com.example.chathub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText edtEmail,edtPassword,edtName;
    Button btnSignUp;
    FirebaseAuth mAuth;
    DatabaseReference mDbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                signup(name,email,password);
            }
        });
    }

    private void signup(String name,String email,String password){
        //logic of creating user

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // code for jumping to home activity
                            addUserToDatabase(name,email,mAuth.getCurrentUser().getUid());
                            Intent intent = new Intent(SignUp.this,MainActivity.class);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUp.this,"some error occured",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUserToDatabase(String name,String email,String user_id){
        //Toast.makeText(SignUp.this, "adduser error last line   " + user_id, Toast.LENGTH_SHORT).show();
        mDbRef = FirebaseDatabase.getInstance().getReference();
        mDbRef.child("user").child(user_id).setValue(new User(user_id,email,name));
    }


}