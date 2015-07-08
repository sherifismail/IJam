package com.Example.iJam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        TextView txtRating = (TextView) rowView.findViewById(R.id.txtRating);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        final trackInterface item = items.get(position);

        final String title = item.getTitle();
        final String likes = Integer.toString(item.getLikes());
        final String rating = Double.toString(item.getRating());
        final String author = (item.getUploader());

        txtTitle.setText(title);
        txtLikes.setText(likes);
        txtRating.setText(rating);

        txtRating.setEnabled(false);


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, context2);
                i.putExtra("title", title);
                i.putExtra("likes", likes);
                i.putExtra("rating", rating);
                i.putExtra("author", author);
                context.startActivity(i);
            }
        });
        return rowView;
    }
}