package com.example.coffeediaries;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CoffeeRecordsEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;

    @ColumnInfo(name = "brewDate")
    private String brewDate;

    @ColumnInfo(name = "brewMethod")
    private String brewMethod;

    @ColumnInfo(name = "coffeeName")
    private String coffeeName;

    @ColumnInfo(name = "brewTime")
    private String brewTime;

    @ColumnInfo(name = "gramsCoffee")
    private double gramsCoffee;

    @ColumnInfo(name = "calories")
    private double calories;

    @ColumnInfo(name = "rating")
    private double rating;

    @ColumnInfo(name = "comment")
    private String comment;

    public CoffeeRecordsEntity(String brewDate, String brewMethod, String coffeeName, String brewTime, double gramsCoffee, double calories, double rating, String comment) {
        this.id = 0;
        this.brewDate = brewDate;
        this.brewMethod = brewMethod;
        this.coffeeName = coffeeName;
        this.brewTime = brewTime;
        this.gramsCoffee = gramsCoffee;
        this.calories = calories;
        this.rating = rating;
        this.comment = comment;
    }

//    getters

    public int getId() {
        return id;
    }

    public String getBrewDate() {
        return brewDate;
    }

    public String getBrewMethod() {
        return brewMethod;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public String getBrewTime() {
        return brewTime;
    }

    public double getGramsCoffee() {
        return gramsCoffee;
    }

    public double getCalories() {
        return calories;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

//    setters
    public void setId(int id) { this.id = id; }
    public void setBrewDate(String brewDate) {
        this.brewDate = brewDate;
    }

    public void setBrewMethod(String brewMethod) {
        this.brewMethod = brewMethod;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public void setBrewTime(String brewTime) {
        this.brewTime = brewTime;
    }

    public void setGramsCoffee(double gramsCoffee) {
        this.gramsCoffee = gramsCoffee;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
