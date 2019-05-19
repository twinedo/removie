package com.removie.ui.list;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreNowPlayingFragment extends Fragment {
    private final String TAG = MoreNowPlayingFragment.class.getSimpleName();

    @BindView(R.id.recyclerview1) RecyclerView recyclerView1;
    @BindView(R.id.recyclerview2) RecyclerView recyclerView2;
    @BindView(R.id.recyclerview3) RecyclerView recyclerView3;
    @BindView(R.id.xtoolbar) Toolbar xtoolbar;

    private MoviesAdapter adapter1, adapter2, adapter3;

    public static Fragment newInstance(){
        return new MoreNowPlayingFragment();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_nowplaying, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(xtoolbar);

        xtoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        xtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Now Playing Movies");

        loadNowPlaying1();
        loadNowPlaying2();
        loadNowPlaying3();
    }

    private void openMovieDetail(long id, Movie movie) {
        String movieJson = new Gson().toJson(movie);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movie_id", id);
        intent.putExtra("movie_data", movieJson);
        getActivity().startActivity(intent);
    }


    //load data NowPlaying1
    public void loadNowPlaying1() {
        MovieService apiService =
                RestApi.getClient().create(MovieService.class);

        Call<MovieResponse> call = apiService.getNowPlayingMovies(ServiceConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()) {
                    adapter1 = new MoviesAdapter();
                    adapter1.setOnClickListener(new MoviesAdapter.OnClickListener() {
                        @Override
                        public void onItemClickListener(long id, Movie movie) {
                            openMovieDetail(id, movie);
                        }
                    });
                    GridLayoutManager linear1 = new GridLayoutManager(
                            getActivity(), 4,
                            RecyclerView.VERTICAL,false);
                    recyclerView1.setLayoutManager(linear1);
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setAdapter(adapter1);
                    adapter1.setMoviesData(response.body().results);

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
    //--ini batas retrieve data NowPlaying1

    //load data NowPlaying2
    public void loadNowPlaying2() {
        MovieService apiService =
                RestApi.getClient().create(MovieService.class);

        Call<MovieResponse> call2 = apiService.getNowPlayingMovies2(ServiceConfig.API_KEY);
        call2.enqueue(new Callback<MovieResponse>() {
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
                    GridLayoutManager linear2 = new GridLayoutManager(
                            getActivity(), 4,
                            RecyclerView.VERTICAL,false);
                    recyclerView2.setLayoutManager(linear2);
                    recyclerView2.setHasFixedSize(true);
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
    //--ini batas retrieve data NowPlaying2

    //load data NowPlaying3
    public void loadNowPlaying3() {
        MovieService apiService =
                RestApi.getClient().create(MovieService.class);

        Call<MovieResponse> call3 = apiService.getNowPlayingMovies3(ServiceConfig.API_KEY);
        call3.enqueue(new Callback<MovieResponse>() {
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
                    GridLayoutManager linear3 = new GridLayoutManager(
                            getActivity(), 4,
                            RecyclerView.VERTICAL,false);
                    recyclerView3.setLayoutManager(linear3);
                    recyclerView3.setHasFixedSize(true);
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
    //--ini batas retrieve data NowPlaying3

}
