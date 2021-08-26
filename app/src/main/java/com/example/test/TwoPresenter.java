package com.example.test;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class TwoPresenter implements TwoContract.Presenter{
    private TwoContract.Repository repository;
    private TwoContract.View view;
    Context contextP;

    TwoPresenter(TwoContract.View view) {
        this.view=view;
        repository = new TwoRepository();
    }

    @Override
    public void init(Context context) {
        contextP = context;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<CatData> catData = null;
                catData = repository.loadData(contextP);
                view.showCats(catData);
            }
        });
        thread.start();
    }

    @Override
    public void tempfunc(CatData catData, int position) {

    }
}
