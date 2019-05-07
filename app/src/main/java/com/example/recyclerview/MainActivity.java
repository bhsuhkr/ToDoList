package com.example.recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener{
    private static final String TAG = "Track Items";
    private SharedPreferences prefs;
    private Set<String> progress;
    final ArrayList<String> listItems = new ArrayList<String>();
    private final int categoryIcon = R.drawable.ic_launcher_foreground;
    private RecyclerView mainRecyclerView;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private  ArrayList<MainModel> mainModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainRecyclerView = findViewById(R.id.my_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setLayoutManager(linearLayoutManager);

        // Saved values
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        progress = prefs.getStringSet("myProgress", new HashSet<String>());

        if(progress != null){
            Iterator<String> iterator = progress.iterator();
            while(iterator.hasNext()){
                String id = iterator.next();
                listItems.add(id);
            }
        }
        Collections.sort(listItems);
        //Recycler Adapter
        startAdapter();
    }

    private void startAdapter(){
        mainModelArrayList = prepareList();
        mainRecyclerAdapter = new MainRecyclerAdapter(this,mainModelArrayList);
        mainRecyclerAdapter.setOnRecyclerViewItemClickListener(this);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
    }

    private ArrayList<MainModel> prepareList() {
        ArrayList<MainModel> mainModelList = new ArrayList<>();
        for (int i = 0; i < listItems.size(); i++) {
            MainModel mainModel = new MainModel();
            mainModel.setOfferName(listItems.get(i));
            mainModel.setOfferIcon(categoryIcon);
            mainModelList.add(mainModel);
        }
        return mainModelList;
    }

    @Override
    public void onItemClick(final int position, View view) {
        MainModel mainModel = (MainModel) view.getTag();
        switch (view.getId()) {
            case R.id.row_main_adapter_linear_layout:
//                Toast.makeText(this,"Position clicked: " + String.valueOf(position) + ", "+ mainModel.getOfferName(),Toast.LENGTH_LONG).show();
                final int curPosition = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Option");
                String[] option = {"Edit", "Delete"};
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // edit
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                LayoutInflater inflater = getLayoutInflater();
                                final View dialogView = inflater.inflate(R.layout.alertdialog_edit_view, null);
                                dialogBuilder.setView(dialogView);

                                final EditText edt = dialogView.findViewById(R.id.updateValue);

                                dialogBuilder.setTitle("Update Item");

                                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String insertedValue = edt.getText().toString();
                                        if(insertedValue.length() != 0){
                                            listItems.set(curPosition, edt.getText().toString());
                                            Collections.sort(listItems);
                                            startAdapter();
                                        }else{
                                            Toast.makeText(MainActivity.this, "Enter a value", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //pass
                                    }
                                });
                                AlertDialog b = dialogBuilder.create();
                                b.show();
                                break;

                            case 1: // delete
                                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                                adb.setTitle("Delete?");
                                adb.setMessage("Are you sure you want to delete this item?");
                                adb.setNegativeButton("Cancel", null);
                                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        listItems.remove(listItems.get(position));
                                        startAdapter();
                                    }});
                                adb.show();
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    public void addItems(View v) {
        startActivity(new Intent(this, AddWorkActivity.class));
    }

    public void settingButtonClicked(View v) {
//        startActivity(new Intent(this, UserSettingActivity.class));
        Intent i = new Intent(this, UserSettingActivity.class);
        startActivityForResult(i, 1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "INSIDE: onDestroy");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Text_Name", null);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "INSIDE: onPause");
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Text_Name", null);
        SharedPreferences.Editor editPrefs = prefs.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(listItems);
        editPrefs.putStringSet("myProgress", set);
        editPrefs.commit();
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "INSIDE: onResume");
        try{
            String data = prefs.getString("Text_Name", null); //no id: default value
            if(data.length() != 0){
                listItems.add(data);
                Collections.sort(listItems);
                startAdapter();
            }
        }catch (Exception e){
            Log.d(TAG, "INSIDE: No extra string");
        }
    }


}