package com.example.chien.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.custom,null);

        ThoiTiet thoiTiet = arrayList.get(position);
        TextView tvDay = (TextView) convertView.findViewById(R.id.tvNgay);
        TextView tvTrangThai = (TextView) convertView.findViewById(R.id.tvTrangThai);
        TextView tvMin = (TextView) convertView.findViewById(R.id.tvMin);
        TextView tvMax = (TextView) convertView.findViewById(R.id.tvMax);
        ImageView imgStatus = (ImageView) convertView.findViewById(R.id.imgTrangThai);

        tvDay.setText(thoiTiet.Day);
        tvTrangThai.setText(thoiTiet.Status);
        tvMax.setText(thoiTiet.MaxTemp);
        tvMin.setText(thoiTiet.MinTemp);

        Picasso.with(context).load("http://openweathermap.org/img/w/" + thoiTiet.Image + ".png").into(imgStatus);
        return convertView;
    }
}
