package com.example.sumup;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sumup.databinding.NotificationsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class notifications extends Fragment {
    private NotificationsBinding binding;
    ArrayList<String> stringArray = new ArrayList<String>();
    ArrayList<Integer> costArray = new ArrayList<Integer>();
    ArrayList<String> monthArray = new ArrayList<String>();
    BarChart barChart;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> label;



    TextView check;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = NotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {



        //check.setText(str.get(0));

        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");

        check = getView().findViewById(R.id.check);


        barChart = getView().findViewById(R.id.graph);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "username";

                //Creating array for data
                String[] data = new String[1];
                data[0] = name;


                PutData putData = new PutData("https://running-wolf.co.za/android/checkExpenses.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result.equals("expenses found!")) {


                            PutData newdata = new PutData("https://running-wolf.co.za/android/grab_expenses.php", "POST", field, data);
                            newdata.startPut();
                            newdata.onComplete();
                            String result2 = newdata.getResult();
                            try {

                                jsonStringToArray(result2);


                            } catch (JSONException e) {
                                throw new RuntimeException(e);

                            }


                        }

                    }
                }

            }
        });


        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(notifications.this)
                        .navigate(R.id.action_notifications2_to_setting2);
            }
        });
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(notifications.this)
                        .navigate(R.id.action_notifications2_to_home3);
            }

        });
        binding.savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(notifications.this)
                        .navigate(R.id.action_notifications2_to_myprofile2);
            }

        });
        binding.stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(notifications.this)
                        .navigate(R.id.action_notifications2_to_stats2);
            }

        });
    }

    public void jsonStringToArray(String str) throws JSONException {
        barChart = getView().findViewById(R.id.barChart);

        barEntries = new ArrayList<>();
        label = new ArrayList<>();
        String expense_name = null;
        String expense_cost =null;
        String date_Added = null;
        JSONArray jsonArray = new JSONArray(str);
        for(int i=0;i< jsonArray.length();i++)
        {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
             expense_name = jsonObject1.optString("expensename");
            expense_cost = jsonObject1.optString("_cost");
            int ex_cost = Integer.parseInt(expense_cost);
            date_Added = jsonObject1.optString("_dateAdded");
            barEntries.add(new BarEntry(i,ex_cost));
            label.add(date_Added);


        }
        Description description= new Description();
        description.setText("date added");
        barChart.setDescription(description);
        check.setText(jsonArray.getString(0));
        Toast toast = Toast.makeText(getActivity(), date_Added
                , Toast.LENGTH_LONG);
        toast.show();

        BarDataSet barDataSet = new BarDataSet(barEntries,"dateAdded");
        barDataSet.setColor(getResources().getColor(R.color.black));
        BarData barData = new BarData(barDataSet);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(label));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(label.size());
        xAxis.setLabelRotationAngle(270);
        barChart.setDescription(description);

        barChart.animateY(2000);
        barChart.setData(barData);
        barChart.invalidate();


       // stringArray.add(format);



    }







    public void populateGraphs(String name, int cost, String date, JSONArray js) {
        barChart = getView().findViewById(R.id.barChart);

        barEntries = new ArrayList<>();
        label = new ArrayList<>();


            for ( int i =0; i <js.length();i++){

                barEntries.add(new BarEntry(i,cost));
                label.add(date);
            }
        Description description= new Description();
        description.setText("date added");
        barChart.setDescription(description);


            BarDataSet barDataSet = new BarDataSet(barEntries,"dateAdded");
            barDataSet.setColor(1);
            BarData barData = new BarData(barDataSet);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(label));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(label.size());
        xAxis.setLabelRotationAngle(270);
        barChart.setDescription(description);

        barChart.animateY(2000);
       barChart.setData(barData);
            barChart.invalidate();







    }


}
