/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sihan.restaurantrecommendation.Function;

/**
 * @author Ginxi
 */
public class ResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
