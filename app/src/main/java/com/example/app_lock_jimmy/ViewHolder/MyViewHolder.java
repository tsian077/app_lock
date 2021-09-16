package com.example.app_lock_jimmy.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_lock_jimmy.R;
import com.example.app_lock_jimmy.adapter.MyAdapter;
import com.example.app_lock_jimmy.model.MyItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView app_name;
    public ImageView app_icon,lock_app;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        app_name= itemView.findViewById(R.id.App_name);
        app_icon= itemView.findViewById(R.id.App_icon);
        lock_app = itemView.findViewById(R.id.Lock_app);

//        itemView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                app_icon.setBackgroundColor(-500136);
////                MyAdapter.Lockapp(MyAdapter)
//            }
//
//
//
//        });
    }
}
