package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of flavorScore here.
 *
 * @version (a version number or a date)
 * @di.yao_1301853 (your name)
 */
public class flavorScoreFilter implements Filter {
    private double myFlavorScore;

    public flavorScoreFilter(double flavorScore) {
        myFlavorScore = flavorScore;
    }

    public boolean satisfies(String id) {
        double flavorScore = RestaurantDatabase.getFlavorScore(id);
        return flavorScore >= myFlavorScore;
    }
}
