package com.example.richmond.uspotmobileapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richmond.uspotmobileapplication.R;
import com.example.richmond.uspotmobileapplication.Spot;
import com.example.richmond.uspotmobileapplication.activities.SingleSpotView;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Richmond on 02/03/2016.
 */
public class AdapterSpotView extends RecyclerView.Adapter<AdapterSpotView.ViewHolderSpot> {

    private ArrayList<Spot> spotList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private static Context context;

    public AdapterSpotView(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setSpotList(ArrayList<Spot> spotList) {
        this.spotList = spotList;
        notifyItemRangeChanged(0, spotList.size());
    }


    @Override
    public ViewHolderSpot onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.spot_view_recycler, parent, false);
        ViewHolderSpot viewHolder = new ViewHolderSpot(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderSpot holder, int position) {
        //need access to a data structure
        Spot currentSpot = spotList.get(position);
        holder.spotName.setText(currentSpot.getSpotName());
        //holder.spotRating.setText(currentSpot.getSpotRating());
        //holder.spotThumbnail.setText(currentSpot.getSpotImage());
        // holder.spotCreationDate.setText(currentSpot.getSpot());

    }

    @Override
    public int getItemCount() {
        return spotList.size();
    }

    static class ViewHolderSpot extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView spotThumbnail;
        private TextView spotName;
        private TextView spotCreationDate;
        private RatingBar spotRating;

        public ViewHolderSpot(View itemView) {
            super(itemView);

            spotThumbnail = (ImageView) itemView.findViewById(R.id.spotThumbnail);
            spotName = (TextView) itemView.findViewById(R.id.spotNameRec);
            spotCreationDate = (TextView) itemView.findViewById(R.id.spotCreationDate);
            spotRating = (RatingBar) itemView.findViewById(R.id.spotRatingRec);

            //setting the onclick listener for each item on the recycler view
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Naviagate to single spot view", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent( context, SingleSpotView.class));


        }
    }
}
