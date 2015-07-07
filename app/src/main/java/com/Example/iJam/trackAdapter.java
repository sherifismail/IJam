package com.Example.iJam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Khodary on 7/5/15.
 */
public class trackAdapter extends ArrayAdapter<trackInterface> {
    private final Activity context;
    private final Class<?> context2;
    ArrayList<trackInterface> items;

    public static HashMap<String, Bitmap> myImages = new HashMap<String, Bitmap>();


    public trackAdapter(Activity context, Class<?> context2, ArrayList<trackInterface> items) {
        super(context, R.layout.track_row_layout, items);
        this.items = items;
        this.context = context;
        this.context2 = context2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.track_row_layout, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        TextView txtLikes = (TextView) rowView.findViewById(R.id.txtLikes);
        RatingBar rateBar = (RatingBar) rowView.findViewById(R.id.ratingBar);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        final trackInterface item = items.get(position);

        txtTitle.setText(item.getTitle());
        txtLikes.setText(Float.toString(item.getLikes()));
        rateBar.setRating((float) item.getRating());
        rateBar.setEnabled(false);


        rowView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, context2);
                context.startActivity(i);
            }
        });
        return rowView;
    }
}