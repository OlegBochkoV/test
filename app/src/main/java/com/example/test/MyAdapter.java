package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private MainContract.Presenter presenter;
    private final LayoutInflater layoutInflater;
    private final List<CatData> catData;
    Context context;

    MyAdapter(Context context, List<CatData> catData, MainContract.Presenter presenter) {
        this.catData = catData;
        this.layoutInflater = LayoutInflater.from(context);
        this.presenter = presenter;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        CatData cat = catData.get(position);
        Glide
                .with(context)
                .load(cat.getImageUrl())
                .placeholder(R.drawable.progress_bar)
                .fitCenter()
                .into(holder.imageView);

        holder.textView.setText(cat.getInformation());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage(holder);
            }
        });
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat.setDownloadState(true);
                presenter.tempfunc(cat, position);
                saveStorage(holder, cat);
            }
        });
    }

    void saveStorage(MyAdapter.ViewHolder holder, CatData cat) {
        FileOutputStream fileOutputStream = null;

//        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dir = new File(context.getExternalFilesDir(null)+"/"+"TEMPfolder");

//        File dir = new File(file.getAbsolutePath() + "/kitty_image/");

        Log.d("QWERTYASD",dir.toString());

        BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        boolean wasSuccessful = dir.mkdirs();
        if (!wasSuccessful) System.out.println("was not successful.");

        File outFile = new File(dir, cat.getInformation() + ".jpg");

        Log.d("QWERTYASD","this");

        try {
            fileOutputStream = new FileOutputStream(outFile);
            Log.d("QWERTYASD","this1");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            Log.d("QWERTYASD","this2");
            fileOutputStream.flush();
            fileOutputStream.close();
            Log.d("QWERTYASD","this3");
            Toast.makeText(holder.imageView.getContext(), "Котик добавлен в избранное", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void downloadImage(MyAdapter.ViewHolder holder) {
        FileOutputStream fileOutputStream = null;

        BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

//        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());

        Log.d("QWERTYASD",dir.toString());

        boolean wasSuccessful = dir.mkdirs();
        if (!wasSuccessful) System.out.println("was not successful.");

        File outFile = new File(dir, System.currentTimeMillis() + ".jpg");

        try {
            fileOutputStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(holder.imageView.getContext(), "Котик сохранён", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return catData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final ImageView imageView;
        final Button button, button1;

        @SuppressLint("ResourceType")
        ViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.image);
            textView = view.findViewById(R.id.textView);
            button = view.findViewById(R.id.button);
            button1 = view.findViewById(R.id.button1);
        }
    }
}
