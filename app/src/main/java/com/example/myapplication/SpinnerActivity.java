package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); }


        public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        spinner.setOnItemSelectedListener(this);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }


    }


