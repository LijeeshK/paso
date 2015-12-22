package com.lijeeshk.paso.core.internal;

import com.lijeeshk.paso.filter.Authenticator;
import com.lijeeshk.paso.filter.ServiceDispatcher;
import rx.Observable;

import javax.annotation.Nonnull;

/**
 * Created by lijeesh on 19/12/15.
 */
public class DefaultRequestProcessor implements RequestProcessor {

    private Authenticator authenticator;

    private ServiceDispatcher dispatcher;

    @Override
    public Observable<ServiceResponse> process(@Nonnull ServiceRequest serviceRequest) {

        return authenticator.<ServiceRequest>authenticate(serviceRequest)
                     .zipWith(dispatcher.dispatch(serviceRequest), (req, resp) -> resp);
    }
}
