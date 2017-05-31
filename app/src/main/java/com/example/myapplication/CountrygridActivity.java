package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.myapplication.CustomGrid;

import static java.security.AccessController.getContext;

public class CountrygridActivity extends AppCompatActivity {
    GridView grid;

    String[] web = {
            "Austria",
            "Belgium",
            "Bulgaria",
            "Croatia",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Estonia",
            "Finland",
            "France",
            "Germany",
            "Greece",
            "Hungary",
            "Ireland",
            "Italy",
            "Latvia",
            "Lithuania",
            "Luxembourg",
            "Malta",
            "Netherlands",
            "Poland",
            "Portugal",
            "Romania",
            "Slovakia",
            "Slovenia",
            "Spain",
            "Sweden",
            "United Kingdom",
    } ;
    int[] imageId = {

            R.drawable.austria,
            R.drawable.belgium,
            R.drawable.bulgaria,
            R.drawable.croatia,
            R.drawable.cyprus,
            R.drawable.czechrepublic,
            R.drawable.denmark,
            R.drawable.estonia,
            R.drawable.finland,
            R.drawable.france,
            R.drawable.germany,
            R.drawable.greece,
            R.drawable.hungary,
            R.drawable.ireland,
            R.drawable.italy,
            R.drawable.latvia,
            R.drawable.lithunia,
            R.drawable.luxemborg,
            R.drawable.malta,
            R.drawable.netherlands,
            R.drawable.poland,
            R.drawable.portugal,
            R.drawable.romania,
            R.drawable.slovakia,
            R.drawable.slovenia,
            R.drawable.spain,
            R.drawable.sweden,
            R.drawable.uk,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrygrid);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CustomGrid adapter = new CustomGrid(CountrygridActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(CountrygridActivity.this, "You Clicked at " +web[position], Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getBaseContext() , CountryActivity.class);
                intent.putExtra("country name",web[position]);

                startActivity(intent);
                



            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("msg","working properly5");
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}