package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of categoriesFilter here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
public class categoriesFilter implements Filter{
    private String myCategories;
    
    public categoriesFilter(String categories){
       myCategories = categories;
    }
    
    public boolean satisfies(String id){
        String categories = RestaurantDatabase.getCategories(id);
        if(categories.indexOf(myCategories) == -1){
            return false;        
        }else{
            return true;
        }
    }
}
