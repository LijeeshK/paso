package com.lijeeshk.paso.core;

import io.netty.handler.codec.http.HttpMethod;
import lombok.*;

import java.util.List;

/**
 * Created by lijeesh on 21/12/15.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ServiceMethod {

    @NonNull
    private final String           name;
    @NonNull
    private final String           path;
    @NonNull
    private final List<HttpMethod> method;
    @NonNull
    private final Service          service;
}
