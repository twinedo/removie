package com.removie.ui.detail;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.removie.R;
import com.removie.api.service.ServiceConfig;
import com.removie.model.Credits;
import com.removie.utils.ImageSize;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
Created by twin on May 11, 2019
*/

public class CreditsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String ARG_MOVIE_DATA = "movie_data";

    private List<Credits> creditsList = new ArrayList<>();

    public void setMoviesCredits(List<Credits> credits) {
        this.creditsList.clear();
        this.creditsList.addAll(credits);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MovieCreditsHolder(parent);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Credits credits = creditsList.get(position);

        if (holder instanceof MovieCreditsHolder){
            MovieCreditsHolder movieCreditsHolder = (MovieCreditsHolder) holder;

            Uri uri = Uri.parse(ServiceConfig.BASE_IMAGE_URL + "/"+ImageSize.w500.getValue() + "/"+creditsList.get(position).profilePath);

            movieCreditsHolder.bind(uri.toString(), creditsList.get(position).name);
            ((MovieCreditsHolder) holder).mCredits = credits;
            ((MovieCreditsHolder) holder).mNameView.setText(credits.getName());
            ((MovieCreditsHolder) holder).mCharView.setText(credits.getCharacter());

        }
    }

    @Override
    public int getItemCount() {
        return creditsList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@LayoutRes int resId, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        }
    }

    public class MovieCreditsHolder extends Holder {
        public final View mView;

        @BindView(R.id.namaAsli)TextView mNameView;
        @BindView(R.id.namaChar)TextView mCharView;
        @BindView(R.id.castPic) ImageView castPic;

        public Credits mCredits;

        public MovieCreditsHolder(ViewGroup parent) {
            super(R.layout.list_item_credits, parent);
            ButterKnife.bind(this, itemView);

            mView=parent;
        }

        public void bind(String profileUrl, String movie) {
            Picasso.get().load(profileUrl).fit().into(castPic);
        }
    }
}
