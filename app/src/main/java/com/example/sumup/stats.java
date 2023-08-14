package com.example.sumup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.sumup.databinding.StatsBinding;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class stats extends Fragment {

    private StatsBinding binding;
    private TextView expense;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = StatsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        String name = sp.getString("name","");

        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(stats.this)
                        .navigate(R.id.action_stats2_to_setting2);
            }
        });
        binding.notifcations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(stats.this)
                        .navigate(R.id.action_stats2_to_notifications2);
            }

        });
        binding.savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(stats.this)
                        .navigate(R.id.action_stats2_to_myprofile2);
            }

        });
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(stats.this)
                        .navigate(R.id.action_stats2_to_home3);
            }

        });
        binding.addexpense.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.expense_prompts, null);

                expense = getView().findViewById(R.id.expen2);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserexpense);
                final EditText usercost = (EditText) promptsView
                        .findViewById(R.id.editTextTextDialogUserCost);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        expense.setVisibility(View.VISIBLE);
                                        expense.setText( name +userInput.getText()+": R"+ usercost.getText()+"\n date added: "+formattedDate);

                                        Handler handler = new Handler(Looper.getMainLooper());
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Starting Write and Read data with URL
                                                //Creating array for parameters
                                                String[] field = new String[4];
                                                field[0] = "username";
                                                field[1] = "expensename";
                                                field[2] = "_cost";
                                                field[3] = "_dateAdded";

                                                //Creating array for data
                                                String[] data = new String[4];
                                                data[0] = name;
                                                data[1] = userInput.getText().toString();
                                                data[2] = usercost.getText().toString();
                                                data[3] = formattedDate;

                                                PutData putData = new PutData("https://running-wolf.co.za/android/addExpenditure.php", "POST", field, data);
                                                if (putData.startPut()) {
                                                    if (putData.onComplete()) {
                                                        String result = putData.getResult();
                                                        if (result.equals("expense added")){


                                                        } else {
                                                            Toast toast = Toast.makeText(getActivity(),result, Toast.LENGTH_LONG);
                                                            toast.show();
                                                        }
                                                    }
                                                }
                                                //End Write and Read data with URL
                                            }
                                        });

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }

        });
        }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}