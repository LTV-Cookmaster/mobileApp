package com.sorcierstechnologiques.cookmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EtudiantAdapter extends BaseAdapter {

    private List<Etudiant> etudiants;
    private Context context;

    public EtudiantAdapter(List<Etudiant> etudiants, Context context) {
        this.etudiants = etudiants;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.etudiants.size();
    }

    @Override
    public Object getItem(int position) {
        return this.etudiants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.row, null);
        }
        TextView nom = convertView.findViewById(R.id.tv_nom);
        TextView prenom = convertView.findViewById(R.id.tv_prenom);

        Etudiant currentEtudiant = (Etudiant) getItem(position);

        nom.setText(currentEtudiant.getNom());
        prenom.setText(currentEtudiant.getPrenom());

        return convertView;
    }
}
