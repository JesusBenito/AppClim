package com.example.exampleclim;

public class ModelClim {
    private String hours;
    private String description;
    private String icon;
    private String city;

    public ModelClim(String city,String hours, String description, String icon) {
        this.city = city;
        this.hours = hours;
        this.description = description;
        this.icon = icon;

    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String city) {
        this.hours = hours;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}


