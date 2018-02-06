package com.example.wzdrodowski.msched;

/**
 * Created by wzdrodowski on 2018-02-01.
 */

public class Item {
    private String food_name;
    private int protein;
    private int carbs;
    private int fat;
    private String date;

    public Item(String n, int p, int c,int f, String d) {
        food_name = n;
        protein = p;
        carbs = c;
        fat = f;
        date = d;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFood_name() {
        return food_name;

    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
}
