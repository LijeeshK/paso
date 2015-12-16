package com.lijeeshk.paso.core.internal.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.util.ResourceLeakDetector;
import lombok.Getter;

import java.util.EnumSet;

/**
 * Configuration class of Connector
 * Created by lijeesh on 15/12/15.
 */
public class ConnectorConfig {

    public static final int SERVER_SHUTDOWN_TIME_IN_SEC = 300;
    public static final int ONE_KB                      = 1024; // one KB
    public static final int SO_BACKLOG                  = ONE_KB;
    public static final int CONNECT_TIMEOUT_MILLIS      = 10 * 1000; // 10 seconds

    /**
     * No of boss thread to accept incoming connections
     */
    @JsonProperty("bossGroup")
    private int bossGroup = 0;

    /**
     * No of worker threads to process incoming requests
     */
    @JsonProperty("workerGroup")
    private int workerGroup = 0;

    /**
     * Server connector name. eg- PasoMain, PasoAdmin
     */
    @Getter @JsonProperty("name")
    private String name;
    /**
     * Server connection port
     */
    @Getter
    @JsonProperty("port")
    private int port = 8080;

    /**
     * Socket send buffer in KB
     */
    @JsonProperty("socketSendBuf")
    private int socketSendBuf = 64;

    /**
     * Socket receive buffer in KB
     */
    @JsonProperty("socketReceiveBuf")
    private int socketReceiveBuf = 64;

    /**
     * Socket backlog. limit for the queue of incoming connections
     */
    @Getter
    @JsonProperty("socketBackLog")
    private int socketBackLog = 1024;

    /**
     * Socket timeout in seconds
     */
    @JsonProperty("socketTimeout")
    private int socketTimeout = 5;

    /**
     * TCP NO delay settings
     */
    @Getter
    @JsonProperty("tcpNoDelay")
    private boolean tcpNoDelay = false;

    /**
     * Socket reuse address
     */
    //@Getter @JsonProperty("socketReUseAddr") private boolean socketReUseAddr = false;

    /**
     * Socket connection keep alive
     */
    @Getter
    @JsonProperty("socketKeepAlive")
    private boolean socketKeepAlive = true;

    @JsonProperty(value = "leakDetectorLevel")
    private String leakDetectorLevel = "SIMPLE";

    @JsonProperty(value = "maxContentSize")
    private int maxContentSizeInKb = 1024 * 2; // 2MB


    // Set bossGroup equal to number of processors if they are set to zero or negative.
    public int getBossGroup() {
        if (bossGroup <= 0) {
            bossGroup = Runtime.getRuntime().availableProcessors();
        }
        return bossGroup;
    }

    // Set workerGroup equal to twice the number of processors if they are set to zero or negative.
    public int getWorkerGroup() {
        if (workerGroup <= 0) {
            workerGroup = Runtime.getRuntime().availableProcessors() * 2;
        }
        return workerGroup;
    }

    public int getSocketSendBuffer() {
        return socketSendBuf * ONE_KB;
    }

    public int getSocketReceiveBuffer() {
        return socketReceiveBuf * ONE_KB;
    }

    public int getSocketTimeout() {
        return socketTimeout * 1000;
    }

    public int getSocketLinger() {
        return 1000;
    }

    public int getMaxContentSize() {
        return ONE_KB * maxContentSizeInKb;
    }

    public ResourceLeakDetector.Level getLeakDetectorLevel() {
        ResourceLeakDetector.Level level = ResourceLeakDetector.Level.DISABLED;
        String levelStr = leakDetectorLevel.trim().toUpperCase();
        for (ResourceLeakDetector.Level l : EnumSet.allOf(ResourceLeakDetector.Level.class)) {
            if (levelStr.equals(l.name()) || levelStr.equals(String.valueOf(l.ordinal()))) {
                level = l;
            }
        }
        return level;
    }
}
