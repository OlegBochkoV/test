package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;;

import com.bumptech.glide.Glide;

import java.io.File;
import java.net.URI;
import java.util.List;

public class TwoAdapter extends RecyclerView.Adapter<TwoAdapter.ViewHolder> {

    private TwoContract.Presenter presenter;
    private final LayoutInflater layoutInflater;
    private final List<CatData> catData;
    Context context;

    TwoAdapter(Context context, List<CatData> catData, TwoContract.Presenter presenter) {
        this.catData = catData;
        this.layoutInflater = LayoutInflater.from(context);
        this.presenter = presenter;
    }

    @Override
    public TwoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_favourite, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TwoAdapter.ViewHolder holder, int position) {
        File dir = new File(context.getExternalFilesDir(null)+"/"+"TEMPfolder");
        CatData cat = catData.get(position);
        holder.textView.setText(cat.getInformation());
        Log.d("QWERTYASD",dir.toString());
        holder.imageView.setImageURI(Uri.parse(dir + "/" + cat.getInformation() + ".jpg"));
    }

    @Override
    public int getItemCount() {
        return catData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final ImageView imageView;

        @SuppressLint("ResourceType")
        ViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.image1);
            textView = view.findViewById(R.id.textView1);
        }
    }
}
