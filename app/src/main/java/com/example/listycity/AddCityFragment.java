package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    private EditText cityName;
    private EditText provinceName;
    private OnFragmentInteractionListener listener;
    private City cityToEdit;

    public interface OnFragmentInteractionListener {
        void onOkPressed(City city);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {

            listener = (OnFragmentInteractionListener) context;

        } else {

            throw new RuntimeException(context.toString() + "Implement karo");
        }
    }

    public static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_city, null);
        cityName = view.findViewById(R.id.city_name_edit);
        provinceName = view.findViewById(R.id.province_edit);

        String title = "Add City";

        // Assumption : If i am getting args then i am in highlighted section
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
            if (cityToEdit != null) {
                cityName.setText(cityToEdit.getCityName());
                provinceName.setText(cityToEdit.getProvinceName());
                title = "Edit City";
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder .setView(view).setTitle(title).setNegativeButton("Cancel", null).setPositiveButton("OK", (dialog, which) -> { String city = cityName.getText().toString(); String province = provinceName.getText().toString(); listener.onOkPressed(new City(city, province)); }).create();
    }
}