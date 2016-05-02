package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of titleFilter here.
 * 
 * @di.yao_1301853 (your name) 
 * @version (a version number or a date)
 */
public class titleFilter implements Filter{
    private String myWord;
    
    public titleFilter(String word){
        myWord = word;
    }
    
    public boolean satisfies(String id){
        String title = RestaurantDatabase.getTitle(id);
        if(title.indexOf(myWord) == -1){
            return false;
        }else{
            return true;
        }
    }
}
