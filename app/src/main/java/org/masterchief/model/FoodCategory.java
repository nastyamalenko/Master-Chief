package org.masterchief.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nastya.symon on 14.12.2016.
 */

public class FoodCategory {

//    @SerializedName("id")
    private Long id;
//    @SerializedName("name")
    private byte[] name;
//    @SerializedName("image")
    private byte[] image;

    public FoodCategory() {
    }

    public FoodCategory(Long id, byte[] name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
