package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of UserDatabase here.
 *
 * @di.yao_1301853 (your name)
 * @version (a version number or a date)
 */
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
public class UserDatabase {
    private static HashMap<String,EfficientUser> ourUsers;

    private static void initialize() {
	    // this method is only called from addRatings
		if (ourUsers == null) {
			ourUsers = new HashMap<String,EfficientUser>();
		}
	}

    public static void initialize(String filename, InputStream is) {
 		if (ourUsers == null) {
 			ourUsers= new HashMap<String,EfficientUser>();
 			addRatings(filename, is);
 		}
 	}

    public static void addRatings(String filename, InputStream is) {
        initialize();
        FileResource fr = new FileResource(filename, is);
        CSVParser csvp = fr.getCSVParser();
        for(CSVRecord rec : csvp) {
                String id = rec.get("user_id");
                String userName = rec.get("userName");
                String item = rec.get("restaurant_id");
                double flavorScore = Double.parseDouble(rec.get("flavorScore"));
                double environmentScore = Double.parseDouble(rec.get("environmentScore"));
                double serviceScore = Double.parseDouble(rec.get("serviceScore"));
                String account = rec.get("account");
                String password = rec.get("password");
                addRaterRating(id,userName, item,account, password,flavorScore, environmentScore, serviceScore);
        }
    }

    public static void addRaterRating(String userID, String userName, String restID,String account, String password,double flavorScore, double environmentScore,
                                     double serviceScore) {
        initialize();
        EfficientUser user =  null;
                if (ourUsers.containsKey(userID)) {
                    user = ourUsers.get(userID);
                }
                else {
                    user = new EfficientUser(userID, userName,account,password);
                    ourUsers.put(userID,user);
                 }
                 user.addRating(restID,flavorScore, environmentScore, serviceScore);
    }

    public static EfficientUser getUser(String id) {
    	initialize();
    	return ourUsers.get(id);
    }

    public static ArrayList<EfficientUser> getUsers(){
        initialize();
        ArrayList<EfficientUser> list = new ArrayList<EfficientUser>(ourUsers.values());

        return list;
    }

    public static int size() {
	    return ourUsers.size();
    }
}
