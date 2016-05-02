package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of addressFilter here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
public class addressFilter implements Filter{
    private String myPlace;
    
    public addressFilter(String place){
        myPlace = place;
    }
    
    public boolean satisfies(String id){
        String address = RestaurantDatabase.getAddress(id);
        if(address.indexOf(myPlace) == -1){
            return false;
        }else{
            return true;
        }
    }
}
