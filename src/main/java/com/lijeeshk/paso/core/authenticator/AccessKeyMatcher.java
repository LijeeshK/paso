package com.lijeeshk.paso.core.authenticator;

import javax.annotation.Nonnull;

/**
 * Created by lijeesh on 21/12/15.
 */
public interface AccessKeyMatcher {

    boolean match(@Nonnull final String accessKey);
}
