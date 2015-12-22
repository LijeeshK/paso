package com.lijeeshk.paso.core.internal;

import com.lijeeshk.paso.core.Failure;
import com.lijeeshk.paso.core.ServiceMethod;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.NonNull;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lijeesh on 16/12/15.
 */
@NotThreadSafe
public class ServiceRequest {

    private final FullHttpRequest httpRequest;

    private final QueryStringDecoder urlDecoder;

    private Failure failure;

    private boolean failed;

    private ServiceMethod serviceMethod;

    public ServiceRequest(@NonNull final FullHttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        urlDecoder = new QueryStringDecoder(httpRequest.getUri(), true);
    }

    public HttpMethod method() {
        return httpRequest.getMethod();
    }

    public String path() {
        return urlDecoder.path();
    }

    public String uri() {
        return urlDecoder.uri();
    }

    public String getHeader(@NonNull final String headerKey) {
        return httpRequest.headers().get(headerKey);
    }

    public ServiceRequest markFailure(@NonNull final Failure failure) {
        if (!failed) {
            this.failure = failure;
            this.failed = true;
        }
        return this;
    }

    public ServiceRequest serviceMethod(@NonNull final ServiceMethod serviceMethod) {
        this.serviceMethod = serviceMethod;
        return this;
    }

}
