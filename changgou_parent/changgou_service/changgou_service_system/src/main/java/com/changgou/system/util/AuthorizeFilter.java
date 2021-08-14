package com.changgou.system.util;


import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tk.mybatis.mapper.util.StringUtil;


@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "token";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取请求
        ServerHttpRequest request = exchange.getRequest();
        //获取相应
        ServerHttpResponse response = exchange.getResponse();
        //如果是登陆就放行
        if (request.getURI().getPath().contains("/admin/login")){
            return chain.filter(exchange);
        }
        //获取请求头
        HttpHeaders headers = request.getHeaders();
        //获取请求头里令牌
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        //判断请求头是否有token令牌
        if (StringUtils.isEmpty(token)){
            //返回状态码，没有权限
            boolean b = response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //返回相应完成
            return response.setComplete();
        }
        try {
            Claims claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //解析令牌出错，返回状态码过期，或者未经授权
             response.setStatusCode(HttpStatus.UNAUTHORIZED);
             //返回相应完成
             return response.setComplete();
        }
        //token满足，放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
