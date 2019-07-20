package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsh.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddWalletBalance extends AppCompatActivity {
private Button AddWalletmoneybtn;
    private DatabaseReference UserDatabaseReference,numref;
    private FirebaseAuth mauth ;
    private TextView accbalance;
    private User user;
    private String accbal;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet_balance);
        mauth=FirebaseAuth.getInstance();
        AddWalletmoneybtn =findViewById(R.id.AddWalletmoneybtn);
        editText= findViewById(R.id.AddAmtWallet);
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        numref=UserDatabaseReference.child(mauth.getUid()).child("Details");
        accbalance = findViewById(R.id.BalanceTxtWallet);
        AddWalletmoneybtn.setEnabled(false);
        initlistener();
        AddWalletmoneybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(accbal)<Integer.parseInt(editText.getText().toString()))
                Toast.makeText(getApplicationContext(),"Insufficient balance",Toast.LENGTH_SHORT).show();
                else
                {
                    int newbal = Integer.parseInt(accbal)-Integer.parseInt(editText.getText().toString());
                    String wallet;
                    Bundle extras = getIntent().getExtras();
                    if(extras!=null)
                    {
                        wallet= extras.getString("Wallet");
                        numref.child("accbal").setValue(String.valueOf(newbal));
                        if(wallet.equals("amazon"))
                        {
                            int bal = Integer.parseInt(user.getAmazon()) + Integer.parseInt(editText.getText().toString());
                            numref.child("amazon").setValue(String.valueOf(bal));
                        }
                        else
                            if (wallet.equals("paytm"))
                            {
                                int bal = Integer.parseInt(user.getPaytm()) + Integer.parseInt(editText.getText().toString());
                                numref.child("paytm").setValue(String.valueOf(bal));
                            }
                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                    }
                }
            }
        });
    }


    private void initlistener() {
        numref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                accbal = user.getAccbal();
                accbalance.setText("Z credits -  " + accbal);
                AddWalletmoneybtn.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
