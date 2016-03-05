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
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @ManyToOne
    private ThreatType type;

    private String description;

    private String login;

    @OneToMany
    private List<ThreatComment> threatComments;

    private Date date;

    @OneToMany
    private List<Vote> votes;

    @OneToOne
    private Coordinates coordinates;

    private boolean isApproved;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ThreatType getType() {
        return type;
    }

    public void setType(ThreatType type) {
        this.type = type;
    }

    public void addComment(ThreatComment comment) {
        threatComments.add(comment);
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ThreatComment> getThreatComments() {
        return threatComments;
    }

    public void setThreatComments(List<ThreatComment> threatComments) {
        this.threatComments = threatComments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public void deleteComment(ThreatComment comment){
        threatComments.remove(comment);
    }

    public void deleteAllConection(){
        type=null;
        threatComments.clear();
        votes.clear();
        coordinates=null;

    }


    @Override
    public String toString() {
        return "{" +
                "\"uuid\":\"" + uuid  + '\"' +
                ", \"login\":\"" + login + '\"'+
                ", \"type\":" + type +
                ", \"description\":\"" + description + '\"' +
                ", \"threatComments\": " + threatComments  +
                ", \"votes\": " + votes +
                ", \"coordinates\":" + coordinates +
                ", \"isApproved\":\"" + isApproved + '\"' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Threat threat = (Threat) o;

        return uuid.equals(threat.uuid);

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
