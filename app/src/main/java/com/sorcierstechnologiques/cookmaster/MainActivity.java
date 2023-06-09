package com.sorcierstechnologiques.cookmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button registerButton;
    private Button loginButton;

    private EditText email;
    private EditText password;

    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        error = findViewById(R.id.textError);

        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });

        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("ratio") && password.getText().toString().equals("ratio")){
                    Intent logged = new Intent(MainActivity.this, HomeActivity.class);
                    MainActivity.this.startActivity(logged);
                } else {
                    error.setText("Email ou mot de passe incorrect");
                }

            }
        });

    }
}