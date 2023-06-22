package com.sorcierstechnologiques.cookmaster;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        this.listView = findViewById(R.id.lv_etudiants);
        EtudiantAdapter adapter = new EtudiantAdapter(this.getClasseEtudiant(), StudentActivity.this);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Etudiant e = (Etudiant) adapterView.getItemAtPosition(position);
                Toast.makeText(StudentActivity.this, Integer.toString(e.getAge()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Etudiant> getClasseEtudiant() {
        List<Etudiant> resultat = new ArrayList<Etudiant>();
        resultat.add(new Etudiant("Doe", "John", 20));
        resultat.add(new Etudiant("Feur", "Jane", 19));
        resultat.add(new Etudiant("Ratio", "Jack", 21));
        return resultat;
    }
}
