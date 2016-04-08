package com.dao;

import com.models.ThreatType;
import org.springframework.stereotype.Repository;

/**
 * Created by piotrek on 05.03.16.
 */
@Repository
public class ThreatTypeDAO extends DatabaseDAO<ThreatType>{
        public void save(ThreatType type) {

            getSession().save(type);
        }

    public void delete (ThreatType type){
        getSession().delete(type);
    }

    public void update(ThreatType threatType) {
        getSession().update(threatType);
    }
    }