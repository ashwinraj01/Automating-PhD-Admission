package com.winash.uniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private Button RegisterUser;
    private EditText fname,lname,number,uname,pass;
    private ProgressBar progress;
    DatabaseReference RefApplicant;
    String UserUniqueNumber;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        RegisterUser=(Button) findViewById(R.id.RegisterBtn);
        fname=(EditText) findViewById(R.id.fname);
        lname=(EditText) findViewById(R.id.lname);
        number=(EditText) findViewById(R.id.number);
        uname=(EditText) findViewById(R.id.Username);
        pass=(EditText) findViewById(R.id.Password);
        progress=(ProgressBar) findViewById(R.id.progressBarReg);
        RefApplicant= FirebaseDatabase.getInstance().getReference();
        RegisterUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.RegisterBtn:
                registeruser();
                break;
        }
    }

    private void registeruser() {
        String email=uname.getText().toString().trim();
        String fn=fname.getText().toString().trim();
        String ln=lname.getText().toString().trim();
        String num=number.getText().toString().trim();
        String p=pass.getText().toString().trim();
        if(email.isEmpty())
        {
            uname.setError("Email Required!!!");
            uname.requestFocus();
            return;
        }
        if(fn.isEmpty()||!noNumber(fn))
        {
            fname.setError("First Name Not Valid!!!");
            fname.requestFocus();
            return;
        }
        if(ln.isEmpty()||!noNumber(ln))
        {
            lname.setError("Last Name Not Valid!!!");
            lname.requestFocus();
            return;
        }
        if(num.isEmpty()||!isInteger(num)||num.length()!=10)
        {
            number.setError("Number Not Valid!!!");
            number.requestFocus();
            return;
        }
        if(p.isEmpty()||p.length()<=8)
        {
            pass.setError("Password Must be greater than 8!!!");
            pass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()||email.contains("@uniapp.admin.com"))
        {
            uname.setError("Valid Email Required!!!");
            uname.requestFocus();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        RegisterUser.setVisibility(View.GONE);
        mAuth.createUserWithEmailAndPassword(email,p)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Applicant newapp=new Applicant(fn,ln,email,num,false,FirebaseAuth.getInstance().getUid());
                            UserUniqueNumber=mAuth.getUid();
                            RefApplicant.child("Applicant").child(UserUniqueNumber).setValue(newapp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterUser.this, "Applicant has been successfully Registered", Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(RegisterUser.this,LoginUser.class));
                                        finish();
                                    }else
                                    {
                                        Toast.makeText(RegisterUser.this, "Unsuccessful!!Try Again", Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                        }else {
                            RegisterUser.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterUser.this, "Unsuccessful!!Try Again", Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
    public boolean noNumber(String s){
        char[] chars = s.toCharArray();
        for(char c : chars){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    public boolean isInteger(String s){
        int flag=0;
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                flag++;
            }
        }
        if(flag==s.length())
            return true;
        else
            return false;
    }

}