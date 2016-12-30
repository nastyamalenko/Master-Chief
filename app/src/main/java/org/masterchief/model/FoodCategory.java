package org.masterchief.model;

/**
 * Created by nastya.symon on 14.12.2016.
 */

public class FoodCategory extends Dictionary {


    private byte[] image;

    public FoodCategory(Long id, byte[] name, byte[] image) {
        super(id, name);
        this.image = image;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
