package com.lijeeshk.paso.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by lijeesh on 21/12/15.
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Failure {

    private int statusCode;
    private int errorCode;
    private String error;
    private String message;

}
