package com.example.sihan.restaurantrecommendation.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ginxi on 2016/4/20.
 */
public class SortRestaurant {

    public void sortRest(String str,ArrayList<Restaurant> rList ) {
        boolean fromMaxToMin = false;
        ArrayList<Double> list = new ArrayList<Double>();
        ArrayList<Double> tr = new ArrayList<Double>();
        ArrayList<Restaurant> temp = new  ArrayList<Restaurant>();
        temp = (ArrayList<Restaurant>) rList.clone();
        switch(str){
            case "Ascending":
                Collections.sort(rList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant lhs, Restaurant rhs) {
                        return lhs.getAverageSpent() - rhs.getAverageSpent();
                    }
                });
                break;
            case "Descending":
                Collections.sort(rList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant lhs, Restaurant rhs) {
                        return rhs.getAverageSpent() - lhs.getAverageSpent();
                    }
                });
                break;
            case "Nearest":
                Collections.sort(rList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant lhs, Restaurant rhs) {
                        return lhs.getDistance() - rhs.getDistance();
                    }
                });
                break;
            case "Furthest":
                Collections.sort(rList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant lhs, Restaurant rhs) {
                        return rhs.getDistance() - lhs.getDistance();
                    }
                });
                break;
            case "Service":
                Collections.sort(rList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant lhs, Restaurant rhs) {
                        return (int) (rhs.getServiceScore()*10 - lhs.getServiceScore()*10);
                    }
                });
                break;
            case "Flavor":
                Collections.sort(rList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant lhs, Restaurant rhs) {
                        return (int) (rhs.getFlavorScore()*10 - lhs.getFlavorScore()*10);
                    }
                });
                break;
            case "Environment":
                Collections.sort(rList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant lhs, Restaurant rhs) {
                        return (int) (rhs.getEnvironmentScore()*10 - lhs.getEnvironmentScore()*10);
                    }
                });
                break;
            case "Total":
                Collections.sort(rList, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant lhs, Restaurant rhs) {
                        return (int) ((rhs.getServiceScore() + rhs.getEnvironmentScore() + rhs.getFlavorScore())*10 - (lhs.getServiceScore() + lhs.getEnvironmentScore() + lhs.getFlavorScore())*10);
                    }
                });
                break;
        }
    }

}
