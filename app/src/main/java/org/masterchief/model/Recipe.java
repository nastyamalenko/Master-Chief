package org.masterchief.model;


import java.util.Arrays;

public class Recipe {

    private String id;
    private byte[] name;
    private Integer complexity;
    private Integer cookingTimeInMinutes;
    //    private CookingMethod cookingMethod;
    /* количество порций */
    private Integer servingsCount;

    private FoodCategory category;

    //    private List<CookingStep> cookingSteps;
    private byte[] image;

    public Recipe() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public Integer getCookingTimeInMinutes() {
        return cookingTimeInMinutes;
    }

    public void setCookingTimeInMinutes(Integer cookingTimeInMinutes) {
        this.cookingTimeInMinutes = cookingTimeInMinutes;
    }

    public Integer getServingsCount() {
        return servingsCount;
    }

    public void setServingsCount(Integer servingsCount) {
        this.servingsCount = servingsCount;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + new String(name) + '\'' +
                ", complexity=" + complexity +
                ", cookingTimeInMinutes=" + cookingTimeInMinutes +
                ", servingsCount=" + servingsCount +
                ", category=" + category +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
