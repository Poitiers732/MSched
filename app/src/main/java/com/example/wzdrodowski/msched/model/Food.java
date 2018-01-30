package com.example.wzdrodowski.msched.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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
}
