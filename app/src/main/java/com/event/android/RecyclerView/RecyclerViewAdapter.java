package com.event.android.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

import com.event.android.R;
import com.event.android.UI.ShowEventActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> id  =  new ArrayList<>();
    private ArrayList<String>mImages =  new ArrayList<>();
    private ArrayList<String>title = new ArrayList<>();
    private ArrayList<String>date =  new ArrayList<>();
    private Context context;
    String token ="";

    public RecyclerViewAdapter(Context context, ArrayList<String> id, ArrayList<String> mImages, ArrayList<String> title, ArrayList<String> date, String token) {
        this.id = id;
        this.mImages = mImages;
        this.title = title;
        this.date = date;
        this.context = context;
        this.token  =  token;
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
        viewHolder.time.setText(date.get(i));

        eventClicked(viewHolder,i);

    }


    public void eventClicked(ViewHolder viewHolder, final int i){
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, ShowEventActivity.class);
                intent.putExtra("eventID",id.get(i));
                //intent.putExtra("Speaker List", speakerID);
                intent.putExtra("token",token);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return title.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.parentLayout)
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            /*
            parentLayout =itemView.findViewById(R.id.parentLayout);
            imageView =  itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
        }*/
        }

    }}
