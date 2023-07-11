package com.sorcierstechnologiques.cookmaster.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
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
    private EditText tv1;
    public ListView lv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(EventsViewModel.class);

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv1 = root.findViewById(R.id.tv1);
        lv = root.findViewById(R.id.lv);

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://cockmaster.fr/api/mobile/reservation/ee475781-16ac-3e98-96ef-afb9285da20a";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray eventsArray = response.getJSONArray(0).getJSONArray(0).getJSONArray(0);
                            if (eventsArray.length() > 0) {
                                List<Events> eventsList = getEvents(eventsArray);
                                EventsAdapter eventsAdapter = new EventsAdapter(eventsList, requireContext());
                                lv.setAdapter(eventsAdapter);
                            } else {
                                tv1.setText("Vous n'avez aucune réservation");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            tv1.setText(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        tv1.setText(error.getMessage());
                    }
                });


// Ajout de la requête à la file d'attente
        queue.add(jsonArrayRequest);



        final TextView textView = binding.textEvents;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public List<Events> getEvents(JSONArray eventsArray) {
        List<Events> resultat = new ArrayList<>();
        try {
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
            tv1.setText(e.getMessage());
        }
        return resultat;
    }




}