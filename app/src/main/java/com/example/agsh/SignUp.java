package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsh.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
private Button submit;
private EditText name,number,email,password,confirmoassword;
String emailtxt,passtxt;
    private FirebaseAuth mauth;
    private DatabaseReference UserDatabaseReference;
    private TextView prevuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
    mauth=FirebaseAuth.getInstance();
    UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    submit = (Button)findViewById(R.id.SubmitBtn);
    name=(EditText)findViewById(R.id.NameEdit);
    number=(EditText)findViewById(R.id.numberedit);
    email =(EditText)findViewById(R.id.EmailEdit);
    password=(EditText)findViewById(R.id.PasswordEdit);
    confirmoassword= findViewById(R.id.confirmPassword);
    prevuser= findViewById(R.id.already_user);

    prevuser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
    });
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            emailtxt = email.getText().toString();
            passtxt = password.getText().toString();
            if (passtxt.equals(confirmoassword.getText().toString())) {
                mauth.createUserWithEmailAndPassword(emailtxt, passtxt)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignUp.this, Dashboard.class));
                                    final User user = new User(emailtxt, name.getText().toString(), number.getText().toString(), "0", mauth.getUid(), "1000", "1000");
                                    UserDatabaseReference.child(mauth.getUid()).child("Details").setValue(user);
                                    finish();
                                }
                            }
                        });
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Passwords dont match",Toast.LENGTH_SHORT).show();
            }
        }
    });
    }
}
