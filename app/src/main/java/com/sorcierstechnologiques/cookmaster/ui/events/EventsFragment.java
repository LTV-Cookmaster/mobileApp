package com.sorcierstechnologiques.cookmaster.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sorcierstechnologiques.cookmaster.R;
import com.sorcierstechnologiques.cookmaster.databinding.FragmentEventsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    private FragmentEventsBinding binding;
    private TextView tv1;
    public ListView lv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(EventsViewModel.class);

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv1 = (TextView) root.findViewById(R.id.tv1);
        lv = root.findViewById(R.id.lv);

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://pokeapi.co/api/v2/pokemon/pikachu";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Traitement des données JSON ici
                            // Par exemple, vous pouvez extraire des valeurs spécifiques du JSON
                            String name = response.getString("name");
                            lv = root.findViewById(R.id.lv);
                            EventsAdapter eadap = new EventsAdapter(getEvents(response), requireContext());
                            lv.setAdapter(eadap);
                            // ...

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gestion des erreurs de la requête
                        // Affichez un message d'erreur approprié ou effectuez d'autres actions
                        error.printStackTrace();
                        tv1.setText(error.getMessage());
                    }
                });

// Ajout de la requête à la file d'attente
        queue.add(jsonObjectRequest);





        final TextView textView = binding.textEvents;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public List<Events> getEvents(JSONObject response) {
        List<Events> resultat = new ArrayList<>();
        try {
            JSONArray eventsArray = response.getJSONArray("events"); // Supposons que les événements soient contenus dans un tableau JSON appelé "events"
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject eventObject = eventsArray.getJSONObject(i);
                String name = eventObject.getString("name");
                String start_date = eventObject.getString("start_date");
                String start_time = eventObject.getString("start_time");
                String end_time = eventObject.getString("end_time");
                String address = eventObject.getString("address");
                Events event = new Events(name, start_date, start_time, end_time, address);
                resultat.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultat;
    }

}