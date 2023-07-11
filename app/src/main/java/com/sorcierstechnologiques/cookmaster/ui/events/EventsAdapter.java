package com.sorcierstechnologiques.cookmaster.ui.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sorcierstechnologiques.cookmaster.R;

import java.util.List;

public class EventsAdapter extends BaseAdapter {
    private List<Events> event;
    private Context context;

    public EventsAdapter(List<Events> event, Context context) {
        this.event = event;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.event.size();
    }

    @Override
    public Object getItem(int i) {
        return this.event.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.row, null);
        }
        TextView tv_n = convertView.findViewById(R.id.tv_name);
        TextView tv_a = convertView.findViewById(R.id.tv_date);

        Events current = (Events)getItem(position);

        tv_n.setText(current.getName());
        tv_a.setText(current.getAddress());

        return convertView;
    }
}
