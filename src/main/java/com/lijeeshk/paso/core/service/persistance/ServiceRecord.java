package com.lijeeshk.paso.core.service.persistance;

import lombok.Data;

import java.util.List;

/**
 * Created by lijeesh on 21/12/15.
 */
@Data
public class ServiceRecord {

    private String                    name;
    private String                    basePath;
    private String                    host;
    private int                       port;
    private List<ServiceMethodRecord> serviceMethodRecords;
}
