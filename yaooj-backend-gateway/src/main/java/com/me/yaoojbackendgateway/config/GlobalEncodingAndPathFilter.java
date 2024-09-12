package com.me.yaoojbackendgateway.config;

import cn.hutool.core.text.AntPathMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 全局字符编码和路径拦截器
 * @author Me.
 * @version 0.0.1
 * @createTime 2024/9/12 11:44
 * @from <a href="https://github.com/Me-60">Me.</a>
 * @blog <a href="https://www.cnblogs.com/Me60">博客园</a>
 */
@Component
@Slf4j
public class GlobalEncodingAndPathFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        ServerHttpResponse serverHttpResponse = exchange.getResponse();

        serverHttpResponse.getHeaders().add("Content-Type","application/json;charset=UTF-8");

        String path = serverHttpRequest.getURI().getPath();

        log.info("请求路径为：" + path);

        // 判断路径中是否包含 inner，只允许内部调用
        if (antPathMatcher.match("/**/inner/**", path)) {
            log.info("路径错误，无法访问");

            serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);

            DataBufferFactory dataBufferFactory = serverHttpResponse.bufferFactory();

            DataBuffer dataBuffer = dataBufferFactory.wrap("路径错误，无法访问".getBytes(StandardCharsets.UTF_8));

            return serverHttpResponse.writeWith(Mono.just(dataBuffer));
        }

        return chain.filter(exchange);
    }

    /**
     * 拦截器优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
