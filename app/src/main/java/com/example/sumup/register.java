package com.example.sumup;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
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

import com.example.sumup.databinding.RegisterBinding;
import com.example.sumup.databinding.SignInBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.time.Duration;

public class register extends Fragment {
    private RegisterBinding binding;
    EditText textInputEditTextUser,textInputEditTextPassword,textInputEditTextPhone,textInputEditTextEmail;
    TextView textView;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = RegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textInputEditTextUser = getView().findViewById(R.id.username);
        textInputEditTextEmail= getView().findViewById(R.id.email);
        textInputEditTextPassword= getView().findViewById(R.id.password);
        textInputEditTextPhone= getView().findViewById(R.id.phone);



        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, password, email, phone;
                username = String.valueOf(textInputEditTextUser.getText());
                password = String.valueOf(textInputEditTextPassword.getText()) ;
                email = String.valueOf(textInputEditTextEmail.getText()) ;
                phone = String.valueOf(textInputEditTextPhone.getText()) ;

                if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "username";
                            field[1] = "password";
                            field[2] = "email";
                            field[3] = "phone";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = username;
                            data[1] = password;
                            data[2] = email;
                            data[3] = phone;
                            PutData putData = new PutData("https://running-wolf.co.za/android/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
                                        Toast toast = Toast.makeText(getActivity(),"sign up successful!", LENGTH_SHORT);
                                        toast.show();
                                        NavHostFragment.findNavController(register.this)
                                                .navigate(R.id.action_register3_to_sign_in);
                                    } else {
                                        Toast toast = Toast.makeText(getActivity(),result, LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    CharSequence text = "please complete all fields!";
                    int duration = Toast.LENGTH_SHORT;

                   Toast toast = Toast.makeText(getActivity(),username+password+phone+email, duration);
                   toast.show();
                }
            }
        });
    }
}
