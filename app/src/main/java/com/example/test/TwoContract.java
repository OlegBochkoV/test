package com.example.test;

import android.content.Context;

import java.util.List;

public interface TwoContract {
    interface View{
        void showCats(List<CatData> catData);
    }

    interface Presenter{
        void init(Context context);
        void tempfunc(CatData catData, int position);
    }

    interface Repository{
        List<CatData> loadData(Context context);
    }
}
