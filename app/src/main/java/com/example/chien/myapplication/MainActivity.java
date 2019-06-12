package com.example.chien.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private EditText mEdtSearch;
    private ImageView mBtnSearch;
    private TextView mTvCityName;
    private TextView mTvNationName;
    private ImageView mImgIcon;
    private TextView mTvNhietDo;
    private TextView mTvTrangThai;
    private TextView mTvDoAm;
    private TextView mTvTocDoGio;
    private TextView mTvMay;
    private TextView mTvApSuat;
    private TextView mTvMatTroiMoc;
    private TextView mTvMatTroiLan;
    private Button mBtnNextDay;
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        GetCurrentWeatherData("Hanoi");

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = mEdtSearch.getText().toString();
                if (city.equals("")){
                    City="Hanoi";
                    GetCurrentWeatherData(City);
                } else {
                    City = city;
                    GetCurrentWeatherData(City);
                }

            }
        });

        mBtnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = mEdtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });

    }

    public void GetCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&APPID=0be6a381102a7cfd0b285b2dff04b260";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String name = jsonObject.getString("name");
                            mTvCityName.setText(name);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String trangthai = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(mImgIcon);
                            mTvTrangThai.setText(trangthai);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");
                            String apsuat = jsonObjectMain.getString("pressure");
                            Double a = Double.valueOf(nhietdo);
                            String NhietDO = String.valueOf(a.intValue());

                            mTvNhietDo.setText(NhietDO + " Độ C");
                            mTvDoAm.setText(doam + " %");
                            mTvApSuat.setText(apsuat + " hPa");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String speed = jsonObjectWind.getString("speed");
                            mTvTocDoGio.setText(speed + " m/s");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String nation = jsonObjectSys.getString("country");
                            mTvNationName.setText(nation);
                            String sunrise = jsonObjectSys.getString("sunrise");
                            String sunset = jsonObjectSys.getString("sunset");

                            long sr = Long.valueOf(sunrise);
                            long ss = Long.valueOf(sunset);

                            Date SR = new Date(sr * 1000L);
                            Date SS = new Date(ss * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                            String rise = simpleDateFormat.format(SR);
                            String set = simpleDateFormat.format(SS);

                            mTvMatTroiMoc.setText(rise);
                            mTvMatTroiLan.setText(set);


                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            mTvMay.setText(may + " %");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    private void initView() {


        mEdtSearch = findViewById(R.id.edtSearch);
        mBtnSearch = findViewById(R.id.btnSearch);
        mTvCityName = findViewById(R.id.tvCityName);
        mTvNationName = findViewById(R.id.tvNationName);
        mImgIcon = findViewById(R.id.imgIcon);
        mTvNhietDo = findViewById(R.id.tvNhietDo);
        mTvTrangThai = findViewById(R.id.tvTrangThai);
        mTvDoAm = findViewById(R.id.tvDoAm);
        mTvTocDoGio = findViewById(R.id.tvTocDoGio);
        mTvMay = findViewById(R.id.tvMay);
        mTvApSuat = findViewById(R.id.tvApSuat);
        mTvMatTroiMoc = findViewById(R.id.tvMatTroiMoc);
        mTvMatTroiLan = findViewById(R.id.tvMatTroiLan);
        mBtnNextDay = findViewById(R.id.btnNextDay);
    }
}
