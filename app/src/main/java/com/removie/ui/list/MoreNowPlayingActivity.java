package com.removie.ui.list;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.removie.R;

import butterknife.ButterKnife;

public class MoreNowPlayingActivity extends AppCompatActivity {

    public static final String FRAGMENT_MOVIES_TAG = "FragmentMoviesTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_nowplaying);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, MoreNowPlayingFragment.newInstance(),
                            FRAGMENT_MOVIES_TAG).commit();
        }

    }
}
