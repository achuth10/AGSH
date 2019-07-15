package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.agsh.Models.InvitedUser;
import com.example.agsh.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourInvites extends AppCompatActivity {
    private FirebaseAuth mauth;
    private DatabaseReference UserDatabaseReference,numref;
    String mynum;
    private User user;
    private ArrayList<InvitedUser>users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_your_invites);
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        users=new ArrayList<>();
        initlistener();
    }

    private void initlistener() {
        numref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                mynum=user.getPhonenumber();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    users.add(dataSnapshot1.getValue(InvitedUser.class));
                }
                for(int i =0;i<users.size();i++)
                    System.out.println(users.get(i).getNumber());
                InvitedUser invitedUser = dataSnapshot.getValue(InvitedUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        UserDatabaseReference.addValueEventListener(postListener);
    }
}
