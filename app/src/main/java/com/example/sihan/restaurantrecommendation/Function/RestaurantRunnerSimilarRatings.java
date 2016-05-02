package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of RestaurantRunnerSimilarRatings here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class RestaurantRunnerSimilarRatings {
        public void printSimilarRatingsByCategories(){
        String userFile = "CSE208Data2Short.csv";
        String restaurantFile = "raw/cse208data1short.csv";
        FourthRatings example = new FourthRatings();
       // UserDatabase.initialize(userFile);
        System.out.println("The number of users in "+userFile+": "+UserDatabase.size());
      //  RestaurantDatabase.initialize(restaurantFile);
        System.out.println("The number of restaurants in RestaurantDatabase is: "+RestaurantDatabase.size());
        
        //Filter f = new categoriesFilter("South Korean dish");
        Filter f = new distanceFilter(1000);
        ArrayList<Rating> output = example.getSimilarRatingsByFilter("1", 7, 2,f);
        for(Rating currentRating: output){
            System.out.println(RestaurantDatabase.getTitle(currentRating.getItem())+" "+currentRating.getAverageValue());
           
        }    
        System.out.println(" ");
    }
}
