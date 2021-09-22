package com.example.weatherkit1;
import java.util.concurrent.TimeUnit;
import android.os.Bundle;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;



import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public double AvgTempdata;
    public double MinTempdata;
    public double MaxTempdata;

    public double AvgPressdata;
    public double MinPressdata;
    public double MaxPressdata;

    public double AvgHumdata;
    public double MinHumdata;
    public double MaxHumdata;

    public Button button;
    public JsonObjectRequest jsonObjReq;
    public EditText et, et1, et2,et3,et4,et5,et6,et7,et8;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText et = (EditText) findViewById(R.id.editText);
        et. setText("0.0");
        this.et = et;
        final EditText et1 = (EditText) findViewById(R.id.editText2);
        et1. setText("0.0");
        this.et1 = et1;
        final EditText et2 = (EditText) findViewById(R.id.editText3);
        et2. setText("0.0");
        this.et2 = et2;
        final EditText et3 = (EditText) findViewById(R.id.editText4);
        et3.setText("0.0");
        this.et3= et3;
        final EditText et4 = (EditText) findViewById(R.id.editText5);
        et4. setText("0.0");
        this.et4 = et4;
        final EditText et5 = (EditText) findViewById(R.id.editText6);
        et5. setText("0.0");
        this.et5 = et5;

        final EditText et6 = (EditText) findViewById(R.id.editText7);
        et6. setText("0.0");
        this.et6 = et6;
        final EditText et7 = (EditText) findViewById(R.id.editText8);
        et7. setText("0.0");
        this.et7 = et7;
        final EditText et8 = (EditText) findViewById(R.id.editText9);
        et8. setText("0.0");
        this.et8 = et8;
        this.AvgTempdata=5.0;
        this.AvgPressdata=5.0;
        this.AvgHumdata=5.0;
        System.out.println("created*.");
        Button button = (Button) findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("RUnning getdata");
                GetJsonData();
                System.out.println("Completed running");
            }

        });

    }


    /*GET JSON DATAT*/
    public void GetJsonData() {


        String lightApi = "https://api.thingspeak.com/channels/1370437/feeds.json?api_key=MUO2KIYKA5KJY1JI&results=829"; /** Give your READAPI here**/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, lightApi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {



                    AvgTempdata=0;
                    MinTempdata=5000;
                    AvgPressdata=0;
                    MinPressdata=5000;
                    AvgHumdata=0;
                    MinHumdata=5000;
                    JSONArray feeds = response.getJSONArray("feeds");
                    System.out.println("At feed"); /** Taking recent 240 values for analysis **/
                    for (int i = feeds.length()-240; i < feeds.length(); i++) {
                        JSONObject jo = feeds.getJSONObject(i);
                        String l = jo.getString("field1");
                        String l1= jo.getString("field2");
                        String l2= jo.getString("field3");
                        float la = Float.parseFloat(l);
                        float la1 = Float.parseFloat(l1);
                        float la2 = Float.parseFloat(l2);
                        if (la > MaxTempdata) {
                            MaxTempdata = la;

                        } else if (la < MinTempdata) {
                            MinTempdata = la;
                        }
                        if (la1 > MaxPressdata) {
                            MaxPressdata = la1;

                        } else if (la1 < MinPressdata) {
                            MinPressdata = la1;
                        }
                        if (la2 > MaxHumdata) {
                            MaxHumdata = la2;

                        } else if (la2 < MinHumdata) {
                            MinHumdata = la2;
                        }
                        AvgTempdata += la;
                        AvgHumdata+=la2;
                        AvgPressdata+=la1;

                    }
                    System.out.println(AvgTempdata);
                    AvgTempdata = AvgTempdata /240;
                    AvgPressdata = AvgPressdata /240;
                    AvgHumdata = AvgHumdata /240;







                } catch (JSONException e) {

                    e.printStackTrace();
                }




            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(jsonObjReq);
        mRequestQueue.start();
        SystemClock.sleep(5000);
        System.out.println("Final Result");
        this.et.setText(Double.toString(MaxTempdata));


        this.et1.setText(Double.toString(AvgTempdata));


        this.et2.setText(Double.toString(MinTempdata));

        this.et3.setText(Double.toString(MaxPressdata));


        this.et4.setText(Double.toString(AvgPressdata));


        this.et5.setText(Double.toString(MinPressdata));

        this.et6.setText(Double.toString(MaxHumdata));


        this.et7.setText(Double.toString(AvgHumdata));


        this.et8.setText(Double.toString(MinHumdata));

    }


}


