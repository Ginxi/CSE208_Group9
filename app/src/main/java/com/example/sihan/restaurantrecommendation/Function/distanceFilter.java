package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of distanceFilter here.
 *
 * @version (a version number or a date)
 * @di.yao_1301853 (your name)
 */
public class distanceFilter implements Filter {
    private int myDistance;

    public distanceFilter(int distance) {
        myDistance = distance;
    }

    public boolean satisfies(String id) {
        int distance = RestaurantDatabase.getDistance(id);
        return distance <= myDistance;
    }
}
