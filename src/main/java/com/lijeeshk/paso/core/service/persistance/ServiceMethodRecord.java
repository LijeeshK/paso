package com.lijeeshk.paso.core.service.persistance;

import lombok.Data;

import java.util.List;

/**
 * Created by lijeesh on 21/12/15.
 */
@Data
public class ServiceMethodRecord {

    public enum HttpMethod {
        GET, PUT, POST, DELETE, HEAD
    }

    private String name;

    private String pathPrefix;

    private List<HttpMethod> httpMethods;
}
