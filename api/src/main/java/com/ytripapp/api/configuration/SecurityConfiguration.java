package com.ytripapp.api.configuration;

import com.ytripapp.api.security.AuthenticationFailureHandler;
import com.ytripapp.api.security.AuthenticationService;
import com.ytripapp.api.security.AuthenticationSuccessHandler;
import com.ytripapp.repository.AccountConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;

@Profile("security")
@EnableWebSecurity
@Import({
    SecurityAutoConfiguration.class,
    ManagementWebSecurityAutoConfiguration.class
})
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountConnectionRepository connectionRepository;

    @Autowired
    MappingJackson2HttpMessageConverter messageConverter;

    @Autowired
    MessageSource messageSource;

    @Autowired
    LocaleResolver localeResolver;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new AuthenticationService(connectionRepository))
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .disable()
            .csrf()
                .disable()
            .formLogin()
                .loginProcessingUrl("/sessions")
                .successHandler(new AuthenticationSuccessHandler(messageConverter))
                .failureHandler(new AuthenticationFailureHandler(messageSource, localeResolver, messageConverter))
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }
}
