package com.example.agsh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agsh.Models.InvitedUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Invites extends AppCompatActivity {
    private DatabaseReference UserDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private Button invite;
    private EditText number, amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites);
        firebaseAuth = FirebaseAuth.getInstance();
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid());
        invite = findViewById(R.id.Invitebtn);
        number = findViewById(R.id.InviteNumber);
        amt = findViewById(R.id.InviteAmt);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvitedUser invitedUser = new InvitedUser(number.getText().toString(), amt.getText().toString(),firebaseAuth.getUid());
                UserDatabaseReference.child("InvitedUser").setValue(invitedUser);
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invitemenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.urinvites:
                startActivity(new Intent(getApplicationContext(),YourInvites.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}