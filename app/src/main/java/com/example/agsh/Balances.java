package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agsh.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Balances extends AppCompatActivity {
    private FirebaseAuth mauth;
    private User user1;
    private DatabaseReference UserDatabaseReference,numref;
    private TextView pbal,azbal;
    private Button addpaytm,addamazon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balances);
       // pcbal = (TextView)findViewById(R.id.PCBalance);
        azbal = (TextView)findViewById(R.id.APBalance);
        pbal = (TextView)findViewById(R.id.PBalance);
        mauth=FirebaseAuth.getInstance();
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        addpaytm= findViewById(R.id.PtmBalanceAddbtn);
        addamazon=findViewById(R.id.APBalanceAddbtn);
        addpaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddWalletBalance.class);
                i.putExtra("Wallet","paytm");
                startActivity(i);
            }
        });
        addamazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddWalletBalance.class);
                i.putExtra("Wallet","amazon");
                startActivity(i);
            }
        });
        init();
    }


    private void init() {
       numref.addListenerForSingleValueEvent(new ValueEventListener() {


           @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user1 = dataSnapshot.getValue(User.class);
               //pcbal.setText(user1.getAccbal());
               azbal.setText(user1.getAmazon());
                pbal.setText(user1.getPaytm());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
