package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of addressFilter here.
 *
 * @version (a version number or a date)
 * @di.yao_1301853 (your name)
 */
public class addressFilter implements Filter {
    private String myPlace;

    public addressFilter(String place) {
        myPlace = place;
    }

    public boolean satisfies(String id) {
        String address = RestaurantDatabase.getAddress(id);
        return address.indexOf(myPlace) != -1;
    }
}
