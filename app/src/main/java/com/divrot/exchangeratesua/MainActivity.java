package com.divrot.exchangeratesua;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get exchangedata from privat JSON
    private static String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    ArrayList<HashMap<String, String>> excRatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        excRatesList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetExchangeData().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetExchangeData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray ratesDataArr = new JSONArray(jsonStr);

                    // looping through All Exchange Data
                    for (int i = 0; i < ratesDataArr.length(); i++) {
                        JSONObject c = ratesDataArr.getJSONObject(i);

                        String ccy = c.getString("ccy");
                        String base_ccy = c.getString("base_ccy");
                        String buy = c.getString("buy");
                        String sale = c.getString("sale");

                        // tmp hash map for single data
                        HashMap<String, String> excDataMap = new HashMap<>();

                        // adding each child node to HashMap key => value
                        excDataMap.put("ccy", ccy);
                        excDataMap.put("base_ccy", base_ccy);
                        excDataMap.put("buy", buy);
                        excDataMap.put("sale", sale);

                        // adding contact to contact list
                        excRatesList.add(excDataMap);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, excRatesList,
                    R.layout.list_item, new String[]{"ccy", "base_ccy",
                    "buy", "sale"}, new int[]{R.id.ccy,
                    R.id.base_ccy, R.id.buy, R.id.sale});

            lv.setAdapter(adapter);
        }

    }
}