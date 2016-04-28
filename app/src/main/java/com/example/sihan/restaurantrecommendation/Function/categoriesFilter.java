package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of categoriesFilter here.
 *
 * @version (a version number or a date)
 * @di.yao_1301853 (your name)
 */
public class categoriesFilter implements Filter {
    private String myCategories;

    public categoriesFilter(String categories) {
        myCategories = categories;
    }

    public boolean satisfies(String id) {
        String categories = RestaurantDatabase.getCategories(id);
        return categories.indexOf(myCategories) != -1;
    }
}
