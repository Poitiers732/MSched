package com.example.wzdrodowski.msched;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wzdrodowski.msched.model.Food;

import java.util.ArrayList;

/**
 * Created by wzdrodowski on 2018-02-06.
 */

class Adapter extends ArrayAdapter<Food> {

    Adapter(Context context, ArrayList arrayList) {
        super(context, R.layout.list_item , arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_item, parent, false);

        Food singleFoodItem = getItem(position);
        TextView liName = (TextView) customView.findViewById(R.id.liName);
        TextView liProt = (TextView) customView.findViewById(R.id.liProt);
        TextView liCarb = (TextView) customView.findViewById(R.id.liCarb);
        TextView liFat = (TextView) customView.findViewById(R.id.liFat);

        liName.setText(singleFoodItem.getFoodName());
        liProt.setText(Integer.toString(singleFoodItem.getProteinAmount()));
        liCarb.setText(Integer.toString(singleFoodItem.getCarbsAmount()));
        liFat.setText(Integer.toString(singleFoodItem.getFatAmount()));

        return customView;
    }
}
