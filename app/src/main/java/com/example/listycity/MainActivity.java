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
    private Button addButton, deleteButton, editButton;

    private ArrayAdapter<City> cityAdapter;
    private ArrayList<City> dataList;

    private int selectedPosition = -1;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        selectedCityText = findViewById(R.id.selected_city_text);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        // Change String !!!
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


        cityAdapter = new CustomList(this, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            City selectedCity = dataList.get(position);
            selectedCityText.setText("Selected: " + selectedCity.getCityName());

            deleteButton.setEnabled(true);
            editButton.setEnabled(true);

            // Highlighting once again (Fittay mu)
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light, getTheme()));
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (i != position) {
                    parent.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent, getTheme()));
                }
            }
        });

        addButton.setOnClickListener(v -> {
            isEditing = false;
            new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
        });

        editButton.setOnClickListener(v -> {
            if (selectedPosition >= 0) {
                isEditing = true;
                City cityToEdit = dataList.get(selectedPosition);


                // Passing edited fragment
                AddCityFragment.newInstance(cityToEdit).show(getSupportFragmentManager(), "EDIT_CITY");
            }
        });

        deleteButton.setOnClickListener(v -> deleteSelectedCity());
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
    }

    @Override
    public void onOkPressed(City city) {
        if (isEditing) {

            dataList.set(selectedPosition, city);
            selectedCityText.setText("Selected: " + city.getCityName());

        } else {

            dataList.add(city);
        }
        cityAdapter.notifyDataSetChanged();
    }

    private void deleteSelectedCity() {
        if (selectedPosition != -1 && selectedPosition < dataList.size()) {
            City cityToDelete = dataList.get(selectedPosition);
            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();

            selectedPosition = -1;
            selectedCityText.setText("No city selected");
            deleteButton.setEnabled(false);
            editButton.setEnabled(false);

            for (int i = 0; i < cityList.getChildCount(); i++) {
                cityList.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent, getTheme()));
            }

            // For my convinience
            Toast.makeText(this, "Deleted: " + cityToDelete.getCityName(), Toast.LENGTH_SHORT).show();
        }
    }
}