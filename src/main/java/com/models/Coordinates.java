package com.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class Coordinates {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    private String vertical;

    private String horizontal;

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public String toString() {
        return "{" +
                "\"uuid\":\"" + uuid + '\"' +
                ", \"vertical\":\"" + vertical + '\"' +
                ", \"horizontal\":\"" + horizontal + '\"' +
                '}';
    }
}
