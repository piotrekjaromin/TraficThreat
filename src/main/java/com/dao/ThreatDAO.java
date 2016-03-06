package com.dao;

import com.configuration.CryptWithMD5;
import com.models.Threat;
import com.models.UserModel;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public class ThreatDAO extends DatabaseDAO<Threat>{

        public void save(Threat threat) {
            getSession().save(threat);
        }


    public Threat get(String uuid) {
            return getSession().get(com.models.Threat.class, uuid);
        }

    public void update(Threat threat) {
            getSession().update(threat);
        }

    public List<Threat> getAll() {
            return getSession().createQuery("from Threat").list();
    }

    public List<Threat> getAllApproved() {
        return getAll().stream().filter(x-> x.getIsApproved()==true).collect(Collectors.toList());
    }

    public List<Threat> getAllNotApproved() {
        return getAll().stream().filter(x -> x.getIsApproved() == false).collect(Collectors.toList());
    }


    public void delete (Threat threat){
        getSession().delete(threat);
    }

}
