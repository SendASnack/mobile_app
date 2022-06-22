package com.example.sendasnack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{
    String msg = "Android : ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                TextView userTv = findViewById(R.id.username_input);
                TextView passwordTv = findViewById(R.id.password_input);
                String user = userTv.getText().toString();
                String password = passwordTv.getText().toString();

                if (user.equals("pedro") && password.equals("pedro123")) {
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("loggedIn","true");
                    finish();
                    Toast.makeText(getBaseContext(), "Success Login" , Toast.LENGTH_SHORT ).show();
                } else if (user.equals("") || password.equals("")) {
                    Toast.makeText(getBaseContext(), "Fill all fields", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText(getBaseContext(), "Incorrect Credentials", Toast.LENGTH_SHORT ).show();
                }
            }
        });
        Log.d(msg, "The onCreate() event");
    }

}
