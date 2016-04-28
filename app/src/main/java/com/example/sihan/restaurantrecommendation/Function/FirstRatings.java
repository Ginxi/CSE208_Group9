package com.example.sihan.restaurantrecommendation.Function;

/**
 * Write a description of FirstRatings here.
 *
 * @di.yao_1301853 (your name)
 * @version (a version number or a date)
 */

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class FirstRatings {

    public ArrayList<Restaurant> loadRestaurants(String filename, InputStream is) {
        ArrayList<Restaurant> allRestaurants = new ArrayList<Restaurant>();
        FileResource fr = new FileResource(filename, is);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord current : parser) {
            String id = current.get("id");
            String title = current.get("title");
            int averageSpent = Integer.parseInt(current.get("averageSpent"));
            String categories = current.get("categories");
            double environmentScore = Double.parseDouble(current.get("environmentScore"));
            double serviceScore = Double.parseDouble(current.get("serviceScore"));
            double flavorScore = Double.parseDouble(current.get("flavorScore"));
            String phoneNumber = current.get("phoneNumber");
            String address = current.get("address");
            int distance = Integer.parseInt(current.get("distance"));

            Restaurant newRestaurant = new Restaurant(id, title, averageSpent, categories, environmentScore, serviceScore, flavorScore, phoneNumber, address, distance);
            allRestaurants.add(newRestaurant);
        }
        return allRestaurants;
    }

    public boolean containWantedWords(Restaurant input, String targetWords) {
        String title = input.getTitle();
        return title.indexOf(targetWords) != -1;
    }

    public boolean containWantedPlace(Restaurant input, String targetPlace) {
        String address = input.getAddress();
        return address.indexOf(targetPlace) != -1;
    }

    public boolean containWantedEnvironScore(Restaurant input, double targetScore) {
        double environScore = input.getEnvironmentScore();
        return environScore >= targetScore;
    }

    public boolean containWantedServiceScore(Restaurant input, double targetScore) {
        double serviceScore = input.getServiceScore();
        return serviceScore >= targetScore;
    }

    public boolean containWantedFlavorScore(Restaurant input, double targetScore) {
        double flavorScore = input.getFlavorScore();
        return flavorScore >= targetScore;
    }

    public boolean containWantedCategories(Restaurant input, String targetCategories) {
        String category = input.getCategories();
        return category.indexOf(targetCategories) != -1;
    }

    public boolean cotainWantedDistances(Restaurant input, int targetDistances) {
        int distances = input.getDistance();
        return distances <= targetDistances;
    }

    public boolean containWantedAveraegSpent(Restaurant input, int targetScore) {
        int averageSpent = input.getAverageSpent();
        return averageSpent <= targetScore;
    }

    public HashMap<String, Integer> phoneNumberMap(ArrayList<Restaurant> input) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Restaurant current : input) {
            String phoneNumbers = current.getPhoneNumber();
            if (phoneNumbers.indexOf("/") != -1) {
                String[] parts = phoneNumbers.split("/ ");
                for (int i = 0; i < parts.length; i++) {
                    if (map.containsKey(parts[i])) {
                        map.put(parts[i], map.get(parts[i]) + 1);
                    } else {
                        map.put(parts[i], 1);
                    }
                }
            } else if (map.containsKey(phoneNumbers)) {
                map.put(phoneNumbers, map.get(phoneNumbers) + 1);
            } else {
                map.put(phoneNumbers, 1);
            }
        }
        return map;
    }
// changed func

    public ArrayList<Restaurant> wantedRestaurants(String word, int averageSpent, String categories,
                                                   String place, int distances, InputStream is) {
        String filename = "raw/cse208data1short.csv";
        ArrayList<Restaurant> output = loadRestaurants(filename, is);
        System.out.println("The number of restaurant in " + filename + ": " + output.size());
        ArrayList<Restaurant> wanted = new ArrayList<Restaurant>();
        for (Restaurant current : output) {
            if (containWantedCategories(current, categories) && cotainWantedDistances(current, distances) && containWantedWords(current, word)
                    && containWantedPlace(current, place) && containWantedAveraegSpent(current, averageSpent)
                    ) {
                wanted.add(current);
            }
        }
        return wanted;
    }

    public ArrayList<User> loadUsers(String fileName, InputStream is) {
        ArrayList<User> allUsers = new ArrayList<User>();
        ArrayList<String> storeID = new ArrayList<String>();
        FileResource fr = new FileResource(fileName, is);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord current : parser) {
            String user_id = current.get("user_id");
            String userName = current.get("userName");
            String restaurant_id = current.get("restaurant_id");
            double flavorScore = Double.parseDouble(current.get("flavorScore"));
            double environmentScore = Double.parseDouble(current.get("environmentScore"));
            double serviceScore = Double.parseDouble(current.get("serviceScore"));

            User newUser = new EfficientUser(user_id, userName);
            newUser.addRating(restaurant_id, flavorScore, environmentScore, serviceScore);

            if (!storeID.contains(user_id)) {
                storeID.add(user_id);
                allUsers.add(newUser);
            } else {
                for (User currentUser : allUsers) {
                    String currentID = currentUser.getID();
                    if (currentID.equals(user_id)) {
                        currentUser.addRating(restaurant_id, flavorScore, environmentScore, serviceScore);
                    }
                }
            }
        }
        return allUsers;
    }

    public ArrayList<User> findMaxNumRatings(ArrayList<User> allUsers) {
        ArrayList<User> output = new ArrayList<User>();
        int maxNum = 0;
        for (User currentUser : allUsers) {
            int numRatings = currentUser.numRatings();
            if (numRatings >= maxNum) {
                maxNum = numRatings;
            }
        }

        for (User currentUser : allUsers) {
            int numRatings = currentUser.numRatings();
            if (numRatings == maxNum) {
                output.add(currentUser);
            }
        }

        return output;
    }

    public ArrayList<User> findRestaurant(ArrayList<User> allUsers, String rest_id) {
        ArrayList<User> findRestaurant = new ArrayList<User>();
        for (User currentUser : allUsers) {
            if (currentUser.getAverageRating(rest_id) != -1) {
                findRestaurant.add(currentUser);
            }
        }
        return findRestaurant;
    }

    public int restaurantNum(ArrayList<User> allUsers) {
        ArrayList<String> restaurantList = new ArrayList<String>();
        for (User currentUser : allUsers) {
            ArrayList<String> list = currentUser.getIDOfRestaurantRated();
            for (String restaurant : list) {
                if (!restaurantList.contains(restaurant)) {
                    restaurantList.add(restaurant);
                }
            }
        }
        return restaurantList.size();
    }

    public void testLoadUsers() {
        String filename = "CSE208Data2Short.csv";
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        ArrayList<User> output = loadUsers(filename, is);
        System.out.println("The number of users in " + filename + ": " + output.size());

        for (User current : output) {
            System.out.println("ID: " + current.getID());
            System.out.println("Number of rated restaurants: " + current.numRatings());
        }

        System.out.println(" ");
        String rest_id = "3";
        ArrayList<User> findRestaurants = findRestaurant(output, rest_id);
        System.out.println("The restaurant ID: " + rest_id);
        System.out.println("The number of users score this restaurant: " + findRestaurants.size());

        System.out.println(" ");
        int number = restaurantNum(output);
        System.out.println("The number of restaurants scored: " + number);
    }
}
