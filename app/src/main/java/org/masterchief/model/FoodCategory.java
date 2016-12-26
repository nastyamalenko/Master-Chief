package org.masterchief.model;

/**
 * Created by nastya.symon on 14.12.2016.
 */

public class FoodCategory {

    private String id;
    private String name;
    private byte[] image;

    public FoodCategory() {
    }

    public FoodCategory(String id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
