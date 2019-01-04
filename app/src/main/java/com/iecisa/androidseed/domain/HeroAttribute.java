package com.iecisa.androidseed.domain;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 4/1/19.
 * email: jmanuel_munoz@iecisa.com
 */

public class HeroAttribute {
    private String attribute;
    private String description;

    public HeroAttribute(String attribute, String description) {
        this.attribute = attribute;
        this.description = description;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
