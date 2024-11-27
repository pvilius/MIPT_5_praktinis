package com.example.mipt_5_praktinis;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ListView currencyListView;
    private EditText filterCurrencyEditText;
    private ArrayList<String> currencyList;
    private ArrayAdapter<String> currencyAdapter;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate method called");

        currencyListView = findViewById(R.id.listView);
        filterCurrencyEditText = findViewById(R.id.filterEditText);
        errorTextView = findViewById(R.id.errorTextView);

        currencyList = new ArrayList<>();
        currencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencyList);
        currencyListView.setAdapter(currencyAdapter);

        new DataLoader(currencyList, currencyAdapter, errorTextView).execute(Constants.FLOATRATES_API_URL);
    }

    public void filterCurrencies(View view) {
        Log.d(TAG, "filterCurrencies method called");

        String filterText = filterCurrencyEditText.getText().toString().toUpperCase();

        if (filterText.isEmpty()) {
            loadData(view);
        } else {
            ArrayList<String> filteredList = new ArrayList<>();
            for (String currency : currencyList) {
                try {
                    String targetCurrency = currency.split(" – ")[0];
                    if (targetCurrency.contains(filterText)) {
                        filteredList.add(currency);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error in filtering: " + e.getMessage(), e);
                }
            }
            currencyAdapter.clear();
            currencyAdapter.addAll(filteredList);
            currencyAdapter.notifyDataSetChanged();
        }
    }

    public void loadData(View view) {
        Log.d(TAG, "loadData method called");

        filterCurrencyEditText.setText("");
        errorTextView.setVisibility(View.GONE);

        Toast.makeText(this, "Loading Data...", Toast.LENGTH_SHORT).show();

        try {
            // Pass the error TextView along with the data
            new DataLoader(currencyList, currencyAdapter, errorTextView).execute(Constants.FLOATRATES_API_URL);
        } catch (Exception e) {
            Log.e(TAG, "Error in loadData: " + e.getMessage(), e);
        }
    }
}
