package com.papertrail.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {

    @Value("${app.cors.origins}:*") // todo :* not recommended on production
    private List<String> allowedOrigins;


    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditAware();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true); // todo: uncomment on production and remove :*
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedHeaders(
                Arrays.asList( "*" // todo don't use in production
//                        HttpHeaders.ORIGIN,
//                        HttpHeaders.ACCEPT,
//                        HttpHeaders.CONTENT_TYPE,
//                        HttpHeaders.AUTHORIZATION
                )
        );
        configuration.setAllowedMethods(
                Arrays.asList( "*" // todo don't use in production
//                        "GET",
//                        "POST",
//                        "PUT",
//                        "DELETE",
//                        "PATCH"
                )
        );
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
