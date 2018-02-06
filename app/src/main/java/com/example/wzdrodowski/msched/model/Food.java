package com.example.wzdrodowski.msched.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Table(name = "food_table")

public class Food extends Model {

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "protein")
    private int proteinAmount;

    @Column(name = "carbs")
    private int carbsAmount;

    @Column(name = "fat")
    private int fatAmount;

    @Column(name = "date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(c.getTime());
        this.date = currentTime;
    }

    public int getProteinAmount() {
        return proteinAmount;
    }

    public void setProteinAmount(int proteinAmount) {
        this.proteinAmount = proteinAmount;
    }

    public int getCarbsAmount() {
        return carbsAmount;
    }

    public void setCarbsAmount(int carbsAmount) {
        this.carbsAmount = carbsAmount;
    }

    public int getFatAmount() {
        return fatAmount;
    }

    public void setFatAmount(int fatAmount) {
        this.fatAmount = fatAmount;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public static List<Food>getAllFood(){
        return new Select("food_name").from(Food.class).execute();
    }

}
