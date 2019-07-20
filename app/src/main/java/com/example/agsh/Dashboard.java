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
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {//implements SwipeRefreshLayout.OnRefreshListener {
    private FloatingActionButton invite,receive;
    private FloatingActionsMenu floatingActionsMenu;
    private DatabaseReference UserDatabaseReference,numref;
    private FirebaseAuth mauth ;
    private User user;
    private String accbal;
    private TextView accountbal,bills,history,balances,cards;
    private SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        mauth=FirebaseAuth.getInstance();
        floatingActionsMenu= findViewById(R.id.Fam);
        swiperefresh= findViewById(R.id.swiperefresh);
        balances= findViewById(R.id.BalanceDashboardBtn);
        bills = findViewById(R.id.DashboardBillsBtn);
        history= findViewById(R.id.TransactionBtn);
        cards= findViewById(R.id.DashboardCardsBtn);
        swiperefresh.setRefreshing(true);
        floatingActionsMenu.setVisibility(View.INVISIBLE);
        balances.setVisibility(View.INVISIBLE);
        bills.setVisibility(View.INVISIBLE);
        cards.setVisibility(View.INVISIBLE);
        history.setVisibility(View.INVISIBLE);
        balances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Balances.class));
            }
        });
        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Invites.class));
            }
        });
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        initlistener();
        accountbal=findViewById(R.id.accbal);
        invite= findViewById(R.id.fab1);
        receive=findViewById(R.id.fab2);
        receive.setVisibility(View.INVISIBLE);
        invite.setVisibility(View.INVISIBLE);
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
                accountbal.setText("Z credits - " + accbal);
                balances.setVisibility(View.VISIBLE);
                bills.setVisibility(View.VISIBLE);
                cards.setVisibility(View.VISIBLE);
                history.setVisibility(View.VISIBLE);
                receive.setVisibility(View.VISIBLE);
                invite.setVisibility(View.VISIBLE);
                floatingActionsMenu.setVisibility(View.VISIBLE);
                swiperefresh.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
