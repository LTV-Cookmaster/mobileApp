package com.sorcierstechnologiques.cookmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;

    private EditText email;
    private EditText password;
    private EditText tv1;

    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        errorText = findViewById(R.id.textError);
        tv1 = findViewById(R.id.tv1);

        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("users", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = settings.edit();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                String url = "https://cockmaster.fr/api/login";
                // Create body request
                JSONObject jsonBody = new JSONObject();
                String requestBody;
                try {
                    jsonBody.put("email", emailText);
                    jsonBody.put("password", passwordText);
                    requestBody = jsonBody.toString();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.equals("false")) {
                                Toast.makeText(LoginActivity.this, "Identifiant ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject jsonObject = new JSONObject(response);
                                edit.putString("token", jsonObject.getString("token"));
                                edit.putString("user_id", jsonObject.getString("user_id"));
                                edit.apply();
                                Intent logged = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(logged);
                                finish();
                            }
                        }catch (JSONException e) { e.printStackTrace(); }
                    }
                }, error -> {
                    errorText.setText("Email ou mot de passe incorrect");
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() {
                        return requestBody.getBytes();
                    }
                };
                queue.add(stringRequest);


/*                if(email.getText().toString().equals("ratio") && password.getText().toString().equals("ratio")){
                    Intent logged = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(logged);
                } else {
                    error.setText("Email ou mot de passe incorrect");
                }*/

            }
        });

    }
}