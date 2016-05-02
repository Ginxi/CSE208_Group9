package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of ThirdRatings here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
import java.io.InputStream;
import java.util.*;
public class ThirdRatings {
    
    private ArrayList<User> myUsers;
    
    public ThirdRatings(){
        //default constructor
      // this( "CSE208Data2Short.csv");
    }
    
    public ThirdRatings(String ratingsfile, InputStream is){
        FirstRatings sample = new FirstRatings();
        
        myUsers = sample.loadUsers(ratingsfile, is);
    }
    
    public int getUserSize(){
        return myUsers.size();
    }
    
    private double getAverageEnvironByID(String id, int minimalUsers){
        FirstRatings userLocator = new FirstRatings();
        ArrayList<User> userList = userLocator.findRestaurant(myUsers, id);
        if(userList.size() < minimalUsers){
            return 0.0;
        }else{
            double sumOfRatings = 0.0;
            for(User currentRater: userList){
                double currentRating = currentRater.getEnvironRating(id);
                sumOfRatings += currentRating;
            }
            return sumOfRatings/userList.size();
        }
    }
    
    private double getAverageServiceByID(String id, int minimalUsers){
        FirstRatings userLocator = new FirstRatings();
        ArrayList<User> userList = userLocator.findRestaurant(myUsers, id);
        if(userList.size() < minimalUsers){
            return 0.0;
        }else{
            double sumOfRatings = 0.0;
            for(User currentRater: userList){
                double currentRating = currentRater.getServiceRating(id);
                sumOfRatings += currentRating;
            }
            return sumOfRatings/userList.size();
        }
    }
    
    private double getAverageFlavorByID(String id, int minimalUsers){
        FirstRatings userLocator = new FirstRatings();
        ArrayList<User> userList = userLocator.findRestaurant(myUsers, id);
        if(userList.size() < minimalUsers){
            return 0.0;
        }else{
            double sumOfRatings = 0.0;
            for(User currentRater: userList){
                double currentRating = currentRater.getFlavorRating(id);
                sumOfRatings += currentRating;
            }
            return sumOfRatings/userList.size();
        }
    }

    public ArrayList<Rating> getAverageRatings(int minimalUsers){
        ArrayList<Rating> output = new ArrayList<Rating>();
        ArrayList<String> restaurants = RestaurantDatabase.filterBy(new TrueFilter());
        for(String restaurantID: restaurants){
            
            double averageEnvironScores = getAverageEnvironByID(restaurantID, minimalUsers);
            double averageServiceScores = getAverageServiceByID(restaurantID, minimalUsers);
            double averageFlavorScores = getAverageFlavorByID(restaurantID, minimalUsers);
           
            output.add(new Rating(restaurantID, averageFlavorScores, averageEnvironScores, averageServiceScores));
        }
        ArrayList<Rating> finalOutput = new ArrayList<Rating>();
        for(Rating currentRating: output){
            if(currentRating.getEnvironValue()!= 0.0 || currentRating.getFlavorValue()!=0.0 ||
               currentRating.getServiceValue()!= 0.0){
                finalOutput.add(currentRating);
            }
        }
        return finalOutput;
    }    
    
    public ArrayList<Rating> getAverageRatingsByFilter(Filter f, int minimalUsers){
        ArrayList<Rating> output = new ArrayList<Rating>();
        ArrayList<String> restaurants = RestaurantDatabase.filterBy(f);
        for(String restaurantID: restaurants){
            
            double averageEnvironScores = getAverageEnvironByID(restaurantID, minimalUsers);
            double averageServiceScores = getAverageServiceByID(restaurantID, minimalUsers);
            double averageFlavorScores = getAverageFlavorByID(restaurantID, minimalUsers);
           
            output.add(new Rating(restaurantID, averageFlavorScores, averageEnvironScores, averageServiceScores));
        }
        ArrayList<Rating> finalOutput = new ArrayList<Rating>();
        for(Rating currentRating: output){
            if(currentRating.getEnvironValue()!= 0.0 || currentRating.getFlavorValue()!=0.0 ||
               currentRating.getServiceValue()!= 0.0){
                finalOutput.add(currentRating);
            }
        }
        return finalOutput;    
    }
    
    
}
