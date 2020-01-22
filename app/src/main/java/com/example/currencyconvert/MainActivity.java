package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView cadText, usdText, tlText, chfText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadText = findViewById(R.id.cadText);
        usdText = findViewById(R.id.usdText);
        tlText = findViewById(R.id.tlText);
        chfText = findViewById(R.id.chfText);
    }

    public void getRates(View view) {

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=80d80acf451d38ee27aef1f3d5f50dee&format=1";

            downloadData.execute(url);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0) {

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }

                return result;

            } catch (Exception e) {

                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                String base = jsonObject.getString("base");
                String rates = jsonObject.getString("rates");

                JSONObject jsonObject1 = new JSONObject(rates);

                String cad = jsonObject1.getString("CAD");
                cadText.setText("CAD : " + cad);

                String usd = jsonObject1.getString("USD");
                usdText.setText("USD : " + usd);

                String tl = jsonObject1.getString("TRY");
                tlText.setText("TRY : " + tl);

                String chf = jsonObject1.getString("CHF");
                chfText.setText("CHF : " + chf);


            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
