package com.example.facebookdemo.config;

import java.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class BeanConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration registration = new ClientRegistration.Builder(properties.getClientId())
//                .authorizationUri(properties.getAuthorizationUri())
//                .clientSecret(properties.getClientSecret())
//                .tokenUri(properties.getTokenUri())
//                .redirectUri(properties.getRedirectUri())
//                .scope(properties.getScopes().split(","))
//                .clientName(properties.getClientName())
//                .clientAlias(properties.getClientAlias())
//                .jwkSetUri(properties.getJwkSetUri())
//                .authorizationGrantType(properties.getAuthorizedGrantType())
//                .userInfoUri(properties.getUserInfoUri())
//                .build();
//
//        return new InMemoryClientRegistrationRepository(Arrays.asList(registration));
//    }
}
