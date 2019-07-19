package com.example.agsh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agsh.Models.InvitedUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Invites extends AppCompatActivity {
    private static final int RESULT_PICK_CONTACT = 135;
    private DatabaseReference UserDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private Button invite;//,phonebook;
    private EditText number, amt,number0,number1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites);
        firebaseAuth = FirebaseAuth.getInstance();
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid());
        invite = findViewById(R.id.Invitebtn);
        number = findViewById(R.id.InviteNumber);
        number0= findViewById(R.id.InviteNumber0);
        number1= findViewById(R.id.InviteNumber1);

        amt = findViewById(R.id.InviteAmt);
        //phonebook= findViewById(R.id.Phonebook);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvitedUser invitedUser = new InvitedUser(number.getText().toString(), amt.getText().toString(),firebaseAuth.getUid());
                UserDatabaseReference.child("InvitedUser").setValue(invitedUser);
            }
        });
//        phonebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int PICK_CONTACT = 2015;
//                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//                startActivityForResult(i, PICK_CONTACT);
//            }
//        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null;
                        String name = null;

                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        phoneNo = cursor.getString(phoneIndex);
                        name = cursor.getString(nameIndex);
                        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                        Log.e("Name/Contact number is",name+","+phoneNo);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e("Failed", "Not able to pick contact");
        }
    }
    }