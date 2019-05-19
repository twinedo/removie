package com.removie.ui.list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.removie.R;

import butterknife.ButterKnife;

public class MorePopularActivity extends AppCompatActivity {

    public static final String FRAGMENT_MOVIES_TAG = "FragmentMoviesTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_popular);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, MorePopularFragment.newInstance(),
                            FRAGMENT_MOVIES_TAG).commit();
        }

    }
}
