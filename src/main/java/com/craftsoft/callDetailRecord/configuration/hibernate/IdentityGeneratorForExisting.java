package com.craftsoft.callDetailRecord.configuration.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

public class IdentityGeneratorForExisting  extends UUIDGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
