package com.text.textr01.locationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AdminUpdatePassword extends AppCompatActivity {

    EditText AdminUsername,AdminPassword;
    Button AdminBtn;
    ProgressDialog loadingBar;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_password);

        AdminUsername=(EditText)findViewById(R.id.emailresetpass);

        AdminBtn=(Button)findViewById(R.id.resetpass);
        loadingBar=new ProgressDialog(this);

        AdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpass();
            }
        });

    }

    private void resetpass() {
        Email = AdminUsername.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(TextUtils.isEmpty(Email)){
            Toast.makeText(AdminUpdatePassword.this,"Enter the correct Email", Toast.LENGTH_SHORT).show();
        }else{
            auth.sendPasswordResetEmail( String.valueOf( Email ) )
                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText( AdminUpdatePassword.this,"Password reset Link is successfully Sent to your Email address",Toast.LENGTH_SHORT ).show();
                            }else{
                                Toast.makeText(AdminUpdatePassword.this,"User Not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } );
        }
    }

}
