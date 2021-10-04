package com.example.app_lock_jimmy.adapter;

import android.content.Context;
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

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    public MyAdapter2(Context ctx, List<MyItem> apps,OnAppListener onapplistener) {
        this.ctx = ctx;
        this.apps = apps;
        this.monapplistener = onapplistener;
    }
    private Context ctx;
    private List<MyItem> apps;
    private OnAppListener monapplistener;


    @NonNull
    @Override
    public MyAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.my_view,parent,false);


        return new ViewHolder(view,monapplistener);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.ViewHolder holder, int position) {
        holder.app_name.setText(apps.get(position).getName());
        holder.app_icon.setImageDrawable(apps.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView app_name;
        public ImageView app_icon,lock_app;
        OnAppListener onapplistener;

        public ViewHolder(@NonNull View itemView,OnAppListener onapplistener) {
            super(itemView);
            app_name= itemView.findViewById(R.id.App_name);
            app_icon= itemView.findViewById(R.id.App_icon);
            lock_app = itemView.findViewById(R.id.Lock_app);
            this.onapplistener=onapplistener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onapplistener.onAppClick(getAdapterPosition());
        }
    }
    public interface OnAppListener{
        void onAppClick(int position);
    }
}
