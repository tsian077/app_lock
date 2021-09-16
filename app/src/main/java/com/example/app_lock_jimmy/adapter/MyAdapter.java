package com.example.app_lock_jimmy.adapter;





import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_lock_jimmy.R;
import com.example.app_lock_jimmy.ViewHolder.MyViewHolder;
import com.example.app_lock_jimmy.model.MyItem;

import org.w3c.dom.Text;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public MyAdapter(Context ctx, List<MyItem> apps) {

        this.ctx = ctx;
        this.apps = apps;
    }

    private Context ctx;
    private List<MyItem> apps;
    private Lockapp lockapp;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.my_view,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.app_name.setText(apps.get(position).getName());
        holder.app_icon.setImageDrawable(apps.get(position).getIcon());
    }

    public interface Lockapp {
        void Loakapp(MyItem myItem);
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }


}

