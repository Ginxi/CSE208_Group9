package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of environScoreFilter here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
public class environScoreFilter implements Filter {
    private double myEnvironScore;
    
    public environScoreFilter(double environScore){
        myEnvironScore = environScore;
    }
    
    public boolean satisfies(String id){
        double environScore = RestaurantDatabase.getEnvironmentScore(id);
        return environScore >= myEnvironScore;
    }
}
