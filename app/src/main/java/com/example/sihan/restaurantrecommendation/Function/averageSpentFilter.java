package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of averageSpentFilter here.
 *
 * @version (a version number or a date)
 * @di.yao_1301853 (your name)
 */
public class averageSpentFilter implements Filter {
    private int myAverageSpent;

    public averageSpentFilter(int averageSpent) {
        myAverageSpent = averageSpent;
    }

    public boolean satisfies(String id) {
        int averageSpent = RestaurantDatabase.getAverageSpent(id);
        return averageSpent <= myAverageSpent;
    }
}
