package com.model;

import org.neo4j.ogm.session.Session;

import java.util.HashMap;
import java.util.Map;

public class PolicemanService extends GenericService<Policeman> {
    public PolicemanService(Session session) {
        super(session);
    }

    @Override
    Class<Policeman> getEntityType() {
        return Policeman.class;
    }

    public Iterable<Map<String, Object>> getPolicemanAboveAge(int age) {
        String query = "MATCH (p:Policeman) " +
                        "WHERE p.age > " + age +
                        "RETURN p";
        Map<String, Object> params = new HashMap<>();
        return session.query(query, params).queryResults();
    }
}
