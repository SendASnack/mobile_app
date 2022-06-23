package com.example.sendasnack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity{
    String msg = "Android : ";
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView userTv = findViewById(R.id.username_input);
                TextView passwordTv = findViewById(R.id.password_input);
                String user = userTv.getText().toString();
                String password = passwordTv.getText().toString();

                if (user.equals("") || password.equals("")) {
                    Toast.makeText(getBaseContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else
                    signIn(user, password);
            }
        });

    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent i=new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("loggedIn","true");
                            finish();
                            Toast.makeText(getBaseContext(), "Success Login" , Toast.LENGTH_SHORT ).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }


    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent i=new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("loggedIn","true");
            finish();
            Toast.makeText(getBaseContext(), "Success Login" , Toast.LENGTH_SHORT ).show();
        }
        else {
            Toast.makeText(getBaseContext(), "Incorrect Credentials", Toast.LENGTH_SHORT ).show();
        }
    }

}
