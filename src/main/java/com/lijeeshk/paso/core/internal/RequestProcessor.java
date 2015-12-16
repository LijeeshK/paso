package com.lijeeshk.paso.core.internal;

import io.netty.handler.codec.http.FullHttpResponse;
import rx.Observable;

/**
 *
 * Created by lijeesh on 16/12/15.
 */
public interface RequestProcessor {

    Observable<FullHttpResponse> process(final ServiceRequestContext serviceRequestContext);
}
