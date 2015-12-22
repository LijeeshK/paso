package com.lijeeshk.paso.core.internal;

import rx.Observable;

import javax.annotation.Nonnull;

/**
 *
 * Created by lijeesh on 16/12/15.
 */
public interface RequestProcessor {

    Observable<ServiceResponse> process(@Nonnull final ServiceRequest serviceRequest);
}
