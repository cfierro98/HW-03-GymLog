package com.example.hw_04_gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hw_04_gymlog.database.GymLogRepository;
import com.example.hw_04_gymlog.database.entities.User;
import com.example.hw_04_gymlog.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private GymLogRepository repository;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(!verifyUser()){
                    toastMaker("Invalid credentials");

                }else{
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                }

            }
        });

    }

    private boolean verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()){
            toastMaker("username should not be blank");
            return false;
        }
        user = repository.getUserByUserName(username);
        if(user != null){
            String password = binding.passwordLoginEditText.getText().toString();
            if(password.equals(user.getPassword())){
                return true;
            }else{
                toastMaker("invalid password");
                return false;
            }
        }
        toastMaker(String.format("No %s found", username));
        return false;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntetntFactory(Context context){
        return new Intent(context, LoginActivity.class);

    }
}