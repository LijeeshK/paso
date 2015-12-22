package com.lijeeshk.paso.core;

import com.lijeeshk.paso.core.internal.ServiceRequest;
import com.lijeeshk.paso.core.internal.ServiceResponse;
import rx.Observable;

import javax.annotation.Nonnull;

/**
 * Created by lijeesh on 20/12/15.
 */
public interface ServiceDispatcher {

    Observable<ServiceResponse> dispatch(@Nonnull ServiceRequest serviceRequest);
}
