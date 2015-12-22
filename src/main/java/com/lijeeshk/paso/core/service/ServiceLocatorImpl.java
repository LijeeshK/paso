package com.lijeeshk.paso.core.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lijeeshk.paso.core.Failure;
import com.lijeeshk.paso.core.Service;
import com.lijeeshk.paso.core.ServiceLocator;
import com.lijeeshk.paso.core.ServiceMethod;
import com.lijeeshk.paso.core.internal.PathTree;
import com.lijeeshk.paso.core.internal.ServiceRequest;
import com.lijeeshk.paso.core.service.persistance.ServiceMethodRecord;
import com.lijeeshk.paso.core.service.persistance.ServiceRecord;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Created by lijeesh on 21/12/15.
 */
@Slf4j
public class ServiceLocatorImpl implements ServiceLocator {

    private final ServiceCacheLoader serviceCacheLoader;

    private final LoadingCache<String, PathTree<ServiceMethod>> smCache;

    @Inject
    public ServiceLocatorImpl(@NonNull final ServiceCacheLoader serviceCacheLoader,
                              @NonNull final CacheBuilderSpec cacheSpec) {
        this.serviceCacheLoader = serviceCacheLoader;
        this.smCache = CacheBuilder.from(cacheSpec).build(serviceCacheLoader);
    }

    @Override
    public Observable<ServiceMethod> find(@NonNull final ServiceRequest serviceRequest) {

        return Observable.create(subscriber -> {
            ServiceMethod sm = getServiceMethod(serviceRequest);
            if (sm == null) {
                serviceRequest.markFailure(Failure.builder().statusCode(HttpResponseStatus.NOT_FOUND.code())
                                                  .errorCode(HttpResponseStatus.NOT_FOUND.code())
                                                  .error("No Service Definition found!!!")
                                                  .message("No service definition found!!!").build());
            }
            subscriber.onNext(sm);
        });

    }

    ServiceMethod getServiceMethod(@NonNull final ServiceRequest serviceRequest) {
        if (StringUtils.isBlank(serviceRequest.path())) {
            return null;
        }

        try {
            String servicePathName = getBasePath(serviceRequest.path());
            PathTree<ServiceMethod> smTree = smCache.get(servicePathName);
            return smTree.get(serviceRequest.path());
        } catch (ExecutionException | CacheLoader.InvalidCacheLoadException e) {
            // logging as warning, because cache miss will try to load from a DB and DB miss will throw exception
            log.warn("Error loading elt from cache", e);
        }
        return null;
    }


    String getBasePath(@NonNull final String path) {
        if (StringUtils.isBlank(path)) {
            return "";
        }
        return StringUtils.substringBefore(StringUtils.substringAfter(path, "/"), "/");

    }
}
