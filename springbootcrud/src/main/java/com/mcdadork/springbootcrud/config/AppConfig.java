package com.mcdadork.springbootcrud.config;

import com.mcdadork.springbootcrud.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration

@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class AppConfig {

    private final UserRepository repository;

    public AppConfig(UserRepository repository) {
        this.repository = repository;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> repository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }

}