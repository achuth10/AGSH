package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agsh.Models.InvitedUser;
import com.example.agsh.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SendMoney extends AppCompatActivity {
    private FirebaseAuth mauth;
    private DatabaseReference UserDatabaseReference,numref,inviteuserref;
    private User user,user1;
    private EditText number,amt;
    private Button sendmoney;
    private String toid,toamt="";
    int flag= 0 ;
    ValueEventListener userListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        mauth=FirebaseAuth.getInstance();
        number=findViewById(R.id.SendNumber);
        amt = findViewById(R.id.SendAmt);
        sendmoney= findViewById(R.id.Sendbtn);
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            toid = extras.getString("Uuid");
            toamt= extras.getString("Amt");
        }
        if(!toamt.equals(""))
        {
            init();
            amt.setText(toamt);

        }
        sendmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                initlistener();
            }
        });
    }


    private void initlistener() {

        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (flag == 0) {
                        for (DataSnapshot data : dataSnapshot1.getChildren()) {
                            if (data.getKey().equals("Details")) {
                                user = data.getValue(User.class);
                                if (user.getPhonenumber().equals(number.getText().toString())) {
                                    String balance = user.getAccbal();
                                    flag = 1;
                                    int newbal = Integer.parseInt(balance) + Integer.parseInt(amt.getText().toString());
                                    UserDatabaseReference.child(user.getId()).child("Details").child("accbal").setValue(String.valueOf(newbal));
                                    if (!toamt.equals("")) {
                                        String realamt = amt.getText().toString();
                                        if (Integer.parseInt(toamt) - Integer.parseInt(realamt) == 0) {
                                            UserDatabaseReference.child(user.getId()).child("InvitedUser").removeValue();
                                        }

                                    }
                                }

                            }

                        }
                    }
                }
                if(flag==0)
                {
                    Toast.makeText(getApplicationContext(),"User is not signed up with PC",Toast.LENGTH_SHORT).show();
                }
                else
                    startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        UserDatabaseReference.addValueEventListener(userListener);

    }

    private void init() {
        UserDatabaseReference.child(toid).child("Details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user1 = dataSnapshot.getValue(User.class);
                number.setText(user1.getPhonenumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
