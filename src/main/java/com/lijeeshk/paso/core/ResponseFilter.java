package com.lijeeshk.paso.core;

import com.lijeeshk.paso.core.internal.ServiceResponse;
import rx.Observable;

import javax.annotation.Nonnull;

/**
 * Created by lijeesh on 19/12/15.
 */
public interface ResponseFilter {

    Phase phase();

    Observable<Void> filter(@Nonnull final ServiceResponse serviceResponse);

}
