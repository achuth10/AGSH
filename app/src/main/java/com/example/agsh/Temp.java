package com.example.agsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsh.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import org.json.JSONObject;

public class Temp extends AppCompatActivity {
    private static final String TAG = "";
    private String id;
    private User user;
    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private DatabaseReference UserDatabaseReference, numref;
    private String name,amt,email,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth=FirebaseAuth.getInstance();
        numref = UserDatabaseReference.child(firebaseAuth.getUid()).child("Details");
        numref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                name = user.getName();
                email= user.getEmail();
                number=user.getPhonenumber();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            if (extras.getString("InviteAmt") != null) {
                amt = extras.getString("InviteAmt");
                amt+=".00";
                numref = UserDatabaseReference.child(extras.getString("Uuid")).child("Details");
            } else if (extras.getString("AddAmt") != null) {

                amt = extras.getString("AddAmt");

            }


        }
        else name = "John Doe";
        Checkout.preload(getApplicationContext());

        startPayment();
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", "Add Z credits");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://firebasestorage.googleapis.com/v0/b/agsh-a7175.appspot.com/o/logo.png?alt=media&token=14b7aa15-869e-4907-a47b-e55a0298e0a3");
            options.put("currency", "INR");
            options.put("amount", amt);

            JSONObject preFill = new JSONObject();
           preFill.put("email", email);
           preFill.put("contact", number);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}