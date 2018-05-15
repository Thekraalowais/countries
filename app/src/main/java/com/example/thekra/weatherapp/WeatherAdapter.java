package com.example.thekra.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class WeatherAdapter extends ArrayAdapter<Weather> {
    private Context context;
    private List<Weather> weatherList;

    public WeatherAdapter(Context context, List<Weather> weatherList) {
        super(context, 0, weatherList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Weather current = getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            //when put the findviewbyid here the app will crash because getname() will take null object
//            viewHolder.name = convertView.findViewById(R.id.name);
//            viewHolder.desc = convertView.findViewById(R.id.desc);
//            viewHolder.image = convertView.findViewById(R.id.image);
//            convertView.setTag(viewHolder);
        }
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.desc = convertView.findViewById(R.id.desc);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.name.setText(current.getName());
            viewHolder.desc.setText(current.getDesc());
            Picasso.with(getContext())
                    .load(current.getImage()).into(viewHolder.image);
//        viewHolder.image.setImageResource(current.getImage());
            Log.i("image", "imae" + viewHolder.image);

        return convertView;
    }

    public class ViewHolder {
        TextView name;
        TextView desc;
        ImageView image;
    }
}
