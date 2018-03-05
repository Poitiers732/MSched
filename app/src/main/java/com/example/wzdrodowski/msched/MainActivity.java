package com.example.wzdrodowski.msched;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.DatabaseHelper;
import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.wzdrodowski.msched.model.Food;
import com.example.wzdrodowski.msched.model.OnSwipeTouchListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteOpenHelper _openHelper;

    EditText foodTxt;
    EditText proteinTxt;   EditText carbsTxt;   EditText fatTxt;    EditText gramsTxt;
    Button btnAddFood;
    Food food;
    ArrayList<Food> arrayList;
    ArrayAdapter<Food> adapter;
    Button btnDeleteAll;
    Button btnDeleteDay;
    Calendar cld;
    String cldString;
    Button btnPrevious;
    Button btnNext;

    TextView calendar;
    TextView protTotal;
    TextView carbsTotal;
    TextView fatTotal;
    TextView caloriesTotal;

    //list_item
    ListView foodItem;
    TextView liName;
    TextView liKcal;

    Button btnSortByDate;
    Button btnSortByName;

    int proteinCounter = 0;
    int carbsCounter = 0;
    int fatCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveAndroid.initialize(this);
        foodTxt = (EditText)findViewById(R.id.foodTxt);
        proteinTxt = (EditText)findViewById(R.id.proteinTxt);
        carbsTxt = (EditText)findViewById(R.id.carbsTxt);
        fatTxt = (EditText)findViewById(R.id.fatTxt);
        gramsTxt = (EditText)findViewById(R.id.gramsTxt);
        btnAddFood = (Button)findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(this);
        foodItem = (ListView)findViewById(R.id.foodList);
        btnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);
        btnDeleteDay = (Button) findViewById(R.id.btnDeleteDay);
        btnSortByDate = (Button) findViewById(R.id.btnSortByDate);
        btnSortByName = (Button) findViewById(R.id.btnSortByName);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnNext = (Button) findViewById(R.id.btnNext);
        calendar = (TextView) findViewById(R.id.calendar);
        protTotal = (TextView) findViewById(R.id.protTotal);
        carbsTotal = (TextView) findViewById(R.id.carbsTotal);
        fatTotal = (TextView) findViewById(R.id.fatTotal);
        caloriesTotal = (TextView) findViewById(R.id.caloriesTotal);
        cldString = getCurrentDate();

        refreshList();

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAll();
                refreshList();
                Toast.makeText(getApplicationContext(), "All records deleted", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllDay(cldString);
                refreshList();
                Toast.makeText(getApplicationContext(), "All records from "+cldString+" deleted", Toast.LENGTH_SHORT).show();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setText(getPreviousDate());
                refreshList();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setText(getNextDate());
                refreshList();
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setText(getCurrentDate());
                refreshList();
            }
        });

        calendar.setText(getCurrentDate());

        foodItem.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v,int position, long id)
            {
                Food food = (Food)foodItem.getItemAtPosition(position);
                //Toast.makeText(MainActivity.this, "1" + foodItem.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, food.getFoodName(), Toast.LENGTH_SHORT).show();
                deleteRecord(food.getFoodName());
                refreshList();
            }
        });

//        foodItem.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
//
//            public void onSwipeTop() {
//                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeRight(AdapterView<?> adapter, View v, int position,
//                                     long id) {
//
//                Food food = (Food)foodItem.getItemAtPosition(position);
//                //Toast.makeText(MainActivity.this, "1" + foodItem.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, food.getFoodName(), Toast.LENGTH_SHORT).show();
//                deleteRecord(food.getFoodName());
//                refreshList();
//
//            }
//            public void onSwipeLeft() {
//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeBottom() {
//                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
//            }
//
//        });
    }

    public static void changeRecords(String str) {
        new Delete().from(Food.class).where("picked_date =?", str).execute();
    }

    public void refreshList() {
        arrayList = new ArrayList<>();
        List<Food> foodList = getAll();

        for(int i=0; i<foodList.size(); i++){
            try {
                if(foodList.get(i).getPickedDate().contains(cldString)) {
                food = foodList.get(i);
                arrayList.add(food);
                proteinCounter += food.getProteinAmount();
                carbsCounter += food.getCarbsAmount();
                fatCounter += food.getFatAmount();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protTotal.setText(Integer.toString(proteinCounter));
        carbsTotal.setText(Integer.toString(carbsCounter));
        fatTotal.setText(Integer.toString(fatCounter));
        int caloriesSum = 4*(proteinCounter + carbsCounter) + 9*fatCounter;
        caloriesTotal.setText("TOTAL KCAL:\n " + Integer.toString(caloriesSum));

        proteinCounter = 0;
        carbsCounter = 0;
        fatCounter = 0;

        adapter = new Adapter(this, arrayList);
        foodItem.setAdapter(adapter);

        btnSortByDate.setTextColor(Color.parseColor("#ffffff"));
        btnSortByName.setTextColor(Color.parseColor("#ffffff"));
        calendar.setTextColor(getResources().getColor(R.color.colorAccent));
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

        public static void deleteAllDay(String str) {
            new Delete().from(Food.class).where("picked_date =?", str).execute();
    }

            public static void deleteRecord(String str) {
            new Delete().from(Food.class).where("food_name =?", str).execute();
    }

     public static List<Food> getByDateDesc() {
        return new Select()
                .from(Food.class)
                .orderBy("picked_date DESC, date DESC")
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

        btnSortByName.setTextColor(getResources().getColor(R.color.colorAccent));
        btnSortByDate.setTextColor(Color.parseColor("#ffffff"));
        calendar.setTextColor(Color.parseColor("#ffffff"));
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

        btnSortByDate.setTextColor(getResources().getColor(R.color.colorAccent));
        btnSortByName.setTextColor(Color.parseColor("#ffffff"));
        calendar.setTextColor(Color.parseColor("#ffffff"));
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

        if(foodTxt.getText().equals("") || gramsTxt.getText().equals("") || proteinTxt.getText().toString().equals("") || carbsTxt.getText().equals("") || fatTxt.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
        }

        else {
            try {
                food = new Food();
                String food_name = foodTxt.getText().toString();
                int protein = Integer.parseInt(proteinTxt.getText().toString());
                int carbs = Integer.parseInt(carbsTxt.getText().toString());
                int fat = Integer.parseInt(fatTxt.getText().toString());
                int grams = Integer.parseInt(gramsTxt.getText().toString());
                food.setFoodName(food_name);
                food.setGrams(grams);
                food.setProteinAmount(protein*grams/100);
                food.setCarbsAmount(carbs*grams/100);
                food.setFatAmount(fat*grams/100);
                food.setCurrentDate();
                food.setPickedDate(calendar.getText().toString());
                Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
                foodTxt.setText("");
                proteinTxt.setText("");
                carbsTxt.setText("");
                fatTxt.setText("");
                gramsTxt.setText("");

                food.save();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        refreshList();
        clearFocuses();
    }
}



