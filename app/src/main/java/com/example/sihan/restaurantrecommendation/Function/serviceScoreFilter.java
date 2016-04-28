package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of serviceScore here.
 *
 * @version (a version number or a date)
 * @di.yao_1301853 (your name)
 */
public class serviceScoreFilter implements Filter {
    private double myServiceScore;

    public serviceScoreFilter(double serviceScore) {
        myServiceScore = serviceScore;
    }

    public boolean satisfies(String id) {
        double serviceScore = RestaurantDatabase.getServiceScore(id);
        return serviceScore >= myServiceScore;
    }
}
