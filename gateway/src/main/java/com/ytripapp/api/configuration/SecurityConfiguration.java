package com.ytripapp.api.configuration;

import com.ytripapp.api.security.AuthenticationFailureHandler;
import com.ytripapp.api.security.AuthenticationService;
import com.ytripapp.api.security.AuthenticationSuccessHandler;
import com.ytripapp.repository.AccountConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.Filter;

@Profile("security")
@EnableWebSecurity
@Import({
    SecurityAutoConfiguration.class,
    ManagementWebSecurityAutoConfiguration.class
})
@EnableOAuth2Client
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

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter wechatFilter = new OAuth2ClientAuthenticationProcessingFilter("/connections/wechat");
        OAuth2RestTemplate wechatTemplate = new OAuth2RestTemplate(wechat(), oauth2ClientContext);
        wechatFilter.setRestTemplate(wechatTemplate);
        wechatFilter.setTokenServices(new UserInfoTokenServices(wechatResource().getUserInfoUri(), wechat().getClientId()));
        return wechatFilter;
    }

    @Bean
    @ConfigurationProperties("wechat.client")
    OAuth2ProtectedResourceDetails wechat() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("wechat.resource")
    ResourceServerProperties wechatResource() {
        return new ResourceServerProperties();
    }

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
            .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }
}
