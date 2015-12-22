package com.lijeeshk.paso.core.service;

import com.lijeeshk.paso.core.service.persistance.ServiceRecord;

import javax.annotation.Nonnull;

/**
 * Created by lijeesh on 21/12/15.
 */
public interface ServicePersistence {

    ServiceRecord get(@Nonnull final String basePath);

    ServiceRecord getAll();
}
