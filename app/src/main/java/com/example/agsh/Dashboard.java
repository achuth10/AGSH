package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agsh.Models.User;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {//implements SwipeRefreshLayout.OnRefreshListener {
    private FloatingActionButton invite,receive;
    private DatabaseReference UserDatabaseReference,numref;
    private FirebaseAuth mauth ;
    private User user;
    private String accbal;
    private TextView accountbal;
    private SwipeRefreshLayout swiperefresh;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mauth=FirebaseAuth.getInstance();
        swiperefresh= findViewById(R.id.swiperefresh);
        swiperefresh.setRefreshing(true);
        button=findViewById(R.id.Balancebtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Balances.class));
            }
        });
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        initlistener();
        accountbal=findViewById(R.id.accbal);
        invite= findViewById(R.id.fab1);
        receive=findViewById(R.id.fab2);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SendMoney.class));
            }
        });
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddBalance.class);
                i.putExtra("AccBal",accbal);
                startActivity(i);

            }
        });
    }


    private void initlistener() {
        numref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                accbal = user.getAccbal();
                accountbal.setText("Pay connect balance " + accbal);
                swiperefresh.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
