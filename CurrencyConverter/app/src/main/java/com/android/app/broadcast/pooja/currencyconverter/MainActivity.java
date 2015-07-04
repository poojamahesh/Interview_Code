package com.android.app.broadcast.pooja.currencyconverter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private ProgressBar pBar = null;

    // URL to get contacts JSON
    private static String url = "http://api.fixer.io/latest?base=USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button convert = (Button) findViewById(R.id.convert);
        final EditText usdValue = (EditText) findViewById(R.id.editTextUSDLR);
        final TextView gbpValue = (TextView) findViewById(R.id.textViewGBP);

        final TextView eurValue = (TextView) findViewById(R.id.textViewEURO);
        final TextView jpyValue = (TextView) findViewById(R.id.textViewJPY);
        final TextView brlValue = (TextView) findViewById(R.id.textViewBRL);
        pBar = (ProgressBar) findViewById(R.id.pBar);

        convert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!usdValue.getText().toString().equals("")) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(url, new AsyncHttpResponseHandler() {

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onSuccess(String response) {
                            Log.i("CHACHING", "Http Success");
                            //super.onSuccess(s);
                            try {

                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject ratesobj = jsonObj.getJSONObject("rates");

                                Double gbpRate = ratesobj.getDouble("GBP");
                                Double eurRate = ratesobj.getDouble("EUR");
                                Double jpyRate = ratesobj.getDouble("JPY");
                                Double brlRate = ratesobj.getDouble("BRL");

                                Log.i("CHACHING", "GBP :" + gbpRate);
                                Log.i("CHACHING", "EUR :" + eurRate);
                                Log.i("CHACHING", "JPY :" + jpyRate);
                                Log.i("CHACHING", "BRL :" + brlRate);


                                Integer usdlr = Integer.valueOf(usdValue.getText().toString());


                                Double gbpVal = usdlr * gbpRate;
                                Double eurVal = usdlr * eurRate;
                                Double jpyVal = usdlr * jpyRate;
                                Double brlVal = usdlr * brlRate;


                                gbpValue.setText("GBP: " + String.valueOf(gbpVal));
                                eurValue.setText("EUR:" + String.valueOf(eurVal));
                                jpyValue.setText("JPY: " + String.valueOf(jpyVal));
                                brlValue.setText("BRL: " + String.valueOf(brlVal));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter USD value..", Toast.LENGTH_LONG).show();
                }
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
