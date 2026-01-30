package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView cityList;
    private TextView selectedCityText;
    private Button addButton, deleteButton;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        selectedCityText = findViewById(R.id.selected_city_text);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        // Data
        String[] cities = {"Lahore", "Peshawar", "Karachi", "Islamabad", "Quetta", "Gawadar", "Rawalpindi", "Multan", "Hyderabad"};
        dataList = new ArrayList<>(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);


        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            String selectedCity = dataList.get(position);
            selectedCityText.setText("Selected: " + selectedCity);

            // I wanted to distinguish what item i had selected from the list
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light, getTheme()));

            for (int i = 0; i < parent.getChildCount(); i++) {
                if (i != position) {
                    parent.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent, getTheme()));
                }
            }
        });


        addButton.setOnClickListener(v -> showAddCityDialog());
        deleteButton.setOnClickListener(v -> deleteSelectedCity());
        deleteButton.setEnabled(false);
    }


    private void showAddCityDialog() {

        final EditText input = new EditText(this);
        input.setHint("Enter city name");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New City").setView(input).setPositiveButton("CONFIRM", (dialog, which) -> {
                    String cityName = input.getText().toString().trim();

                    if (!cityName.isEmpty()) {
                        dataList.add(cityName);
                        cityAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Added: " + cityName, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "City name cannot be empty", Toast.LENGTH_SHORT).show();

                    }

                }).setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel()).show();
    }

    private void deleteSelectedCity() {

        if (selectedPosition != -1 && selectedPosition < dataList.size()) {
            String cityToDelete = dataList.get(selectedPosition);

            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();

            selectedPosition = -1;
            selectedCityText.setText("No city selected");
            deleteButton.setEnabled(false);

            for (int i = 0; i < cityList.getChildCount(); i++) {
                cityList.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent, getTheme()));
            }

            Toast.makeText(this, "Deleted: " + cityToDelete, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            selectedCityText.setText("Selected: " + dataList.get(position));
            deleteButton.setEnabled(true);

            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light, getTheme()));
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (i != position) {
                    parent.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent, getTheme()));
                }
            }
        });


    }
}