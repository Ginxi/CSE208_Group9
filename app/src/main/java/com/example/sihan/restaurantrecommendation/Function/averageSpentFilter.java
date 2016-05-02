package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of averageSpentFilter here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
public class averageSpentFilter implements Filter{
    private int myAverageSpent;
    
    public averageSpentFilter(int averageSpent){
        myAverageSpent = averageSpent;
    }
    
    public boolean satisfies(String id){
        int averageSpent = RestaurantDatabase.getAverageSpent(id);
        return averageSpent <= myAverageSpent;
    }
}
