package com.lijeeshk.paso.core;

import com.lijeeshk.paso.core.internal.ServiceRequest;
import rx.Observable;

import javax.annotation.Nonnull;

/**
 * Created by lijeesh on 19/12/15.
 */
public interface ServiceLocator {

    Observable<ServiceMethod> find(@Nonnull final ServiceRequest serviceRequest);
}
