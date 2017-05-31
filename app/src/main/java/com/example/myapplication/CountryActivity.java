package com.example.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.ProgressDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import android.widget.ProgressBar;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

import static android.R.attr.button;
import static android.R.attr.data;
import static android.R.attr.entries;
import static android.R.attr.onClick;
import static android.R.attr.x;

import static android.os.Build.VERSION_CODES.M;
import static jxl.biff.formula.FormulaErrorCode.NA;

public class CountryActivity extends AppCompatActivity {

    Spinner spinner,spinner2,spinner3,spinner4;
    BarChart barChart;
    TableLayout tl;
    String comp="";
    ArrayList<String> temp = new ArrayList<String>();

    //reads from excel file and gives data to graph
    void readAndGetData(String type, String country, String mineral, Integer startDate, Integer endDate)
    {
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("Datafinal.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s;

            if(type.equals("Import"))
            {
                s = wb.getSheet(0);
                comp="Import";
            }
            else if(type.equals("Export"))
            {
                s = wb.getSheet(1);
                comp="Export";
            }
            else
            {
                s = wb.getSheet(2);
                comp="Production";
            }
            int rows=s.getRows();
            int cols=s.getColumns();

            int i;

            for(i=1;i <rows;i++)
            {
                //Log.d("Here", new Integer(rows).toString()+s.getCell(0,2).getContents());
                //Toast.makeText(CountryActivity.this, s.getCell(1,2).getContents(),Toast.LENGTH_SHORT).show();
                Log.d("CHECK", s.getCell(1,i).getContents().toString()+ " "+ country+ " "+ s.getCell(0,i).getContents().toString() + " "+ type + " "+s.getCell(2,i).getContents().toString()+ " "+mineral);

                if(s.getCell(1,i).getContents().toString().trim().equals(country.trim()) && s.getCell(0,i).getContents().toString().trim().equals(type.trim()) && s.getCell(2,i).getContents().toString().trim().equals(mineral.trim()) )
                {
                    //Log.d("CHECK", s.getCell(1,i).getContents().toString()+ " "+ country+ " "+ s.getCell(0,i).getContents().toString() + " "+ type + " "+s.getCell(2,i).getContents().toString()+ " "+mineral);
                    //1970 is the 7th column ie 6th index
                    Integer start = startDate - 1970 + 3;
                    Integer end = endDate - 1970 + 3;
                    String xx="";
                    String yy="";
                    TextView tx=(TextView)findViewById(R.id.textView2) ;
                    TextView ty=(TextView)findViewById(R.id.textView3) ;
                    Integer j;
                    Integer k=0;
                    Integer m;
                    Integer zz=0;
                    ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
                    ArrayList<String> labels = new ArrayList<String>();
                    for (j = start; j <= end; j++) {
                        xx=xx+( new Integer(j+1967).toString() +"\n");
                        yy=yy + (s.getCell(j, i).getContents().toString()+"\n");
                        tx.setText(xx);
                        ty.setText(yy);

                        float a = j.floatValue() + 1967;
                          if (!s.getCell(j, i).getContents().toString().trim().equals("NA")) {
                              m=j-k;
                              k=k+1;
                              zz=Integer.parseInt(s.getCell(j,i).getContents());
                            entries.add(new BarEntry(zz,j-m));
                            labels.add(Integer.toString(j + 1967));
                              temp=labels;
                        }
                    }
                    if(labels.size()==0)
                    {
                        Toast.makeText(this, "There is no data available to query!", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Log.d("OHYEAH", "Here");
                        BarDataSet dataset = new BarDataSet(entries, comp+"Tonnes");
                        BarData data = new BarData(labels, dataset);
                        barChart.setData(data);
                        barChart.setDescription("Tonnes vs Year");
                        barChart.setVisibility(View.VISIBLE);
                    }

                    return;
                }

            }
            Toast.makeText(this, "Data not available for your query", Toast.LENGTH_LONG).show();


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countryactivity);
        s = getIntent().getStringExtra("country name");
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner = (Spinner) findViewById(R.id.planets_spinner);
        barChart = (BarChart) findViewById(R.id.barChart);
        barChart.setNoDataText("Tap Here for Bar Chart");
        // Button 1
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets1, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Commodity");
// Apply the adapter to the spinner
        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter, R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        // Button 2
        spinner2 = (Spinner) findViewById(R.id.planet_spinner2);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.planets2, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setPrompt("Select Data Type");
// Apply the adapter to the spinner
        spinner2.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter2, R.layout.contact_spinner_row_nothing_selected2,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this));

        //  Button 3

        spinner3 = (Spinner) findViewById(R.id.planets_spinner3);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.planets3, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner3.setPrompt("Select Date: From");
// Apply the adapter to the spinner
        spinner3.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter3, R.layout.contact_spinner_row_nothing_selected3,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this));

        // Button 4

        spinner4 = (Spinner) findViewById(R.id.planets_spinner4);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.planets4, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner4.setPrompt("Select Date: To");
// Apply the adapter to the spinner
        spinner4.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter4, R.layout.contact_spinner_row_nothing_selected4,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this));

        // Submit Button
        final TableLayout tl=(TableLayout) findViewById(R.id.tablelayout);
        tl.setVisibility(View.INVISIBLE);
        barChart.setVisibility(View.INVISIBLE);

        final Button button = (Button) findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(spinner.getSelectedItem()==null ||  spinner2.getSelectedItem()==null || spinner3.getSelectedItem()==null || spinner4.getSelectedItem()==null)
                {
                    Toast.makeText(CountryActivity.this, "Please select all options before submitting", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d("DEBUG", spinner.getSelectedItem().toString()+" "+spinner2.getSelectedItem().toString()+" ");
                if(Integer.parseInt(spinner3.getSelectedItem().toString()) > Integer.parseInt(spinner4.getSelectedItem().toString()))
                {
                    Toast.makeText(CountryActivity.this, "Start year can't be greater than end year!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //parse the excelsheet and get the relevant data
                readAndGetData(spinner2.getSelectedItem().toString(), s,spinner.getSelectedItem().toString(), Integer.parseInt(spinner3.getSelectedItem().toString()), Integer.parseInt(spinner4.getSelectedItem().toString()));

                if(temp.size()!=0)
                {tl.setVisibility(View.VISIBLE);
                barChart.setVisibility(View.VISIBLE);}
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
