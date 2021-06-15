package com.model;

import org.neo4j.ogm.session.Session;

public class AddressService extends GenericService<Address> {
    public AddressService(Session session) {
        super(session);
    }

    @Override
    Class<Address> getEntityType() {
        return Address.class;
    }
}
