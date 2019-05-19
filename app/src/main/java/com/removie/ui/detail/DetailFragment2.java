package com.removie.ui.detail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.facebook.share.model.ShareHashtag;
//import com.facebook.share.model.ShareLinkContent;
//import com.facebook.share.model.SharePhoto;
//import com.facebook.share.model.SharePhotoContent;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.removie.R;
import com.removie.api.service.MovieService;
import com.removie.api.service.RestApi;
import com.removie.api.service.ServiceConfig;
import com.removie.model.CreditsResponse;
import com.removie.model.Movie;
import com.removie.model.Trailer;
import com.removie.model.TrailersResponse;
import com.removie.utils.ImageSize;
import com.removie.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Created by twin on May 11, 2019
*/

public class DetailFragment2 extends Fragment {

    public final String TAG = DetailFragment2.class.getSimpleName();

    public static final String ARG_MOVIE_ID = "movie_id";
    public static final String ARG_MOVIE_DATA = "movie_data";
    //public static final String EXTRA_REVIEWS = "EXTRA_REVIEWS";
    private TrailersAdapter adapter;
    private CreditsAdapter mCreditsAdapter;

    @BindView(R.id.mov_poster)ImageView posters;
    @BindView(R.id.mov_date)TextView releaseDates;
    @BindView(R.id.mov_rate)TextView ratingAverageView;
    @BindView(R.id.mov_ratebar)RatingBar ratingBarView;
    @BindView(R.id.mov_overview)TextView overview;
    @BindView(R.id.recyclerview_trailer) RecyclerView recyclerViewTrailer;
    @BindView(R.id.recyclerview_credits) RecyclerView recyclerViewCredits;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.share) FloatingActionButton share;

    public static Fragment newInstance(long movieId, String movieData){
        Bundle args = new Bundle();
        args.putLong(ARG_MOVIE_ID, movieId);
        args.putString(ARG_MOVIE_DATA, movieData);

        DetailFragment2 detailFragment = new DetailFragment2();
        detailFragment.setArguments(args);

        return detailFragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        return inflater.inflate(R.layout.fragment_detail2,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Movie movie = new Gson().fromJson(getArguments().getString(ARG_MOVIE_DATA),
                        Movie.class);

        Uri uri = ImageUtils.movieUrl(ImageSize.original.getValue(),
                movie.backdropPath);
        Picasso.get().load(uri.toString()).fit().into(posters);

        releaseDates.setText(getText(R.string.release).toString() + movie.releaseDate);
        ratingAverageView.setText(String.valueOf(movie.voteAverage));
        ratingBarView.setRating(movie.voteAverage);
        overview.setText(movie.overview);
        (getActivity()).setTitle(movie.title);

        getTrailer(movie.id);
        getCredits(movie.id);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmapFromView(posters);
                try {
                    File file = new File(getActivity().getExternalCacheDir(), "removie.PNG");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.toString()));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "ReMovie App");
                    intent.putExtra(Intent.EXTRA_TEXT, movie.originalTitle+"\n" +
                            "Release Date : " + movie.releaseDate +
                            "\nOverview :...."+
                            "\n"+
                            "\n"+
                            "get Movies Overview only on :"+
                            "\nhttps://play.google.com/store/apps/details?id=" +getActivity().getPackageName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(Intent.createChooser(intent, "Share to:"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        }   else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }


    private void getTrailer(long id) {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "loading...");

        MovieService apiService =
                RestApi.getClient().create(MovieService.class);

        Call<TrailersResponse> call = apiService.trailerMovie(id, ServiceConfig.API_KEY);
        call.enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse>call, Response<TrailersResponse> response) {
                dialog.dismiss();

                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()){
                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(
                            getActivity(),
                            RecyclerView.VERTICAL,
                            false);

                    recyclerViewTrailer.setLayoutManager(gridLayoutManager);
                    recyclerViewTrailer.setHasFixedSize(true);

                    adapter = new TrailersAdapter();
                    adapter.setOnClickListener(new TrailersAdapter.OnClickListener() {
                        @Override
                        public void onItemClickListener(String key,
                                                        Trailer movie) {
                            Intent appIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("vnd.youtube:" + key));
                            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://www.youtube.com/watch?v=" + key));
                            try {
                                startActivity(appIntent);
                            } catch (ActivityNotFoundException ex) {
                                startActivity(webIntent);
                            }
                        }
                    });

                    recyclerViewTrailer.setAdapter(adapter);
                    adapter.setMoviesData(response.body().results);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Symptom Tidak Ada");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<TrailersResponse>call, Throwable t) {
                dialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void getCredits(long id) {
        MovieService apiService =
                RestApi.getClient().create(MovieService.class);
        Call<CreditsResponse> call = apiService.creditsMovie(id, ServiceConfig.API_KEY);
        call.enqueue(new Callback<CreditsResponse>() {
            @Override
            public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().cast));

                if (response.code() == 200 && response.isSuccessful()){
                    mCreditsAdapter = new CreditsAdapter();
                    LinearLayoutManager linear2 = new LinearLayoutManager(
                            getActivity(),
                            RecyclerView.HORIZONTAL,
                            false);

                    recyclerViewCredits.setLayoutManager(linear2);
                    recyclerViewCredits.setHasFixedSize(true);

                    recyclerViewCredits.setAdapter(mCreditsAdapter);
                    mCreditsAdapter.setMoviesCredits(response.body().cast);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error" );
                    alertDialog.setMessage("Symptom Tidak Ada" );
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<CreditsResponse> call, Throwable t) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error" );
                alertDialog.setMessage("Kesalahan Jaringan" + t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });
    }
}
