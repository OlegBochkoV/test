package com.example.test;

import android.content.Context;

import java.util.List;

public interface MainContract {
    interface View {
        void showCats(List<CatData> catData);
        void openTwoScreen();
    }

    interface Presenter {
        void favouriteScreenButtonClick();
        void init(Context context);
        void update_recycler();
        void tempfunc(CatData catData, int position);
    }

    interface Repository {
        List<CatData> loadData(Context context);
        List<CatData> updateData();
        void saveStorage();
        void editCatData(CatData catData, int position);
    }
}
