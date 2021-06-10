package com.xcf.gateway.handle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * @author xcf
 * @Date 创建时间：2021年6月9日 下午4:45:32
 */

@ApiIgnore
@RestController
@RequestMapping("/swagger-resources")
public class SwaggerHandler {

	/**
	 * 此方法是swagger源码的信息，通过这个来加载gateway的信息
	 */
	@Autowired
	@Qualifier("inMemorySwaggerResourcesProvider")
	private SwaggerResourcesProvider gatewaySwaggerResources;

	@Autowired(required = false)
	private SecurityConfiguration securityConfiguration;

	@Autowired(required = false)
	private UiConfiguration uiConfiguration;

	private final SwaggerResourcesProvider swaggerResources;

	@Autowired
	public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {
		this.swaggerResources = swaggerResources;
	}

	/**
	 * Swagger安全配置，支持oauth和apiKey设置
	 */
	@GetMapping("/configuration/security")
	public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
		return Mono.just(new ResponseEntity<>(
				Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()),
				HttpStatus.OK));
	}

	/**
	 * Swagger UI配置
	 */
	@GetMapping("/configuration/ui")
	public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
		return Mono.just(new ResponseEntity<>(
				Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
	}

	/**
	 * Swagger资源配置，微服务中这各个服务的api-docs信息
	 */

	//@GetMapping("/swagger-resources")
	//public Mono<ResponseEntity> swaggerResources() {
	//	return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
	//}

	@GetMapping
	public Mono<List<SwaggerResource>> handle() {

		return Mono.just(Stream.of(gatewaySwaggerResources, swaggerResources)
				.flatMap(swaggerResourcesProvider -> swaggerResourcesProvider.get().stream())
				.collect(Collectors.toList()));
	}
}
