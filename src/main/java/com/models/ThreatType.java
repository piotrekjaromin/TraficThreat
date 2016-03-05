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

    private String threaType;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getThreatType() {
        return threaType;
    }

    public void setThreatType(String threat) {
        this.threaType = threat;
    }

    @Override
    public String toString() {
        return "{" +
                "\"uuid\":\"" + uuid + '\"' +
                ", \"threatType\":\"" + threaType + '\"' +
                '}';
    }
}
