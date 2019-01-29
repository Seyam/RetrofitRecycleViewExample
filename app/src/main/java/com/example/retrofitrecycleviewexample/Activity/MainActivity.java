package com.example.retrofitrecycleviewexample.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofitrecycleviewexample.API.RetrofitInstance;
import com.example.retrofitrecycleviewexample.API.UserClient;
import com.example.retrofitrecycleviewexample.Adapter.EmployeeAdapter;
import com.example.retrofitrecycleviewexample.Model.Employee;
import com.example.retrofitrecycleviewexample.Model.EmployeeList;
import com.example.retrofitrecycleviewexample.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EmployeeAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Create handle for the RetrofitInstance interface*/
        UserClient service = RetrofitInstance.getRetrofitInstance().create(UserClient.class);
        /*Call the service method with parameter in the interface to get the employee data*/
        Call<EmployeeList> call = service.getEmployeeData();

        /*Log the URL called*/
        Log.e("URL Called", call.request().url().toString());

        call.enqueue(new Callback<EmployeeList>() {
            @Override
            public void onResponse(Call<EmployeeList> call, Response<EmployeeList> response) {
                generateEmployeeList(response.body().getEmployeeArrayList());
            }

            @Override
            public void onFailure(Call<EmployeeList> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    /*Method to generate List of employees using RecyclerView with custom adapter*/
    public void generateEmployeeList(ArrayList<Employee> empDataList){

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_employee_list);


        adapter = new EmployeeAdapter(empDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


    }



}
