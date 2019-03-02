package com.event.android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> id  =  new ArrayList<>();
    private ArrayList<String>mImages =  new ArrayList<>();
    private ArrayList<String>title = new ArrayList<>();
    private ArrayList<String>date =  new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context) {

    }

    public RecyclerViewAdapter(Context context, ArrayList<String > id, ArrayList<String> mImages, ArrayList<String> title, ArrayList<String> date) {
        this.id = id;
        this.mImages = mImages;
        this.title = title;
        this.date = date;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindVuewHolder :  called");

        Picasso.get()
                .load(mImages.get(i))
                .into(viewHolder.imageView);


        viewHolder.title.setText(title.get(i));
        viewHolder.time.setText(title.get(i));
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, title.get(i), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, time;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout =itemView.findViewById(R.id.parentLayout);
            imageView =  itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
        }
    }


}
