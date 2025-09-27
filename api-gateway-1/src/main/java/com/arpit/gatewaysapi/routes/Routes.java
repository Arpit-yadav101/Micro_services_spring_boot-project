package com.arpit.gatewaysapi.routes;

import com.arpit.gatewaysapi.ApiGateway1Application;

import java.net.URI;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayServerResponse;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions.Builder;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class Routes {

    private final ApiGateway1Application apiGateway1Application;

    Routes(ApiGateway1Application apiGateway1Application) {
        this.apiGateway1Application = apiGateway1Application;
    }
	
	@Bean
	public RouterFunction<ServerResponse> productServiceRoute(){
		return GatewayRouterFunctions.route("product-service-1").route(RequestPredicates.path("/api/product")
				,HandlerFunctions.http("http://localhost:8080")).filter(CircuitBreakerFilterFunctions.circuitBreaker("product ServiceCircuitBreaker",URI.create("forward:/fallbackRoute"))).build();
	}
	@Bean
	public RouterFunction<ServerResponse> orderServiceRoute(){
		return GatewayRouterFunctions.route("order-service").route(RequestPredicates.path("/api/order")
				,HandlerFunctions.http("http://localhost:8081")).filter(CircuitBreakerFilterFunctions.circuitBreaker("product ServiceCircuitBreaker",URI.create("forward:/fallbackRoute")))
					.build();
	}
	@Bean
	public RouterFunction<ServerResponse> invetoryServiceRoute(){
		return GatewayRouterFunctions.route("invintory-service-1").route(RequestPredicates.path("/api/inventory")
				,HandlerFunctions.http("http://localhost:8083")).filter(CircuitBreakerFilterFunctions.circuitBreaker("product ServiceCircuitBreaker",URI.create("forward:/fallbackRoute"))).build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> productServiceSwaggerRoute(){
		return GatewayRouterFunctions.route("product_service_swagger").route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"
			)	,HandlerFunctions.http("http://localhost:8080/v3/api-docs")).filter(CircuitBreakerFilterFunctions.circuitBreaker("product ServiceCircuitBreaker",URI.create("forward:/fallbackRoute"))).filter(setPath("/api-docs")).build();
	}
	
	
	@Bean
	public RouterFunction<ServerResponse> orderServiceSwaggerRoute(){
		return GatewayRouterFunctions.route("order_service_swagger").route(RequestPredicates.path("/aggregate/order-service/v3/api-docs")
				,HandlerFunctions.http("http://localhost:8081/v3/api-docs")).filter(CircuitBreakerFilterFunctions.circuitBreaker("product ServiceCircuitBreaker",URI.create("forward:/fallbackRoute"))).filter(setPath("/api-docs")).build();
	}
	@Bean
	public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute(){
		return GatewayRouterFunctions.route("invintory_service_swagger").route(RequestPredicates.path("/aggregate/invintory-service/v3/api-docs")
				,HandlerFunctions.http("http://localhost:8083/v3/api-docs")).filter(CircuitBreakerFilterFunctions.circuitBreaker("product ServiceCircuitBreaker",URI.create("forward:/fallbackRoute"))).filter(setPath("/api-docs")).build();
	}
	
	
	 
	@Bean
	public RouterFunction<ServerResponse> fallbackRoute(){
	    return GatewayRouterFunctions.route("fallbackRoute")
	        .route(RequestPredicates.path("/fallbackRoute"),
	            request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
	                .body("Server Unavailable, please try again later"))
	        .build();
	}
	
	
	
	
	
	
	
	

}
