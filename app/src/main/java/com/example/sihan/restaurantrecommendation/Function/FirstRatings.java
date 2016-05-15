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
        if (title.indexOf(targetWords) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean containWantedPlace(Restaurant input, String targetPlace) {
        String address = input.getAddress();
        if (address.indexOf(targetPlace) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean containWantedEnvironScore(Restaurant input, double targetScore) {
        double environScore = input.getEnvironmentScore();
        if (environScore >= targetScore) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containWantedServiceScore(Restaurant input, double targetScore) {
        double serviceScore = input.getServiceScore();
        if (serviceScore >= targetScore) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containWantedFlavorScore(Restaurant input, double targetScore) {
        double flavorScore = input.getFlavorScore();
        if (flavorScore >= targetScore) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containWantedCategories(Restaurant input, String targetCategories) {
        String category = input.getCategories();
        if (category.indexOf(targetCategories) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean cotainWantedDistances(Restaurant input, int targetDistances) {
        int distances = input.getDistance();
        if (distances <= targetDistances) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containWantedAveraegSpent(Restaurant input, int targetScore) {
        int averageSpent = input.getAverageSpent();
        if (averageSpent <= targetScore) {
            return true;
        } else {
            return false;
        }
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

    public ArrayList<EfficientUser> loadUsers(String fileName, InputStream is) {
        ArrayList<EfficientUser> allUsers = new ArrayList<EfficientUser>();
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
            // changed
            String account = current.get("account");
            String password = current.get("password");

            EfficientUser newUser = new EfficientUser(user_id, userName, account, password);
            newUser.addRating(restaurant_id, flavorScore, environmentScore, serviceScore);

            if (!storeID.contains(user_id)) {
                storeID.add(user_id);
                allUsers.add(newUser);
            } else {
                for (EfficientUser currentUser : allUsers) {
                    String currentID = currentUser.getID();
                    if (currentID.equals(user_id)) {
                        currentUser.addRating(restaurant_id, flavorScore, environmentScore, serviceScore);
                    }
                }
            }
        }
        return allUsers;
    }

    public ArrayList<EfficientUser> findMaxNumRatings(ArrayList<EfficientUser> allUsers) {
        ArrayList<EfficientUser> output = new ArrayList<EfficientUser>();
        int maxNum = 0;
        for (EfficientUser currentUser : allUsers) {
            int numRatings = currentUser.numRatings();
            if (numRatings >= maxNum) {
                maxNum = numRatings;
            }
        }

        for (EfficientUser currentUser : allUsers) {
            int numRatings = currentUser.numRatings();
            if (numRatings == maxNum) {
                output.add(currentUser);
            }
        }

        return output;
    }

    public ArrayList<EfficientUser> findRestaurant(ArrayList<EfficientUser> allUsers, String rest_id) {
        ArrayList<EfficientUser> findRestaurant = new ArrayList<EfficientUser>();
        for (EfficientUser currentUser : allUsers) {
            if (currentUser.getAverageRating(rest_id) != -1) {
                findRestaurant.add(currentUser);
            }
        }
        return findRestaurant;
    }

    public int restaurantNum(ArrayList<EfficientUser> allUsers) {
        ArrayList<String> restaurantList = new ArrayList<String>();
        for (EfficientUser currentUser : allUsers) {
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
        String filename = "cse208Data2Short.csv";
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        ArrayList<EfficientUser> output = loadUsers(filename, is);
        System.out.println("The number of users in " + filename + ": " + output.size());

        for (EfficientUser current : output) {
            System.out.println("ID: " + current.getID());
            System.out.println("Number of rated restaurants: " + current.numRatings());
        }

        System.out.println(" ");
        String rest_id = "3";
        ArrayList<EfficientUser> findRestaurants = findRestaurant(output, rest_id);
        System.out.println("The restaurant ID: " + rest_id);
        System.out.println("The number of users score this restaurant: " + findRestaurants.size());

        System.out.println(" ");
        int number = restaurantNum(output);
        System.out.println("The number of restaurants scored: " + number);
    }
}
