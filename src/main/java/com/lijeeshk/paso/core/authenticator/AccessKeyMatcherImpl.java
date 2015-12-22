package com.lijeeshk.paso.core.authenticator;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lijeesh on 21/12/15.
 */
public class AccessKeyMatcherImpl implements AccessKeyMatcher {

    private final Set<String> availableUsers;

    public AccessKeyMatcherImpl() {
        availableUsers = new HashSet<>();
        for (String u : StringUtils.split(System.getProperty("paso.accesskey.list", ""), ",")) {
            if (StringUtils.isNotBlank(u)) {
                availableUsers.add(u.trim());
            }
        }
    }

    @Override
    public boolean match(@NonNull String accessKey) {
        return availableUsers.contains(accessKey);
    }
}
