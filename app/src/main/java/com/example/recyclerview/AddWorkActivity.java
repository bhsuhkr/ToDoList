package com.example.recyclerview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddWorkActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_insert);
    }

    public void addTask(View v) {
        EditText et = findViewById(R.id.editText);
        String insert = et.getText().toString();
        if(!insert.equals(null)){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Text_Name", insert);
            editor.commit();
        }
        super.onBackPressed();
    }
}