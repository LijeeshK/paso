package com.lijeeshk.paso.core.service;

import com.google.common.cache.CacheLoader;
import com.lijeeshk.paso.core.Service;
import com.lijeeshk.paso.core.ServiceMethod;
import com.lijeeshk.paso.core.internal.PathTree;
import com.lijeeshk.paso.core.service.persistance.ServiceRecord;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

/**
 * Created by lijeesh on 22/12/15.
 */
public class ServiceCacheLoader extends CacheLoader<String, PathTree<ServiceMethod>> {

    private final ServicePersistence servicePersistence;

    @Inject
    public ServiceCacheLoader(@NonNull final ServicePersistence servicePersistence) {
        this.servicePersistence = servicePersistence;
    }


    @Override
    public PathTree<ServiceMethod> load(final String key) throws Exception {
        // service is not present in cache.. loading from underlying Persistence
        if (StringUtils.isBlank(key)) {
            ServiceRecord serviceRecord = servicePersistence.get(key);
            if (serviceRecord != null) {
                return buildMap(serviceRecord);
            }
        }
        return null;
    }

    /**
     * Method to build path prefix tree of service method from service entity
     *
     * @param serviceRecord - service entity to build path prefix tree
     * @return PathTree&lt;ServiceMethod&gt;
     */
    PathTree<ServiceMethod> buildMap(final ServiceRecord serviceRecord) {

        if (serviceRecord != null) {
            PathTree<ServiceMethod> smPathTree = new PathTree<>();
            Service service = new Service(serviceRecord.getName(), serviceRecord.getBasePath(), serviceRecord.getHost(),
                                          serviceRecord.getPort());
            CollectionUtils.emptyIfNull(serviceRecord.getServiceMethodRecords()).stream()
                           .map(sm -> new ServiceMethod(sm.getName(), sm.getPathPrefix(), null, service))
                           .forEach(sm -> smPathTree.add(sm.getPath(), sm));
            return smPathTree;
        }
        return null;
    }
}
