package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.agsh.Models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddBalance extends AppCompatActivity {
private Button addmoney ,quick,paytm,amznpay;
private EditText amt;
    private FirebaseAuth mauth;
    private DatabaseReference UserDatabaseReference,numref;
    private User user1;
    private String prevbal,paytmbal,amznpaybal,accbal;
    int newbalance;
    private RelativeLayout relativeLayout;
    private boolean flag ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
        flag=false;
        relativeLayout =findViewById(R.id.AddRelative);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            prevbal = extras.getString("AccBal");
        }
        mauth=FirebaseAuth.getInstance();
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        init();
        amt=findViewById(R.id.AddAmtEdit);
        addmoney=findViewById(R.id.AddMoneyBtn);
        quick= findViewById(R.id.AddMoneyBtnTemp);
        amznpay= findViewById(R.id.AddMoneyBtnTempAmzn);
        paytm = findViewById(R.id.AddMoneyBtnTempPaytm);

        addmoney.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!flag) {
                    quick.setVisibility(View.VISIBLE);
                    amznpay.setVisibility(View.VISIBLE);
                    paytm.setVisibility(View.VISIBLE);
                    flag = true;
                }
                else
                {
                    quick.setVisibility(View.INVISIBLE);
                    amznpay.setVisibility(View.INVISIBLE);
                    paytm.setVisibility(View.INVISIBLE);
                    flag = false;
                }
                return true;
            }
        });
        quick.setVisibility(View.INVISIBLE);
        amznpay.setVisibility(View.INVISIBLE);
        paytm.setVisibility(View.INVISIBLE);
        addmoney.setEnabled(false);
        quick.setEnabled(false);
        amznpay.setEnabled(false);
        paytm.setEnabled(false);



        amznpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!amt.getText().toString().equals("")) {
                        if(Integer.parseInt(amznpaybal)<Integer.parseInt(amt.getText().toString()))
                            Snackbar.make(relativeLayout, "Insufficient balance in Amazon pay", Snackbar.LENGTH_SHORT).show();
                        else {
                            amznpaybal = String.valueOf(Integer.parseInt(amznpaybal) - Integer.parseInt(amt.getText().toString()));
                            numref.child("amazon").setValue(amznpaybal);
                            int a = Integer.parseInt(amt.getText().toString());
                            String newvalue = String.valueOf(Integer.parseInt(prevbal) + a);
                            numref.child("accbal").setValue(newvalue);
                            Intent i = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amt.getText().toString().equals("")) {
                    if(Integer.parseInt(paytmbal)<Integer.parseInt(amt.getText().toString()))
                        Snackbar.make(relativeLayout, "Insufficient balance in Paytm wallet", Snackbar.LENGTH_SHORT).show();
                    else {
                        paytmbal = String.valueOf(Integer.parseInt(paytmbal) - Integer.parseInt(amt.getText().toString()));
                        numref.child("paytm").setValue(paytmbal);
                        int a = Integer.parseInt(amt.getText().toString());
                        String newvalue = String.valueOf(Integer.parseInt(prevbal) + a);
                        numref.child("accbal").setValue(newvalue);
                        Intent i = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amt.getText().toString().equals("") || Integer.parseInt(amt.getText().toString()) < 50) {
                    Snackbar.make(relativeLayout, "Please enter a valid amount", Snackbar.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(getApplicationContext(), Temp.class);
                    i.putExtra("AddAmt", amt.getText().toString());
                    startActivity(i);
                }
            }
        });

        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a =0;
                if(!amt.getText().toString().equals(""))
                    a=Integer.parseInt(amt.getText().toString());
                String newvalue = String.valueOf(Integer.parseInt(prevbal) + a);
                numref.child("accbal").setValue(newvalue);
                Intent i = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toast(String amazon_pay) {
        Toast.makeText(getApplicationContext(),amazon_pay,Toast.LENGTH_SHORT).show();
    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.invitemenu, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.urinvites:
//                startActivity(new Intent(getApplicationContext(),Invites.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void init() {
        numref.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user1 = dataSnapshot.getValue(User.class);

                accbal = user1.getAccbal();
                amznpaybal = user1.getAmazon();
                paytmbal = user1.getPaytm();


                addmoney.setEnabled(true);
                quick.setEnabled(true);
                amznpay.setEnabled(true);
                paytm.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
