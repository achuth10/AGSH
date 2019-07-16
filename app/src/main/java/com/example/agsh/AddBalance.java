package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agsh.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddBalance extends AppCompatActivity {
private Button addmoney ,quick;
private EditText amt;
    private FirebaseAuth mauth;
    private DatabaseReference UserDatabaseReference,numref;
    private User user;
    private String prevbal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);


        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            prevbal = extras.getString("AccBal");
        }
        mauth=FirebaseAuth.getInstance();
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details").child("accbal");
        amt=findViewById(R.id.AddAmtEdit);
        addmoney=findViewById(R.id.AddMoneyBtn);
        quick= findViewById(R.id.AddMoneyBtnTemp);
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Temp.class);
                i.putExtra("AddAmt",amt.getText().toString());
                startActivity(i);
            }
        });

        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a =0;
                if(!amt.getText().toString().equals(""))
                    a=Integer.parseInt(amt.getText().toString());
                String newvalue = String.valueOf(Integer.parseInt(prevbal) + a);
                numref.setValue(newvalue);
                Intent i = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
            }
        });
    }

}
