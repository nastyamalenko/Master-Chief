package org.masterchief.food;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by nastya.symon on 14.12.2016.
 */

public class FoodCategory {
    private String name;
    private byte[] image;

    public FoodCategory(String name, byte[] image) {
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
}
