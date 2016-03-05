package com.dao;

import com.configuration.CryptWithMD5;
import com.models.DatabaseObject;
import com.models.ThreatComment;
import com.models.UserModel;
import com.models.UserRole;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ThreatCommentDAO extends DatabaseDAO<ThreatComment>{

        public void save(ThreatComment comment) {

            getSession().save(comment);
        }

    public void delete (ThreatComment uuid){
        getSession().delete(uuid);
    }

    public ThreatComment get(String uuid) {
        return getSession().get(com.models.ThreatComment.class, uuid);
    }


}
