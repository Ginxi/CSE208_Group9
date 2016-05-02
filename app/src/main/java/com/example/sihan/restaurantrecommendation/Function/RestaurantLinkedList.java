/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sihan.restaurantrecommendation.Function;

/**
 *
 * @author Ginxi
 */

public class RestaurantLinkedList {

    private RestaurantNode listStart;
    private RestaurantNode listEnd;
    //constructor

    public RestaurantLinkedList() {
    }
    //add to the start of the list

    public void add(Restaurant b) {
        listStart = new RestaurantNode(b, listStart);
        if (listEnd == null) { // list was empty
            listEnd = listStart; // 1 card in list
        }
    }

    public void addend(Restaurant b) {
        if (listStart == null) {
            add(b);
        } else {
            listEnd.addend(b);
            listEnd = listEnd.tail(); //update the pointer
        }
    }

    public int length() {
        // stores the number of nodes found so far
        int len = 0;
        // this variable traverses each node in the list
        RestaurantNode n = listStart;
        // find the end of the list
        while (n != null) {
            // found a node, so count it and move on
            len++;
            n = n.tail();
        }
        return len;
    }

    public void removeLast() {
        if (listStart != null) {
            RestaurantNode n = listStart;
            while (n.tail().tail() != null) {
                n = n.tail();
            }
            listEnd = n;
            listEnd.chop();
        }
    }

    public void removeFirst() {
        if (listStart != null) {
            listStart = listStart.tail();
        }
    }

    public RestaurantNode getStartNode() {
        return listStart;
    }

    public RestaurantNode getEndNode() {
        return listEnd;
    }

//    public String toString() {
//        String str = "";
//        RestaurantNode n = listStart;
//        while (n != null) {
//            AnnotationLinkedList annList = (AnnotationLinkedList) n.head().getAnntationList();
//            str += "Name: " + n.head().getName() + " Size: " + n.head().getSize() + " Owner: " + n.head().getOwner() + " Creation Date: " + n.head().getCreation_Date() + " Eclecticism: " + " Annotations: [" + annList.toString() + "]";
//            n = n.tail();
//        }
//        return str;
//    }

    public class RestaurantNode {

        private Restaurant head;
        private RestaurantNode tail;

        public void chop() {
            tail = null;
        }

        public RestaurantNode(Restaurant b, RestaurantNode t) {
            head = b;
            tail = t;
        }

        public RestaurantNode(Restaurant b) {
            head = b;
            tail = null;
        }

        public Restaurant head() {
            return head;
        }

        public RestaurantNode tail() {
            return tail;
        }

        public void addend(Restaurant b) {
            tail = new RestaurantNode(b);
        }

//        public String toString() {
//            String str = "";
//            AnnotationLinkedList annList = (AnnotationLinkedList) head.getAnntationList();
//            str += "Name: " + head.getName() + " Size: " + head().getSize() + " Owner: " + head.getOwner() + " Creation Date: " + head.getCreation_Date() + " Eclecticism: " + " Annotations: [" + annList.toString() + "]";
//            return str;
//        }
    }
}
