package com.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class ThreatType {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)

    private String uuid;
}
