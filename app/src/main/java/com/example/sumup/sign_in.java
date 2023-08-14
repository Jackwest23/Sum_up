package com.example.sumup;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.content.SharedPreferences;

import com.example.sumup.databinding.SignInBinding;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class sign_in extends Fragment {
    private SignInBinding binding;
    EditText textInputEditTextUser,textInputEditTextPassword;
    TextView textView;
   public SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {

        binding = SignInBinding.inflate(inflater, container, false);
        return binding.getRoot();



    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sp = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);

        textInputEditTextUser = getView().findViewById(R.id.Susername);
        textInputEditTextPassword = getView().findViewById(R.id.Spassword);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username, password;
                username = String.valueOf(textInputEditTextUser.getText());
                password = String.valueOf(textInputEditTextPassword.getText()) ;


                if (!username.isEmpty() && !password.isEmpty() ) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("https://running-wolf.co.za/android/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")){

                                        NavHostFragment.findNavController(sign_in.this)
                                                .navigate(R.id.action_sign_in_to_home32);

                                    } else {
                                        Toast toast = Toast.makeText(getActivity(),result, Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                }

                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("name",username);
                                editor.apply();

                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    CharSequence text = "please complete all fields!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getActivity(),username+password, duration);
                    toast.show();
                }
            }
        });


        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(sign_in.this)
                        .navigate(R.id.action_sign_in_to_register3);
            }
        });
    }
}

