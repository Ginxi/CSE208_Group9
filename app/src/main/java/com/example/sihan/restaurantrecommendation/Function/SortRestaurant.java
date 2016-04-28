package com.example.sihan.restaurantrecommendation.Function;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ginxi on 2016/4/20.
 */
public class SortRestaurant {
    private ArrayList<Restaurant> rList;

    public SortRestaurant(ArrayList<Restaurant> r) {
        rList = r;
    }

    public void sortRest(String str) {
        ArrayList<Double> list = new ArrayList<Double>();
        ArrayList<Double> tr = new ArrayList<Double>();
        ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
        temp = (ArrayList<Restaurant>) rList.clone();
        switch (str) {
            case "Service":
                for (Restaurant res1 : temp) {
                    list.add(res1.getServiceScore());
                    tr.add(res1.getServiceScore());
                }
                break;
            case "Environment":
                for (Restaurant res1 : temp) {
                    list.add(res1.getEnvironmentScore());
                    tr.add(res1.getEnvironmentScore());
                }
                break;
            case "Flavor":
                for (Restaurant res1 : temp) {
                    list.add(res1.getFlavorScore());
                    tr.add(res1.getFlavorScore());
                }
                break;

        }
        ArrayList<Integer> index = new ArrayList<Integer>();
        Collections.sort(list);
        for (Double dou : list) {
            index.add(tr.indexOf(dou));
        }
        rList.clear();
        RestaurantLinkedList rl = new RestaurantLinkedList();
        for (int i : index) {
            rList.add(temp.get(index.size() - i - 1));
        }
    }
}
