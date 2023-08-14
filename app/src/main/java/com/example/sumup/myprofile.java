package com.example.sumup;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sumup.databinding.MyprofileBinding;
import com.example.sumup.databinding.StatsBinding;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class myprofile extends Fragment {
    private MyprofileBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MyprofileBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(myprofile.this)
                        .navigate(R.id.action_myprofile2_to_setting2);
            }
        });
        binding.notifcations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(myprofile.this)
                        .navigate(R.id.action_myprofile2_to_notifications2);
            }

        });
        binding.stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(myprofile.this)
                        .navigate(R.id.action_myprofile2_to_stats2);
            }

        });
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(myprofile.this)
                        .navigate(R.id.action_myprofile2_to_home3);
            }

        });
        binding.set2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(myprofile.this)
                        .navigate(R.id.action_myprofile2_to_setnotif);
            }


        });
        binding.set1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
                String user = sp.getString("name","");
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.update_profile, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());
                alertDialogBuilder.setView(promptsView);

                final EditText upUser = (EditText) promptsView
                        .findViewById(R.id.upUser);
                final EditText upPass = (EditText) promptsView
                        .findViewById(R.id.upPass);
                final EditText upEmail = (EditText) promptsView
                        .findViewById(R.id.upEmail);
                final EditText upPhone = (EditText) promptsView
                        .findViewById(R.id.upPhone);


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
                                                String[] field = new String[5];
                                                field[0] = "username";
                                                field[1] = "email";
                                                field[2] ="password";
                                                field[3] ="phone";
                                                field[4] = "old_username";

                                                //Creating array for data
                                                String[] data = new String[5];
                                                data[0] = upUser.getText().toString();
                                                data[1] = upEmail.getText().toString();
                                                data[2] = upPass.getText().toString();
                                                data[3]= upPhone.getText().toString();
                                                data[4]= user;


                                                PutData putData = new PutData("https://running-wolf.co.za/android/update_records.php", "POST", field, data);
                                                if (putData.startPut()) {
                                                    if (putData.onComplete()) {
                                                        String result = putData.getResult();
                                                        if (result.equals("records updated")){
                                                            SharedPreferences.Editor editor = sp.edit();
                                                            editor.putString("name",upUser.toString());
                                                            editor.apply();
                                                            System.exit(0);

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

}
