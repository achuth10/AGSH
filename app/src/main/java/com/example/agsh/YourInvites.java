package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.agsh.Adapters.InviteAdapter;
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
    private DatabaseReference UserDatabaseReference,numref,inviteuserref;
    String mynum;
    InvitedUser invitedUser;
    private User user;
    private ArrayList<InvitedUser>users;
    private InviteAdapter inviteAdapter;
    private RecyclerView recyclerView;
    ValueEventListener inviteListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_your_invites);
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        users=new ArrayList<>();
        recyclerView=findViewById(R.id.InviteRecyler);
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


         inviteListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                users.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    for(DataSnapshot data :dataSnapshot1.getChildren())
                    {
                        if(data.getKey().equals("InvitedUser")) {
                            invitedUser = data.getValue(InvitedUser.class);
//                            System.out.println("Number is " + invitedUser.getNumber());
                            if (invitedUser.getNumber().equals(mynum)) {

                                inviteuserref = UserDatabaseReference.child(invitedUser.getBy()).child("Details");
                                inviteuserref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        user=dataSnapshot.getValue(User.class);
                                        System.out.println("Invited by " + user.getName());
                                        invitedUser.setInvitee(user.getName());
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                users.add(invitedUser);
                            }
                        }

                    }
                }


                disp();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        UserDatabaseReference.addValueEventListener(inviteListener);
    }

    private void disp() {
        inviteAdapter= new InviteAdapter(getApplicationContext(),users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(inviteAdapter);
        inviteAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
