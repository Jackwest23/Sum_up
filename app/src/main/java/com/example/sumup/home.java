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
import android.preference.PreferenceFragment;

import com.example.sumup.databinding.HomeBinding;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class home extends Fragment {

    private HomeBinding binding;
    private TextView addallowannce;
    public int bal;
    private TextView balance;
    private TextView username;
    

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = HomeBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balance = getView().findViewById(R.id.balance);

        SharedPreferences sp = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        String name = sp.getString("name","");
        username = getView().findViewById(R.id.usertext);
        username.setText("welcome "+name);

        username.setTextSize(30);
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
                data[0] =   name;


                PutData putData = new PutData("https://running-wolf.co.za/android/checkbalance.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result.equals("balance found")){
                            PutData newdata = new PutData("https://running-wolf.co.za/android/grab_balance.php", "POST", field, data);
                            newdata.startPut();
                            newdata.onComplete();
                           String result2 =newdata.getResult();
                            balance.setText(result2);
                        }

                    }
                }
                //End Write and Read data with URL
            }
        });



        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(home.this)
                        .navigate(R.id.action_home3_to_setting2);
            }
        });
        binding.notifcations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(home.this)
                        .navigate(R.id.action_home3_to_notifications2);
            }

        });
        binding.savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(home.this)
                        .navigate(R.id.action_home3_to_myprofile2);
            }

        });
        binding.stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(home.this)
                        .navigate(R.id.action_home3_to_stats2);
            }

        });
        binding.balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                SharedPreferences sp = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
                String user = sp.getString("name","");

                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.prompts, null);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);


                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        Handler handler = new Handler(Looper.getMainLooper());
                                        handler.post(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             //Starting Write and Read data with URL
                                                             //Creating array for parameters
                                                             String[] field = new String[3];
                                                             field[0] = "username";
                                                             field[1] = "balance";
                                                             field[2] ="date";

                                                             //Creating array for data
                                                             String[] data = new String[3];
                                                             data[0] = user;
                                                             data[1] = userInput.getText().toString();
                                                             data[2] = formattedDate;

                                                             PutData putData = new PutData("https://running-wolf.co.za/android/addBal.php", "POST", field, data);
                                                             if (putData.startPut()) {
                                                                 if (putData.onComplete()) {
                                                                     String result = putData.getResult();
                                                                     if (result.equals("balance added")){
                                                                         Toast toast = Toast.makeText(getActivity(),result, Toast.LENGTH_LONG);
                                                                         toast.show();

                                                                     } else {
                                                                         Toast toast = Toast.makeText(getActivity(),result, Toast.LENGTH_LONG);
                                                                         toast.show();
                                                                     }
                                                                 }
                                                             }
                                                             //End Write and Read data with URL
                                                         }
                                                     });
                                        bal =Integer.parseInt( userInput.getText().toString());
                                      balance.setText(bal+"");



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