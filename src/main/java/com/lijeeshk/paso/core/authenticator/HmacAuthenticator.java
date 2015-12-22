package com.lijeeshk.paso.core.authenticator;

import com.lijeeshk.paso.core.internal.ServiceRequest;
import com.lijeeshk.paso.core.Authenticator;
import rx.Observable;

import javax.annotation.Nonnull;

/**
 * Created by lijeesh on 21/12/15.
 */
public class HmacAuthenticator implements Authenticator {

    @Override
    public Observable<Boolean> authenticate(@Nonnull ServiceRequest serviceRequest) {
        return null;
    }
}
