package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of User here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public interface User {
    public void addRating(String rest_id, double value1, double value2, double value3);
    
    public boolean hasRating(String rest_id);
    
    public String getID();
    
    public String getName();
    
    public double getFlavorRating(String rest_id);
    
    public double getEnvironRating(String rest_id);
    
    public double getServiceRating(String rest_id);
    
    public double getAverageRating(String rest_id);
    
    public int numRatings();
    
    public ArrayList<String> getIDOfRestaurantRated();
}
