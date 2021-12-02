package com.tutrit.tt.security.quickstart.db;

import com.tutrit.tt.security.quickstart.db.controller.Navigation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.net.InetAddress;
import java.util.stream.Collectors;

@Configuration
@SpringBootApplication
public class InDbSecurityApplication {
	public static ApplicationContext ctx;

	public static void main(String[] args) {
		ctx = SpringApplication.run(InDbSecurityApplication.class, args);
	}

	@Bean
	public Navigation navigation() {
		return () -> {
			String port = ctx.getBean(ServerProperties.class).getPort().toString();
			String host = getHost();
			String link = "<td width=128px><h3> <a href='%s'>%s</a> </h3></td>";
			return ctx.getBean(RequestMappingHandlerMapping.class).getHandlerMethods().keySet()
					.stream()
					.flatMap(info -> info.getPatternValues().stream())
					.filter(endpoint -> !endpoint.equals("/error"))
					.sorted()
					.map(endpoint -> link.formatted(String.format("http://%s:%s%s", host, port, endpoint), endpoint))
					.collect(Collectors.joining("", "<table><tr>", "</tr></table>"));
		};
	}

	private String getHost() {
		try {
			return InetAddress.getLocalHost(). getCanonicalHostName();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
