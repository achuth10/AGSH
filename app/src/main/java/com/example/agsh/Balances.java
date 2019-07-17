package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private TextView pbal,pcbal,azbal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balances);
        pcbal = (TextView)findViewById(R.id.PCBalance);
        azbal = (TextView)findViewById(R.id.APBalance);
        pbal = (TextView)findViewById(R.id.PBalance);
        mauth=FirebaseAuth.getInstance();
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        init();
    }


    private void init() {
       numref.addListenerForSingleValueEvent(new ValueEventListener() {


           @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user1 = dataSnapshot.getValue(User.class);
               pcbal.setText("PC credits - " + user1.getAccbal());
               azbal.setText("Amazon pay  - " + user1.getAmazon());
                pbal.setText("Paytm - " + user1.getPaytm());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
