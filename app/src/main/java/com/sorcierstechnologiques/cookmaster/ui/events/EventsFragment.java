package com.sorcierstechnologiques.cookmaster.ui.events;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
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
    private TextView tv1;
    public ListView lv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(EventsViewModel.class);

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv1 = root.findViewById(R.id.tv1);
        lv = root.findViewById(R.id.lv);
        SharedPreferences settings = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = settings.getString("token", "");
        String userId = settings.getString("user_id", "");
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://cockmaster.fr/api/mobile/reservation/" + userId + "/" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray eventsArray = response.getJSONArray("events");
                            List<Events> eventsList = new ArrayList<>();
                            for (int i = 0; i < eventsArray.length(); i++) {
                                eventsList.addAll(getEvents(eventsArray.getJSONArray(i)));
                            }
                            if (eventsList.size() > 0) {
                                EventsAdapter eventsAdapter = new EventsAdapter(eventsList, requireContext());
                                lv.setAdapter(eventsAdapter);
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                        Events e = (Events) adapterView.getItemAtPosition(position);
                                        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View popupView = inflater.inflate(R.layout.popup_layout, null);
                                        TextView nameTextView = popupView.findViewById(R.id.name);
                                        TextView dateTextView = popupView.findViewById(R.id.date);
                                        TextView hourTextView = popupView.findViewById(R.id.hour);
                                        TextView descriptionTextView = popupView.findViewById(R.id.description);
                                        nameTextView.setText(e.getName());
                                        dateTextView.setText(e.getStart_date());
                                        hourTextView.setText(e.getStart_time() + " - " + e.getEnd_time());
                                        descriptionTextView.setText(e.getAddress());
                                        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                                        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                                        popupView.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                if (popupWindow.isShowing()) {
                                                    popupWindow.dismiss();
                                                }
                                                return true;
                                            }
                                        });
                                    }

                                });
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

    public List<Events> getEvents(JSONArray eventsArray) {
        List<Events> resultat = new ArrayList<>();
        try {
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject eventObject = eventsArray.getJSONObject(i);
                String name = eventObject.getString("name");
                String start_date = eventObject.getString("start_date");
                String start_time = eventObject.getString("start_time");
                String end_time = eventObject.getString("end_time");
                String address = eventObject.getString("description");
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