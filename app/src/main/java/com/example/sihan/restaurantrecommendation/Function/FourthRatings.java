package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of FourthRatings here.
 *
 * @di.yao_1301953 (your name)
 * @version (a version number or a date)
 */
//changed
import java.util.*;
public class FourthRatings {

    private double getAverageEnvironByID(String id, int minimalUsers){
        FirstRatings userLocator = new FirstRatings();
        ArrayList<EfficientUser> userList = userLocator.findRestaurant(UserDatabase.getUsers(), id);
        if(userList.size() < minimalUsers){
            return 0.0;
        }else{
            double sumOfRatings = 0.0;
            for(EfficientUser currentRater: userList){
                double currentRating = currentRater.getEnvironRating(id);
                sumOfRatings += currentRating;
            }
            return sumOfRatings/userList.size();
        }
    }

    private double getAverageServiceByID(String id, int minimalUsers){
        FirstRatings userLocator = new FirstRatings();
        ArrayList<EfficientUser> userList = userLocator.findRestaurant(UserDatabase.getUsers(), id);
        if(userList.size() < minimalUsers){
            return 0.0;
        }else{
            double sumOfRatings = 0.0;
            for(EfficientUser currentRater: userList){
                double currentRating = currentRater.getServiceRating(id);
                sumOfRatings += currentRating;
            }
            return sumOfRatings/userList.size();
        }
    }

    private double getAverageFlavorByID(String id, int minimalUsers){
        FirstRatings userLocator = new FirstRatings();
        ArrayList<EfficientUser> userList = userLocator.findRestaurant(UserDatabase.getUsers(), id);
        if(userList.size() < minimalUsers){
            return 0.0;
        }else{
            double sumOfRatings = 0.0;
            for(EfficientUser currentRater: userList){
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

    private int dotProduct(EfficientUser me, EfficientUser r){
        ArrayList<String> restItemOfMe = me.getIDOfRestaurantRated();
        ArrayList<String> restItemOfR = r.getIDOfRestaurantRated();
        EfficientUser copyOfMe = new EfficientUser();
        EfficientUser copyOfR = new EfficientUser();

        for(String currentItem: restItemOfMe){
            double currentRating = me.getAverageRating(currentItem)-5;
            copyOfMe.addRating(currentItem, currentRating, currentRating, currentRating);
        }

        for(String currentItem: restItemOfR){
            double currentRating = r.getAverageRating(currentItem)-5;
            copyOfR.addRating(currentItem, currentRating,currentRating, currentRating);
        }

        int output = 0;
        for(String restItem: restItemOfMe){
            if(copyOfR.hasRating(restItem)){
                output += copyOfMe.getAverageRating(restItem)*copyOfR.getAverageRating(restItem);
            }
        }
        return output;
    }

    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> list = new ArrayList<Rating>();
        EfficientUser me = UserDatabase.getUser(id);
        for(EfficientUser r: UserDatabase.getUsers()){
            if(!r.getID().equals(id)){
                int product = dotProduct(me, r);
                if(product > 0){
                    list.add(new Rating(r.getID(),product,product,product));
                }
            }
        }
        Collections.sort(list, new Comparator<Rating>(){
                            public int compare(Rating r1, Rating r2){
                                return Double.valueOf(r1.getAverageValue()).compareTo(Double.valueOf(r2.getAverageValue()));
                            }
        });
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarUsers, int minimalUsers){
        ArrayList<Rating> list = getSimilarities(id);
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<String> restaurantID = getRestaurant(list, numSimilarUsers, minimalUsers);
        for(String currentRestaurantID: restaurantID){
            int number = 0;
            double newRatings = 0.0;
            for(int k = 0; k < numSimilarUsers; k++){
                Rating r = list.get(k);
                double weight = r.getAverageValue();
                EfficientUser currentUser = UserDatabase.getUser(r.getItem());
                if(currentUser.getIDOfRestaurantRated().indexOf(currentRestaurantID) != -1){
                    newRatings += weight*currentUser.getAverageRating(currentRestaurantID);
                    number += 1;
                }
            }
            ret.add(new Rating (currentRestaurantID, newRatings/number,newRatings/number,newRatings/number));
        }

        Collections.sort(ret, new Comparator<Rating>(){
                            public int compare(Rating r1, Rating r2){
                                return Double.valueOf(r1.getAverageValue()).compareTo(Double.valueOf(r2.getAverageValue()));
                            }
        });
        Collections.sort(ret, Collections.reverseOrder());
        return ret;
    }

        public ArrayList<String> getRestaurant( ArrayList<Rating> list, int numSimilarUsers,  int minimalUsers){
        ArrayList<EfficientUser> users = new ArrayList<EfficientUser>();
        for(int k = 0; k < numSimilarUsers; k++){
            Rating currentRating = list.get(k);
            String userID = currentRating.getItem();
            users.add(UserDatabase.getUser(userID));
        }

        HashMap<String, Integer> allRestaurants = new HashMap<String, Integer>();
        for(EfficientUser currentUser: users){
            ArrayList<String> restaurantItems = currentUser.getIDOfRestaurantRated();
            for(String currentRestaurantID: restaurantItems){
                if(!allRestaurants.containsKey(currentRestaurantID)){
                    allRestaurants.put(currentRestaurantID, 1);
                }else{
                    allRestaurants.put(currentRestaurantID, allRestaurants.get(currentRestaurantID)+1);
                }
            }
        }

        ArrayList<String> restaurantID = new ArrayList<String>();
        for(String restaurantItem: allRestaurants.keySet()){
            if(allRestaurants.get(restaurantItem) >= minimalUsers){
                restaurantID.add(restaurantItem);
            }
        }

        return restaurantID;
    }

        public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarUsers, int minimalUsers, Filter f){
        ArrayList<Rating> list = getSimilarities(id);
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<String> restaurantID2 = getRestaurant(list, numSimilarUsers, minimalUsers);
        ArrayList<String> allFilteredRestaurantID = RestaurantDatabase.filterBy(f);
        ArrayList<String> restaurantID = new ArrayList<String>();

        for(String currentID: restaurantID2){
            if(allFilteredRestaurantID.indexOf(currentID) != -1){
                restaurantID.add(currentID);
            }
        }

        for(String currentRestaurantID: restaurantID){
            int number = 0;
            double newRatings = 0.0;
            for(int k = 0; k < numSimilarUsers; k++){
                Rating r = list.get(k);
                double weight = r.getAverageValue();
                EfficientUser currentUser = UserDatabase.getUser(r.getItem());
                if(currentUser.getIDOfRestaurantRated().indexOf(currentRestaurantID) != -1){
                    newRatings += weight*currentUser.getAverageRating(currentRestaurantID);
                    number += 1;
                }
            }
            ret.add(new Rating (currentRestaurantID, newRatings/number,newRatings/number,newRatings/number));
        }

        Collections.sort(ret, new Comparator<Rating>(){
                            public int compare(Rating r1, Rating r2){
                                return Double.valueOf(r1.getAverageValue()).compareTo(Double.valueOf(r2.getAverageValue()));
                            }
        });
        Collections.sort(ret, Collections.reverseOrder());
        return ret;
    }

}
