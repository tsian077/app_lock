package com.example.app_lock_jimmy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.app_lock_jimmy.adapter.MyAdapter2;
import com.example.app_lock_jimmy.model.MyItem;
import com.example.app_lock_jimmy.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter2.OnAppListener {
    androidx.appcompat.widget.Toolbar toolbar;
    LinearLayout layout_permission;

    private static final String TAG="MainActivity";

    List<MyItem> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if Android M or higher


        initView();
        initToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
        }

        Intent intent = new Intent(this,MyService.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(intent);
        }
        else{
            startService(intent);
        }




//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
////                    ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
////                    List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();
//                    while(true){
//                        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//                        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();
//                        for (int i = 0; i < runningAppProcessInfo.size(); i++) {
////                            Log.d(TAG,"hihihih");
//                            Log.d(TAG,runningAppProcessInfo.get(i).processName);
//                            if(runningAppProcessInfo.get(i).processName.equals("com.android.settings")){
////        com.android.settings com.android.chrome
//
//                                // Do you stuff
//                                Log.d(TAG,"hello");
//
//                            }
//                        }
//
//                    }
////                    for (int i = 0; i < runningAppProcessInfo.size(); i++) {
////                        Log.d(TAG,"hihihih");
////                        Log.d(TAG,runningAppProcessInfo.get(i).processName);
////                        if(runningAppProcessInfo.get(i).processName.equals("com.android.chrome")){
////
////                            // Do you stuff
////                            Log.d(TAG,"hello");
////                        }
////                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();






    }

    private void initView() {
        RecyclerView recyclerView=findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter2 adapter = new MyAdapter2(this,getApplications(),this);
        recyclerView.setAdapter(adapter);

        layout_permission=findViewById(R.id.layout_permission);

    }

    private List<MyItem> getApplications() {

        PackageManager manager =getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> infos = manager.queryIntentActivities(intent,0);
        for(ResolveInfo resolveInfo:infos){
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            items.add(new MyItem(activityInfo.loadIcon(manager),activityInfo.loadLabel(manager).toString(),activityInfo.packageName));
        }
        return items;
    }
//    private void

    private void initToolbar() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Applications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home);
        finish();
        return true;
    }

    @Override
    public void onAppClick(int position) {
//         items.get(position);
        Log.d(TAG,"click button"+items.get(position).getName());
    }

    public void set_permission(View view) {
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));


    }

    @Override
    protected void onResume(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(Utils.PermissionCheck(this)){
                layout_permission.setVisibility(View.GONE);
            }else{
                layout_permission.setVisibility(View.VISIBLE);
            }
        }
        super.onResume();
    }
}