package com.base.postapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public TextInputEditText name, age, city;


    private MaterialButton post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.et_name);
        age = findViewById(R.id.et_age);
        city = findViewById(R.id.et_city);
        post = findViewById(R.id.btn_post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userAge = age.getText().toString();
                String userCity = city.getText().toString();

                postDataToApi(userName, userAge, userCity);
            }
        });
    }

    private void postDataToApi(String userName, String userAge, String userCity) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApiService apiService = retrofit.create(UserApiService.class); // Remove the 'new' keyword

        UserData userData = new UserData(userName, userAge, userCity);

        Call<Void> call = apiService.postUserData(userData);

        TextView output = findViewById(R.id.tv_result);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    String successMessage = "Data Posted Successfully. Status code: " + statusCode;
                    output.setText(successMessage);
                    Toast.makeText(MainActivity.this, successMessage, Toast.LENGTH_SHORT).show();
                } else {
                    int statusCode = response.code();
                    String errorMessage = "Failed to Post Data. Status code: " + statusCode;
                    output.setText(errorMessage);
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String errorMessage = "Network request failed: " + t.getMessage();
                output.setText(errorMessage);
                Toast.makeText(MainActivity.this, errorMessage , Toast.LENGTH_SHORT).show();
            }
        });
    }

}