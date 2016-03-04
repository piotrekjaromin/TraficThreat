package com.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Threat extends DatabaseObject {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "id", strategy = "uuid")
    @Column(name = "uuid", unique = true)

    private String uuid;

    @ManyToOne
    private ThreatType type;

    private String description;

    @OneToMany
    private List<ThreatComment> threatComments;

    private Date date;

    @OneToMany
    private List<Vote> vote;

    private Coordinates cordinates;
}
