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

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.wzdrodowski.msched.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText foodTxt;
    EditText proteinTxt;   EditText carbsTxt;   EditText fatTxt;
    Button btnAddFood;
    Food food;
    ListView foodListView;
    ArrayList<String> arrayList;
    ArrayAdapter<Food> adapter;

    //list_item
    ListView listView;
    TextView liName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveAndroid.initialize(this);
        foodTxt = (EditText)findViewById(R.id.foodTxt);
        proteinTxt = (EditText)findViewById(R.id.proteinTxt);
        carbsTxt = (EditText)findViewById(R.id.carbsTxt);
        fatTxt = (EditText)findViewById(R.id.fatTxt);

        btnAddFood = (Button)findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(this);

        listView = (ListView)findViewById(R.id.foodList);


        List<Food>storedFood = Food.getAllFood();

    }

    public static List<Food> getAll() {
        return new Select("food_name")
                .from(Food.class)
                .orderBy("food_name ASC")
                .execute();
    }

    public void displayFood(View view){
        arrayList = new ArrayList<>();

        List<Food> foodList = getAll();

        for(int i=0;i<=foodList.size();i++){
         food = foodList.get(i);
         arrayList.add(food.getFoodName());
        }

        adapter = new ArrayAdapter<Food>(getApplicationContext(),android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        food = new Food();

        if(foodTxt.getText().equals("") || proteinTxt.getText().toString().equals("") || carbsTxt.getText().equals("") || fatTxt.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
        }
        else {

            String food_name = foodTxt.getText().toString();
            int protein = Integer.parseInt(proteinTxt.getText().toString());
            int carbs = Integer.parseInt(carbsTxt.getText().toString());
            int fat = Integer.parseInt(fatTxt.getText().toString());
            food.setFoodName(food_name);
            food.setProteinAmount(protein);
            food.setCarbsAmount(carbs);
            food.setFatAmount(fat);
            food.setCurrentDate();
            Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
            foodTxt.setText("");    proteinTxt.setText("");    carbsTxt.setText("");    fatTxt.setText("");

//        arrayList.add(foodTxt.getText().toString());
//        adapter.notifyDataSetChanged();

            food.save();
        }


        foodTxt.clearFocus();

    }
}
