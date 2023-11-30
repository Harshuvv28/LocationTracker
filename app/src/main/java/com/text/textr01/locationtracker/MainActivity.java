package com.text.textr01.locationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText Username,Password;
    Button LoginButton,tr;
    String UsernameText,PasswordText;
    String adminusername,adminpassword;

    String Etphone,Etpass,Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username=(EditText)findViewById(R.id.username);
        Password=(EditText)findViewById(R.id.password);
        LoginButton=(Button)findViewById(R.id.loginbutton);
        tr=(Button)findViewById(R.id.ptbt);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignInActivity.class));
            }
        });



        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        String s1 = sh.getString("active", "");
        String s2 = sh.getString("phone", "");

        if(s1.equals("admin"))
        {
           Toast.makeText(MainActivity.this,"Please login as Parent",Toast.LENGTH_SHORT).show();
        }
        if(s1.equals("user"))
        {
            Intent i=new Intent(MainActivity.this,UserActivty.class);
            i.putExtra("phone",s2);
            startActivity(i);
        }

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsernameText=Username.getText().toString();
                PasswordText=Password.getText().toString();



                    if (UsernameText.matches("[0-9]+") && UsernameText.length() == 10)
                    {
                        final DatabaseReference RootRef2;
                        RootRef2= FirebaseDatabase.getInstance().getReference().child("Users");

                        RootRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snap:dataSnapshot.getChildren()) {


                                        if(snap.child(UsernameText).exists())
                                        {
                                            Etphone=snap.child(UsernameText).child("phone").getValue().toString();
                                            Etpass=snap.child(UsernameText).child("password").getValue().toString();
                                            Status=snap.child(UsernameText).child("status").getValue().toString();

                                            Login(Etphone,Etpass,Status);
                                        }
                                        else
                                        {
                                            Toast.makeText(MainActivity.this, "user with this phone number not exist..", Toast.LENGTH_SHORT).show();
                                        }
                                    }




                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Please enter correct phone number..", Toast.LENGTH_SHORT).show();
                    }


            }
        });
    }

    private void Login(String etphone, String etpass,String status) {
        if(UsernameText.equals(etphone))
        {
            if(PasswordText.equals(etpass))
            {
                if(status.equals("Active"))
                {
                    SharedPreferences sharedPreferences
                            = getSharedPreferences("MySharedPref",
                            MODE_PRIVATE);

                    SharedPreferences.Editor myEdit
                            = sharedPreferences.edit();

                    myEdit.putString(
                            "active",
                            "user");
                    myEdit.putString(
                            "phone",
                            etphone);
                    myEdit.commit();

                    Intent i=new Intent(MainActivity.this,UserActivty.class);
                    i.putExtra("phone",etphone);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(this, "Your Account is Inactive, please contact admin for activation", Toast.LENGTH_SHORT).show();
                }

            }
            else

            {
                Toast.makeText(MainActivity.this, "Please enter correct password..", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {

        Intent i=new Intent(MainActivity.this,MainAct2.class);
        startActivity(i);

    }
}
