package com.removie.ui.detail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.removie.R;

import butterknife.ButterKnife;

/*
Created by twin on May 11, 2019
*/

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        long id = getIntent().getLongExtra("movie_id", -1);
        String movieData = getIntent().getStringExtra("movie_data");

        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                DetailFragment2
                        .newInstance(id,movieData)).commit();
    }

}
