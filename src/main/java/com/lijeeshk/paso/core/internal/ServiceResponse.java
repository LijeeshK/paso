package com.lijeeshk.paso.core.internal;

import io.netty.handler.codec.http.FullHttpResponse;
import lombok.NonNull;

/**
 * Created by lijeesh on 19/12/15.
 */
public class ServiceResponse {

    private final ServiceRequest serviceRequest;

    private final FullHttpResponse httpResponse;

    public ServiceResponse(@NonNull final ServiceRequest serviceRequest, @NonNull final FullHttpResponse httpResponse) {
        this.serviceRequest = serviceRequest;
        this.httpResponse = httpResponse;
    }

}
