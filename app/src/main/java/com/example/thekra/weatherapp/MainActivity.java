package com.example.thekra.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String URL = "http://countryapi.gear.host/v1/Country/getCountries";
    WeatherAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.list);
        ArrayList<Weather> list = new ArrayList<>();
        progressBar = findViewById(R.id.progress);
        adapter = new WeatherAdapter(this, list);
        listView.setAdapter(adapter);
        WeatherAsyncTask asyncTask = new WeatherAsyncTask();
        asyncTask.execute(URL);
    }

//Non-static inner classes holds a reference to the containing class.
// When you declare AsyncTask as an inner class, it might live longer than the containing Activity class.
// This is because of the implicit reference to the containing class. This will prevent the activity from being garbage collected,
// hence the memory leak.


    public class WeatherAsyncTask extends AsyncTask<String, Void, List<Weather>> {
//        private Context mContext;
//
//        public WeatherAsyncTask(Context context) {
//            mContext = context;
//        }

        @Override
        protected List<Weather> doInBackground(String... strings) {
            URL url = null;
            String jsonrespond = null;
            try {
                url = new URL(URL);
                jsonrespond = CreateConnection(url);
            } catch (Exception e) {
                Log.e("ERROR", "IN BACKGROUND THREAD", e);
            }
            List<Weather> weather = getJson(jsonrespond);
            return weather;
        }

        @Override
        protected void onPostExecute(List<Weather> weatherList) {
            progressBar.setVisibility(View.GONE);
            adapter.addAll(weatherList);
        }
    }

    private static String CreateConnection(URL s) {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;
        try {

            HttpURLConnection connection = (HttpURLConnection) s.openConnection();
            connection.setRequestMethod("GET");
            //15 work perfect but 1000 crash the app
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();
            //read from server
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //convert respond from server to String
            String line = bufferedReader.readLine();
            stringBuilder = new StringBuilder();
            if (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            Log.e("ERROR", "EROOR HTTP getDAta method", e);
        }
        return stringBuilder.toString();

    }

    private static List<Weather> getJson(String response) {
        List<Weather> jsonresponse = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentJson = jsonArray.getJSONObject(i);
                String name = currentJson.getString("NativeName");
                String info = currentJson.getString("Region");
                String image = currentJson.getString("FlagPng");
                Log.i("IMAGE","IIII" +name + info + image);
                Weather weatherClass = new Weather(name, info,image);
                jsonresponse.add(weatherClass);
            }
        } catch (Exception e) {
            Log.e("Error", "ERROR in Json response from server", e);
        }
        return jsonresponse;
    }
}

