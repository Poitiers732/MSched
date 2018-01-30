package com.example.wzdrodowski.msched;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wzdrodowski.msched.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText foodTxt;
    EditText proteinTxt;   EditText carbsTxt;   EditText fatTxt;
    Button btnAddFood;
    Food food;
    ListView foodList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    //list_item
    LinearLayout listView;
    TextView liName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodTxt = (EditText)findViewById(R.id.foodTxt);
        proteinTxt = (EditText)findViewById(R.id.proteinTxt);
        carbsTxt = (EditText)findViewById(R.id.carbsTxt);
        fatTxt = (EditText)findViewById(R.id.fatTxt);
        foodList = (ListView)findViewById(R.id.foodList);

        btnAddFood = (Button)findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(this);

        listView = (LinearLayout)findViewById(R.id.listItem);
        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        foodList.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        food = new Food();

        String food_name = foodTxt.getText().toString();
        int protein = Integer.parseInt(proteinTxt.getText().toString());
        int carbs = Integer.parseInt(carbsTxt.getText().toString());
        int fat = Integer.parseInt(fatTxt.getText().toString());

        food.setFoodName(food_name);
        food.setProteinAmount(protein);
        food.setCarbsAmount(carbs);
        food.setFatAmount(fat);

        arrayList.add(foodTxt.getText().toString());
        adapter.notifyDataSetChanged();

        food.save();
        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
        foodTxt.setText("");    proteinTxt.setText("");    carbsTxt.setText("");    fatTxt.setText("");
        //foodTxt.requestFocus();

        view.clearFocus();

    }
}
