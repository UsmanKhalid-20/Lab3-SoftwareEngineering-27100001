package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {
    private ListView cityList;
    private TextView selectedCityText;
    private Button addButton, deleteButton, editButton; // Added editButton

    private ArrayAdapter<City> cityAdapter;
    private ArrayList<City> dataList;

    private int selectedPosition = -1;
    private boolean isEditing = false; // Flag to track state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        selectedCityText = findViewById(R.id.selected_city_text);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        // You should add this button to your main_activity.xml
        // Or you can reuse existing layout buttons differently.
        // For this code, I assume you added a button with id 'edit_button'
        editButton = findViewById(R.id.edit_button);

        // Initialize Data
        dataList = new ArrayList<>();
        dataList.add(new City("Peshawar", "KP"));
        dataList.add(new City("Charsadda", "KP"));
        dataList.add(new City("Karachi", "SI"));
        dataList.add(new City("Hydrabad", "SI"));
        dataList.add(new City("Bahawalpur", "PU"));
        dataList.add(new City("Lahore", "PU"));
        dataList.add(new City("Quetta", "BA"));
        dataList.add(new City("Gawadar", "BA"));


        // Use CustomList Adapter
        cityAdapter = new CustomList(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Handle Clicks
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            City selectedCity = dataList.get(position);
            selectedCityText.setText("Selected: " + selectedCity.getCityName());

            deleteButton.setEnabled(true);
            editButton.setEnabled(true); // Enable edit on selection

            // Highlight Logic
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light, getTheme()));
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (i != position) {
                    parent.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent, getTheme()));
                }
            }
        });

        // Add Button Logic
        addButton.setOnClickListener(v -> {
            isEditing = false;
            new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
        });

        // Edit Button Logic
        editButton.setOnClickListener(v -> {
            if (selectedPosition >= 0) {
                isEditing = true;
                City cityToEdit = dataList.get(selectedPosition);
                // Use newInstance to pass the city
                AddCityFragment.newInstance(cityToEdit).show(getSupportFragmentManager(), "EDIT_CITY");
            }
        });

        // Delete Button Logic
        deleteButton.setOnClickListener(v -> deleteSelectedCity());

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
    }

    // Interface method from Fragment
    @Override
    public void onOkPressed(City city) {
        if (isEditing) {
            // Update existing city
            dataList.set(selectedPosition, city);
            selectedCityText.setText("Selected: " + city.getCityName());
        } else {
            // Add new city
            dataList.add(city);
        }
        cityAdapter.notifyDataSetChanged();
    }

    private void deleteSelectedCity() {
        if (selectedPosition != -1 && selectedPosition < dataList.size()) {
            City cityToDelete = dataList.get(selectedPosition);
            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();

            // Reset UI
            selectedPosition = -1;
            selectedCityText.setText("No city selected");
            deleteButton.setEnabled(false);
            editButton.setEnabled(false);

            // Clear highlights
            for (int i = 0; i < cityList.getChildCount(); i++) {
                cityList.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent, getTheme()));
            }
            Toast.makeText(this, "Deleted: " + cityToDelete.getCityName(), Toast.LENGTH_SHORT).show();
        }
    }
}