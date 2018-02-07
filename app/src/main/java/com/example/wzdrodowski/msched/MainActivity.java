package com.example.wzdrodowski.msched;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.wzdrodowski.msched.model.Food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText foodTxt;
    EditText proteinTxt;   EditText carbsTxt;   EditText fatTxt;
    Button btnAddFood;
    Food food;
    ListView foodListView;
    ArrayList<Food> arrayList;
    ArrayAdapter<Food> adapter;
    Button btnDeleteAll;
    Calendar cld;
    String cldString;
    Button btnPrevious;
    Button btnNext;

    TextView calendar;

    //list_item
    ListView foodItem;
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
        foodItem = (ListView)findViewById(R.id.foodList);
        btnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);
        btnPrevious = (Button) findViewById(R.id.btnPrevious); btnNext = (Button) findViewById(R.id.btnNext);
        calendar = (TextView) findViewById(R.id.calendar);

        //displayFood();
        arrayList = new ArrayList<>();
        List<Food> foodList = getAll();

        for(int i=0; i<foodList.size(); i++){
            food = foodList.get(i);
            arrayList.add(food);
        }

        adapter = new Adapter(this, arrayList);

        foodItem.setAdapter(adapter);
        //

        //List<Food>storedFood = Food.getAll();

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAll();
                sortByName(view);
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setText(getPreviousDate());
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setText(getNextDate());
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setText(getCurrentDate());
            }
        });

        calendar.setText(getCurrentDate());

    }

    public String getPreviousDate(){
        cld.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(cld.getTime());
        cldString = currentTime;
        return currentTime;
    }

    public String getNextDate(){
        cld.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(cld.getTime());
        cldString = currentTime;
        return currentTime;
    }

    public String getCurrentDate(){
        cld = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(cld.getTime());
        cldString = currentTime;
        return currentTime;
    }

    public void clearFocuses(){
        foodTxt.clearFocus();
        proteinTxt.clearFocus();
        carbsTxt.clearFocus();
        fatTxt.clearFocus();
    }

    public static List<Food> getAll() {
        return new Select()
                .from(Food.class)
                .orderBy("food_name ASC")
                .execute();
    }

    public static List<Food> deleteAll() {
        return new Delete()
                .from(Food.class)
                .execute();
    }


     public static List<Food> getByDateDesc() {
        return new Select()
                .from(Food.class)
                .orderBy("date DESC")
                .execute();
    }

      public void sortByName(View view) {
        arrayList = new ArrayList<>();
        List<Food> foodList = getAll();

        for(int i=0; i<foodList.size(); i++){
            food = foodList.get(i);
            arrayList.add(food);
        }

        adapter = new Adapter(this, arrayList);
        foodItem.setAdapter(adapter);
    }

    public void sortByDate(View view) {
        arrayList = new ArrayList<>();
        List<Food> foodList = getByDateDesc();

        for(int i=0; i<foodList.size(); i++){
            food = foodList.get(i);
            arrayList.add(food);
        }

        adapter = new Adapter(this, arrayList);
        foodItem.setAdapter(adapter);
    }

    public void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View view) {
        hideKeyboard();
        food = new Food();
        boolean showToastWrongData = false;

        if(!proteinTxt.getText().toString().matches("-?\\d+")){
            showToastWrongData = true;
            proteinTxt.setText("");
        }

        if(!carbsTxt.getText().toString().matches("-?\\d+")){
            showToastWrongData = true;
            carbsTxt.setText("");
        }

        if(!fatTxt.getText().toString().matches("-?\\d+")){
            showToastWrongData = true;
            fatTxt.setText("");
        }

        if(foodTxt.getText().equals("") || proteinTxt.getText().toString().equals("") || carbsTxt.getText().equals("") || fatTxt.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
            showToastWrongData = false;
        }

        else if(showToastWrongData){
            Toast.makeText(getApplicationContext(), "Wrong data, use numbers", Toast.LENGTH_SHORT).show();
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

            food.save();
        }

        //displayFood();
        arrayList = new ArrayList<>();
        List<Food> foodList = getAll();

        for(int i=0; i<foodList.size(); i++){
            food = foodList.get(i);
            arrayList.add(food);
        }

        adapter = new Adapter(this, arrayList);
        foodItem.setAdapter(adapter);
        //

        clearFocuses();

    }
}
