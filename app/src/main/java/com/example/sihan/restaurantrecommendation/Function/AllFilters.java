package com.example.sihan.restaurantrecommendation.Function;


/**
 * Write a description of AllFilters here.
 *
 * @di.yao_1301853 (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class AllFilters implements Filter {
    ArrayList<Filter> filters;

    public AllFilters() {
        filters = new ArrayList<Filter>();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    public boolean satisfies(String id) {
        for (Filter f : filters) {
            if (!f.satisfies(id)) {
                return false;
            }
        }

        return true;
    }

}
