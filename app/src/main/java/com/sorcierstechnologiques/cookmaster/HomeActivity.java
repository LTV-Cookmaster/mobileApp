package com.sorcierstechnologiques.cookmaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {

    private Button btnquit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.btnquit = findViewById(R.id.btn_quit);

        this.btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(MainActivity.this,"Catch this ratio !",Toast.LENGTH_SHORT).show();*/
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Quitter ?")
                        .setMessage("Voulez vous quittez ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });
    }
}
