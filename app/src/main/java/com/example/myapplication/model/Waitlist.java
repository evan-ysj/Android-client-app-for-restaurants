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

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getCategory() { return this.category; }

    public void setCategory(String category) { this.category = category; }

    public int getRank() { return this.rank; }

    public void setRank(int rank) { this.rank = rank; }

    public void clear() {
        this.id = -1;
        this.category = "";
        this.rank = -1;
    }
}
