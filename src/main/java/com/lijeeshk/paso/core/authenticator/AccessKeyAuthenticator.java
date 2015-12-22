package com.lijeeshk.paso.core.authenticator;

import com.lijeeshk.paso.core.Authenticator;
import com.lijeeshk.paso.core.Failure;
import com.lijeeshk.paso.core.internal.ServiceRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;

import javax.inject.Inject;

/**
 * Created by lijeesh on 21/12/15.
 */
public class AccessKeyAuthenticator implements Authenticator {

    public static final String DEFAULT_ACCESS_KEY_HEADER = "x-accesskey";

    private final AccessKeyMatcher accessKeyMatcher;
    private final String           accessKeyHeader;


    @Inject
    public AccessKeyAuthenticator(@NonNull final AccessKeyMatcher accessKeyMatcher) {
        this(accessKeyMatcher, DEFAULT_ACCESS_KEY_HEADER);
    }

    public AccessKeyAuthenticator(@NonNull final AccessKeyMatcher accessKeyMatcher, @NonNull final String headerName) {
        this.accessKeyMatcher = accessKeyMatcher;
        this.accessKeyHeader = headerName;
    }


    @Override
    public Observable<Boolean> authenticate(@NonNull ServiceRequest serviceRequest) {

        String accessKey = serviceRequest.getHeader(accessKeyHeader);
        if (StringUtils.isBlank(accessKey)) {
            serviceRequest.markFailure(Failure.builder().statusCode(HttpResponseStatus.FORBIDDEN.code())
                                              .errorCode(HttpResponseStatus.FORBIDDEN.code())
                                              .error("Missing AccessKey!!!")
                                              .message("You are not authorized to access this service").build());

        } else if (!accessKeyMatcher.match(accessKey)) {
            serviceRequest.markFailure(Failure.builder().statusCode(HttpResponseStatus.FORBIDDEN.code())
                                              .errorCode(HttpResponseStatus.FORBIDDEN.code())
                                              .error("Invalid AccessKey!!!")
                                              .message("You are not authorized to access this service").build());

        } else {
            return Observable.just(Boolean.TRUE);
        }
        return Observable.just(Boolean.FALSE);
    }
}
