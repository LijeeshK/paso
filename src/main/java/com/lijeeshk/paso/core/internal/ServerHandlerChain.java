package com.lijeeshk.paso.core.internal;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.NonNull;

/**
 * Created by lijeesh on 16/12/15.
 */
public class ServerHandlerChain extends ChannelInitializer<SocketChannel> {

    private final int maxContentSize;

    public ServerHandlerChain(final int maxContentSize) {
        this.maxContentSize = maxContentSize;
    }


    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {

        // pre condition check
        if (null == ch) {
            throw new IllegalArgumentException("Socket channel is null");
        }

        ch.pipeline().addLast(new HttpServerCodec(), new HttpObjectAggregator(maxContentSize));


    }
}
