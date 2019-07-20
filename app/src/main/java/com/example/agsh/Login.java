
package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {
private Button login;
private TextView signup;
private String password,email;
private EditText emailedit,passedit;
private FirebaseAuth mAuth;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    getSupportActionBar().hide();
    mAuth= FirebaseAuth.getInstance();
    Executor newExecutor = Executors.newSingleThreadExecutor();

    FragmentActivity activity = this;

    final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity, newExecutor, new BiometricPrompt.AuthenticationCallback() {
        @Override


        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
            }
        }

        @Override
        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            System.out.println("success");
            startActivity(new Intent(getApplicationContext(), Dashboard.class));

        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            System.out.println("Failed");

        }
    });

    final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
            .setTitle("Scan to verify your identity")
            .setNegativeButtonText("Cancel")
            .build();
    init();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    if(firebaseUser!=null)
        myBiometricPrompt.authenticate(promptInfo);
    }

    private void init() {
        login= (Button)findViewById(R.id.SubmitLogin);
        emailedit=(EditText)findViewById(R.id.EmailLditLogin);
        passedit=(EditText)findViewById(R.id.PasswordEditLogin);
        signup=(TextView) findViewById(R.id.SignupText);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailedit.getText().toString();
                password = passedit.getText().toString();
                if (email.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Please enter your email id ", Toast.LENGTH_SHORT).show();
                } else if (password.length() <= 5) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid password ", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(Login.this, Dashboard.class));
                                        finish();
                                    }
                                }
                            });


                }
            }
        });
    }
}
