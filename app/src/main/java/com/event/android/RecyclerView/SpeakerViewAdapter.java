package com.event.android.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.event.android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpeakerViewAdapter extends RecyclerView.Adapter<SpeakerViewAdapter.ViewHolder>{

    private ArrayList<String> arrayName = new ArrayList<>();
    private ArrayList<String> arrayBio = new ArrayList<>();
    private ArrayList<String> arrayImageUrl = new ArrayList<>();
    private Context context;

    public SpeakerViewAdapter(ArrayList<String> speakerName, ArrayList<String> speakerBio, ArrayList<String> speakerImageUrl, Context context) {
        this.arrayName = speakerName;
        this.arrayBio = speakerBio;
        this.arrayImageUrl = speakerImageUrl;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.speaker_list,parent,false);
        ViewHolder viewHolder =  new ViewHolder(view);
        Toast.makeText(context, "In create viewHolder", Toast.LENGTH_SHORT).show();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        Picasso.get()
                .load(arrayImageUrl.get(i)).into(viewHolder.speakerImage);

        viewHolder.speakerName.setText(arrayName.get(i));
        viewHolder.speakerBio.setText(arrayBio.get(i));

    }


    @Override
    public int getItemCount() {
        return arrayName.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.speaker_name)
        TextView speakerName;
        @BindView(R.id.aboutSpeaker)
        TextView speakerBio;
        @BindView((R.id.speaker_pic))
        ImageView speakerImage;
        @BindView(R.id.speakerLayout)
        RelativeLayout layout;
        /*
       ImageView speakerImage;
       TextView speakerBio,speakerName;
        RelativeLayout layout;*/
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

/*
                    layout =  itemView.findViewById(R.id.speakerLayout);
            speakerImage =  itemView.findViewById(R.id.speaker_pic);
            speakerName = itemView.findViewById(R.id.speaker_name);
            speakerBio = itemView.findViewById(R.id.aboutSpeaker);*/



        }
    }
}
