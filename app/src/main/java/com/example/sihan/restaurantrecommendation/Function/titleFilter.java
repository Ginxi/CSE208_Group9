package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of titleFilter here.
 *
 * @version (a version number or a date)
 * @di.yao_1301853 (your name)
 */
public class titleFilter implements Filter {
    private String myWord;

    public titleFilter(String word) {
        myWord = word;
    }

    public boolean satisfies(String id) {
        String title = RestaurantDatabase.getTitle(id);
        return title.indexOf(myWord) != -1;
    }
}
