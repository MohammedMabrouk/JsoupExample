package com.mabrouk.jsoupexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SlideViewHolder> {

    List<String> imagesUrlList;
    List<String> imagesTitlesList;
    Context context;


    public SliderAdapter(List<String> imagesUrlList, List<String> imagesTitlesList, Context context) {
        this.imagesUrlList = imagesUrlList;
        this.imagesTitlesList = imagesTitlesList;
        this.context = context;
    }

    @Override
    public SlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_slide, parent, false);
        return new SliderAdapter.SlideViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(SlideViewHolder holder, int position) {
        //set title
        holder.programTitleTextView.setText(imagesTitlesList.get(position));
//        holder.rentalNumber.setText(String.valueOf(rentalsList.get(position).getRANumber()));

        if (imagesUrlList.get(position) != null && !imagesUrlList.get(position).isEmpty()) {
                Picasso.get().load(imagesUrlList.get(position))
                        .error(context.getResources().getDrawable(R.drawable.nat_geo_full_en))
                        .placeholder(context.getResources().getDrawable(R.drawable.nat_geo_full_en))
                        .into(holder.programImageView);
        }

        holder.programTitle = imagesTitlesList.get(position);
        holder.programImageUrl = imagesUrlList.get(position);
    }

    @Override
    public int getItemCount() {
        return imagesTitlesList.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder {

        String programImageUrl;
        String programTitle;

        TextView programTitleTextView;
        ImageView programImageView;

        SlideViewHolder(View itemView, final Context context) {
            super(itemView);

            programImageView = itemView.findViewById(R.id.program_image);
            programTitleTextView = itemView.findViewById(R.id.program_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, programTitle, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
