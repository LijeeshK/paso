package com.lijeeshk.paso.core.internal;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lijeeshk.paso.core.internal.config.ConnectorConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Main PASO server.
 * Created by lijeesh on 15/12/15.
 */
@Slf4j
public class ServerConnector {

    private final ConnectorConfig connectorConfig;

    private final ServerBootstrap serverBootstrap;


    private final AtomicBoolean started = new AtomicBoolean(false);

    @Inject
    public ServerConnector(@NonNull final ConnectorConfig connectorConfig, @NonNull final ChannelHandler childHandler) {
        this.connectorConfig = connectorConfig;
        this.serverBootstrap = createServerBootstrap(connectorConfig, childHandler);

    }

    /**
     * Method to create server bootstrap from given connector configuration
     *
     * @param connectorConfig - Connector configuration used to create server bootstrap
     * @param childHandler    - Http Handler to handle the incoming request
     * @return ServerBootstrap
     */
    protected ServerBootstrap createServerBootstrap(@NonNull final ConnectorConfig connectorConfig,
                                                    @NonNull final ChannelHandler childHandler) {
        EventLoopGroup bossGrp = new NioEventLoopGroup(connectorConfig.getBossGroup(),
                                                       new ThreadFactoryBuilder().setNameFormat("paso-io-boss-%d")
                                                                                 .build());
        EventLoopGroup wrkGrp = new NioEventLoopGroup(connectorConfig.getWorkerGroup(),
                                                      new ThreadFactoryBuilder().setNameFormat("paso-io-worker-%d")
                                                                                .build());

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGrp, wrkGrp).channel(NioServerSocketChannel.class).childHandler(childHandler);
        bootstrap.option(ChannelOption.SO_BACKLOG, connectorConfig.getSocketBackLog());
        bootstrap.option(ChannelOption.SO_TIMEOUT, connectorConfig.getSocketTimeout());

        bootstrap.childOption(ChannelOption.SO_SNDBUF, connectorConfig.getSocketSendBuffer());
        bootstrap.childOption(ChannelOption.SO_RCVBUF, connectorConfig.getSocketReceiveBuffer());
        bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, connectorConfig.isTcpNoDelay());
        bootstrap.childOption(ChannelOption.SO_LINGER, connectorConfig.getSocketLinger());
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, connectorConfig.isSocketKeepAlive());

        return bootstrap;
    }

    @PostConstruct
    public void start() {

        if (!started.get()) {
            log.info("Starting connector....[{}]", connectorConfig.getName());
            serverBootstrap.bind(connectorConfig.getPort()).syncUninterruptibly();
            started.set(true);
            log.info("Started connector....[{}]@[{}]", connectorConfig.getName(), connectorConfig.getPort());
        } else {
            log.error("Connector [{}] is already running", connectorConfig.getName());
        }

    }

    @PreDestroy
    public void stop() {

        if (started.compareAndSet(true, false)) {

            log.info("Shutting down connector [{}}", connectorConfig.getName());

            Future<?> bssGrpFuture = serverBootstrap.group().shutdownGracefully();
            Future<?> wrkGrpFuture = serverBootstrap.childGroup().shutdownGracefully();

            log.info("Waiting connector to shutdown threads");
            bssGrpFuture.awaitUninterruptibly();
            wrkGrpFuture.awaitUninterruptibly();

            log.info("Shutdown completed!!!");
        } else {
            log.warn("Not Started!!!!");
        }
    }
}
