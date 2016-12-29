package org.masterchief.model;


public class Cuisine {

    private Long id;
    private byte[] cuisine;

    public Cuisine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getCuisine() {
        return cuisine;
    }

    public void setCuisine(byte[] cuisine) {
        this.cuisine = cuisine;
    }
}
