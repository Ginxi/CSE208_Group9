package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of environScoreFilter here.
 *
 * @version (a version number or a date)
 * @di.yao_1301853 (your name)
 */
public class environScoreFilter implements Filter {
    private double myEnvironScore;

    public environScoreFilter(double environScore) {
        myEnvironScore = environScore;
    }

    public boolean satisfies(String id) {
        double environScore = RestaurantDatabase.getEnvironmentScore(id);
        return environScore >= myEnvironScore;
    }
}
