package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of Rating here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
public class Rating implements Comparable<Rating> {
    private String item;
    private double flavorValue;
    private double environValue;
    private double serviceValue; 

    public Rating (String anItem, double value1, double value2, double value3) {
        item = anItem;
        flavorValue = value1;
        environValue = value2;
        serviceValue = value3;
    }

    // Returns item being rated
    public String getItem () {
        return item;
    }

    // Returns the value of this rating (as a number so it can be used in calculations)
    public double getFlavorValue () {
        return flavorValue;
    }
    
    public double getEnvironValue () {
        return environValue;
    }
    
    public double getServiceValue () {
        return serviceValue;
    }
    
    public double getAverageValue(){
        return (flavorValue+environValue+serviceValue)/3;
    }
    // Returns a string of all the rating information
    public String toString () {
        return "[" + getItem() + ", falvor: " + getFlavorValue() +", environment: " + getEnvironValue() + ", service: "+ getServiceValue() + "]";
    }

    public int compareTo(Rating other) {
        double averageOfCurrent = this.getAverageValue();
        double averageOfOther = other.getAverageValue();
        if (averageOfCurrent < averageOfOther) return -1;
        if (averageOfCurrent > averageOfOther) return 1;
        
        return 0; 
    }
}
