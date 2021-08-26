package com.example.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TwoActivity extends AppCompatActivity implements TwoContract.View {

    private TwoContract.Presenter presenter;

    TwoAdapter twoAdapter;

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        recyclerView = findViewById(R.id.two_id);

        presenter = new TwoPresenter(this);
        presenter.init(this);
    }

    @Override
    public void showCats(List<CatData> catData) {
        twoAdapter = new TwoAdapter(this, catData, presenter);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(twoAdapter);
            }
        });
    }
}
