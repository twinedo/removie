package com.removie.ui.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.removie.R;
import com.removie.api.service.MovieService;
import com.removie.api.service.RestApi;
import com.removie.api.service.ServiceConfig;
import com.removie.model.Movie;
import com.removie.model.MovieResponse;
import com.removie.ui.detail.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Created by twin on May 11, 2019
*/

public class MainFragment extends Fragment {
    private final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.recyclerview2) RecyclerView recyclerView2;
    @BindView(R.id.recyclerview3) RecyclerView recyclerView3;
    @BindView(R.id.recyclerview4) RecyclerView recyclerView4;
    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.nestedScrollView) NestedScrollView nestedScrollView;
    @BindView(R.id.text_cat) TextView text_cat;
    @BindView(R.id.text_cat2) TextView text_cat2;
    @BindView(R.id.text_cat3) TextView text_cat3;
    @BindView(R.id.text_cat4) TextView text_cat4;
    @BindView(R.id.text_more1) TextView text_more1;
    @BindView(R.id.text_more2) TextView text_more2;
    @BindView(R.id.text_more3) TextView text_more3;

    private MoviesAdapter adapter, adapter2, adapter3, adapter4;
    private Animation bttone, bttwo, btthree;

    public static Fragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //slideup = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_animation);
        bttone = AnimationUtils.loadAnimation(getContext(), R.anim.bttone);
        bttwo = AnimationUtils.loadAnimation(getContext(), R.anim.bttwo);
        btthree = AnimationUtils.loadAnimation(getContext(), R.anim.btthree);
        imageView.startAnimation(bttone);
        text_cat.startAnimation(bttwo);
        text_cat2.startAnimation(bttwo);
        text_cat3.startAnimation(bttwo);
        text_cat4.startAnimation(bttwo);
        text_more1.startAnimation(bttwo);
        text_more2.startAnimation(bttwo);
        text_more3.startAnimation(bttwo);

        recyclerView.startAnimation(btthree);
        recyclerView2.startAnimation(btthree);
        recyclerView3.startAnimation(btthree);
        recyclerView4.startAnimation(btthree);

        AnimationDrawable animationDrawable = (AnimationDrawable)nestedScrollView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.setOneShot(true);
        animationDrawable.start();

        loadPopular();
        loadTopRated();
        loadNowPlaying();
        loadUpcoming();

        text_more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent more1 = new Intent(getContext(), MorePopularActivity.class);
                startActivity(more1);
            }
        });
        text_more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent more2 = new Intent(getContext(), MoreTopRatedActivity.class);
                startActivity(more2);
            }
        });
        text_more3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent more3 = new Intent(getContext(), MoreNowPlayingActivity.class);
                startActivity(more3);
            }
        });

    }

    private void openMovieDetail(long id, Movie movie) {
        String movieJson = new Gson().toJson(movie);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movie_id", id);
        intent.putExtra("movie_data", movieJson);
        getActivity().startActivity(intent);
    }

    //load data Popular
    public void loadPopular() {
        MovieService apiService =
                RestApi.getClient().create(MovieService.class);

        Call<MovieResponse> call = apiService.getPopularMovies(ServiceConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()) {
                    adapter = new MoviesAdapter();
                    adapter.setOnClickListener(new MoviesAdapter.OnClickListener() {
                        @Override
                        public void onItemClickListener(long id, Movie movie) {
                            openMovieDetail(id, movie);
                        }
                    });
                    LinearLayoutManager linear1 = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(linear1);
                    recyclerView.setAdapter(adapter);
                    adapter.setMoviesData(response.body().results);

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Symptopm Tidak Ada");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //loadingView.setVisibility(View.GONE);
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan " + t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
        });
    }
    //--ini batas retrieve data Popular

    //dibawah ini TopRated
    public void loadTopRated() {
        MovieService apiService = RestApi.getClient().create(MovieService.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(ServiceConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()) {
                    adapter2 = new MoviesAdapter();
                    adapter2.setOnClickListener(new MoviesAdapter.OnClickListener() {
                        @Override
                        public void onItemClickListener(long id, Movie movie) {
                            openMovieDetail(id, movie);
                        }
                    });
                    LinearLayoutManager linear2 = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL,false);
                    recyclerView2.setLayoutManager(linear2);
                    recyclerView2.setAdapter(adapter2);
                    adapter2.setMoviesData(response.body().results);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Symptopm Tidak Ada");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //loadingView.setVisibility(View.GONE);

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan " + t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
        });
    }
    //batas load Top Rated

    //dibawah ini load Now Playing
    public void loadNowPlaying() {
        MovieService apiService = RestApi.getClient().create(MovieService.class);

        Call<MovieResponse> call = apiService.getNowPlayingMovies(ServiceConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()) {
                    adapter3 = new MoviesAdapter();
                    adapter3.setOnClickListener(new MoviesAdapter.OnClickListener() {
                        @Override
                        public void onItemClickListener(long id, Movie movie) {
                            openMovieDetail(id, movie);
                        }
                    });
                    LinearLayoutManager linear3 = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL,false);
                    recyclerView3.setLayoutManager(linear3);
                    recyclerView3.setAdapter(adapter3);
                    adapter3.setMoviesData(response.body().results);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Symptopm Tidak Ada");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan " + t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
        });
    }
    //batas load Now Playing

    //dibawah ini load Upcoming
    public void loadUpcoming() {
        MovieService apiService = RestApi.getClient().create(MovieService.class);

        Call<MovieResponse> call = apiService.getUpcomingMovies(ServiceConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()) {
                    adapter4 = new MoviesAdapter();
                    adapter4.setOnClickListener(new MoviesAdapter.OnClickListener() {
                        @Override
                        public void onItemClickListener(long id, Movie movie) {
                            openMovieDetail(id, movie);
                        }
                    });
                    LinearLayoutManager linear4 = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL,false);
                    recyclerView4.setLayoutManager(linear4);
                    recyclerView4.setAdapter(adapter4);
                    adapter4.setMoviesData(response.body().results);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Symptopm Tidak Ada");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //loadingView.setVisibility(View.GONE);

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan " + t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
        });
    }
    //batas load Upcoming
}
