package com.model;

import org.neo4j.ogm.session.Session;

public class PoliceStationService extends GenericService<PoliceStation> {
    public PoliceStationService(Session session) {
        super(session);
    }

    @Override
    Class<PoliceStation> getEntityType() {
        return PoliceStation.class;
    }
}
