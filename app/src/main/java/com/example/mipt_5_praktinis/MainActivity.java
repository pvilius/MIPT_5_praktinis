package com.example.mipt_5_praktinis;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText filterEditText;
    private ArrayList<String> currencyList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        filterEditText = findViewById(R.id.filterEditText);

        currencyList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencyList);
        listView.setAdapter(adapter);

        new DataLoader(currencyList, adapter).execute(Constants.FLOATRATES_API_URL);
    }

    public void filterCurrencies(View view) {
        String filter = filterEditText.getText().toString().toUpperCase();

        if (filter.isEmpty()) {
            loadData(view);
        } else {
            ArrayList<String> filteredList = new ArrayList<>();
            for (String currency : currencyList) {
                String targetCurrency = currency.split(" – ")[0];
                if (targetCurrency.contains(filter)) {
                    filteredList.add(currency);
                }
            }
            adapter.clear();
            adapter.addAll(filteredList);
            adapter.notifyDataSetChanged();
        }
    }

    public void loadData(View view) {
        filterEditText.setText("");

        Toast.makeText(this, "Loading Data...", Toast.LENGTH_SHORT).show();

        new DataLoader(currencyList, adapter).execute(Constants.FLOATRATES_API_URL);
    }
}
