package com.example.mipt_5_praktinis;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataLoader extends AsyncTask<String, Void, ArrayList<String>> {
    private static final String TAG = "DataLoader";
    private ArrayList<String> currencyList;
    private ArrayAdapter<String> adapter;
    private TextView errorTextView;

    public DataLoader(ArrayList<String> currencyList, ArrayAdapter<String> adapter, TextView errorTextView) {
        this.currencyList = currencyList;
        this.adapter = adapter;
        this.errorTextView = errorTextView;
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {
        Log.d(TAG, "doInBackground method called");
        ArrayList<String> currencies = new ArrayList<>();
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();

            Document document = Parser.parseXML(inputStream);
            currencies = Parser.extractCurrencyRates(document);
        } catch (Exception e) {
            Log.e(TAG, "Error loading data", e);
        }
        return currencies;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        Log.d(TAG, "onPostExecute method called");
        if (result != null && !result.isEmpty()) {
            currencyList.clear();
            currencyList.addAll(result);
            adapter.notifyDataSetChanged();
            errorTextView.setVisibility(TextView.GONE);
        } else {
            Log.e(TAG, "Result is null or empty in onPostExecute");
            errorTextView.setVisibility(TextView.VISIBLE);

        }
    }
}
