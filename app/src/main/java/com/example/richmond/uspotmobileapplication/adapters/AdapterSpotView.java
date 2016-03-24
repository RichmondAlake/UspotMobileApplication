package com.example.richmond.uspotmobileapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.richmond.uspotmobileapplication.R;

import org.w3c.dom.Text;

/**
 * Created by Richmond on 02/03/2016.
 */
public class AdapterSpotView extends RecyclerView.Adapter<AdapterSpotView.ViewHolderSpot>{

    private LayoutInflater layoutInflater;
    public AdapterSpotView(Context context){
        layoutInflater = LayoutInflater.from(context);
    }




    @Override
    public ViewHolderSpot onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  layoutInflater.inflate(R.layout.spot_view_recycler,parent,false);
        ViewHolderSpot viewHolder = new ViewHolderSpot(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderSpot holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolderSpot extends RecyclerView.ViewHolder{
        private ImageView spotThumbnail;
        private TextView spotName;
        private TextView spotCreationDate;
        private RatingBar spotRating;

        public ViewHolderSpot(View itemView){
            super (itemView);

            spotThumbnail = (ImageView) itemView.findViewById(R.id.spotThumbnail);
            spotName = (TextView) itemView.findViewById(R.id.spotNameRec);
            spotCreationDate = (TextView) itemView.findViewById(R.id.spotCreationDate);
            spotRating = (RatingBar) itemView.findViewById(R.id.spotRatingRec);

        }
    }
}
