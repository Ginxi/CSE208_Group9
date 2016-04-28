package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of RestaurantDatabase here.
 *
 * @di.yao_130185 (your name)
 * @version (a version number or a date)
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantDatabase {
    private static HashMap<String, Restaurant> ourRestaurants;

    public static void initialize(String restaurantFile, InputStream is) {
        if (ourRestaurants == null) {
            ourRestaurants = new HashMap<String, Restaurant>();
            loadRestaurants(restaurantFile, is);
        }
    }

    private static void initialize() {
//        if(ourRestaurants == null){
//            ourRestaurants = new HashMap<String, Restaurant>();
//            loadRestaurants("raw/cse208data1short.csv");
//        }
    }

    public static void loadRestaurants(String fileName, InputStream is) {
        FirstRatings fr = new FirstRatings();
        ArrayList<Restaurant> list = fr.loadRestaurants(fileName, is);
        for (Restaurant r : list) {
            ourRestaurants.put(r.getID(), r);
        }
    }

    private static boolean containsID(String id) {
        initialize();
        return ourRestaurants.containsKey(id);
    }

    public static String getTitle(String id) {
        initialize();
        //  return ourRestaurants.get(id).getTitle();
        return ourRestaurants.get(id).getTitle();
    }

    public static int getAverageSpent(String id) {
        initialize();
        //   return ourRestaurants.get(id).getAverageSpent();
        return ourRestaurants.get(id).getAverageSpent();
    }

    public static String getCategories(String id) {
        initialize();
        //return ourRestaurants.get(id).getCategories();
        return ourRestaurants.get(id).getCategories();
    }

    public static double getEnvironmentScore(String id) {
        initialize();
        // return ourRestaurants.get(id).getEnvironmentScore();
        return ourRestaurants.get(id).getEnvironmentScore();
    }

    public static double getServiceScore(String id) {
        initialize();
        // return ourRestaurants.get(id).getServiceScore();
        return ourRestaurants.get(id).getServiceScore();
    }

    public static double getFlavorScore(String id) {
        initialize();
        // return ourRestaurants.get(id).getFlavorScore();
        return ourRestaurants.get(id).getFlavorScore();
    }

    public static String getPhoneNumber(String id) {
        initialize();
        // return ourRestaurants.get(id).getPhoneNumber();
        return ourRestaurants.get(id).getPhoneNumber();
    }

    public static String getAddress(String id) {
        initialize();
        //   return ourRestaurants.get(id).getAddress();
        return ourRestaurants.get(id).getAddress();
    }

    public static int getDistance(String id) {
        initialize();
        //  return ourRestaurants.get(id).getDistance();
        return ourRestaurants.get(id).getDistance();
    }

    public static int size() {
        return ourRestaurants.size();
    }

    public static ArrayList<String> filterBy(Filter f) {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for (String id : ourRestaurants.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }

        return list;
    }
}
