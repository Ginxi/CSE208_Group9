package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of User here.
 *
 * @di.yao_1301853 (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public interface User {
    void addRating(String rest_id, double value1, double value2, double value3);

    boolean hasRating(String rest_id);

    String getID();

    String getName();

    double getFlavorRating(String rest_id);

    double getEnvironRating(String rest_id);

    double getServiceRating(String rest_id);

    double getAverageRating(String rest_id);

    int numRatings();

    ArrayList<String> getIDOfRestaurantRated();
}
