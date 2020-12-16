package com.example.myapplication.model;

public class Waitlist {

    private static class InstanceHolder {
        private static final Waitlist INSTANCE = new Waitlist();
    }

    private int id;
    private String category;
    private int rank;

    private Waitlist() {
        clear();
    }

    public static Waitlist getInstance() { return InstanceHolder.INSTANCE; }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public String getCategory() {
        return category;
    }

    public void update(int id, String category, int rank) {
        this.id = id;
        this.category = category;
        this.rank = rank;
    }

    public void clear() {
        this.id = -1;
        this.category = "";
        this.rank = -1;
    }
}
