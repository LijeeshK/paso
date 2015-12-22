package com.lijeeshk.paso.core;

import lombok.*;

import java.util.List;

/**
 * Created by lijeesh on 21/12/15.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Service {

    @NonNull
    private final String name;
    @NonNull
    private final String path;
    @NonNull
    private final String host;
    @NonNull
    private final int    port;

}
