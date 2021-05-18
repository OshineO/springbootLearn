package com.mfk.ws;

import com.mfk.ws.handler.CustomWebSocketInterceptor;
import com.mfk.ws.handler.WsHandler;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
@EnableWebSocket
@EnableAspectJAutoProxy
@EnableAsync
@EnableCaching
@EnableRabbit
public class WsApplication {
/*public class WsApplication implements WebSocketConfigurer {*/

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}


	@Bean
	public WsHandler wsHandler () {
		return new WsHandler();
	}
	@Bean
	public CustomWebSocketInterceptor customWebSocketInterceptor () {
		return new CustomWebSocketInterceptor();
	}


/*	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(wsHandler(), "/ws");
		registry.addHandler(wsHandler(),"/ws").addInterceptors(customWebSocketInterceptor())
		.setAllowedOrigins("*");
	}*/


}
