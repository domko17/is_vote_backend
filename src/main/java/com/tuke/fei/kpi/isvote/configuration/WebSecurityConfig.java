package com.tuke.fei.kpi.isvote.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.tuke.fei.kpi.isvote.modules.auth.security.TokenAuthenticationFilter;
import com.tuke.fei.kpi.isvote.modules.auth.security.token.JwtTokenFactory;
import com.tuke.fei.kpi.isvote.modules.auth.security.token.JwtTokenParser;
import com.tuke.fei.kpi.isvote.modules.auth.security.token.RestAuthenticationEntryPoint;
import com.tuke.fei.kpi.isvote.modules.auth.security.token.TokenFactory;
import com.tuke.fei.kpi.isvote.modules.properties.SecurityProperties;

import javax.servlet.Filter;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityProperties securityProperties;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public WebSecurityConfig(SecurityProperties securityProperties,
                             RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.securityProperties = securityProperties;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    private static final String[] AUTH_WHITELIST = {
            "/",
            "/auth/login/**",
            "/v3/api-docs",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/version",
            "/csrf"
    };

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private Filter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenParser());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenFactory tokenFactory() {
        return new JwtTokenFactory(securityProperties);
    }

    @Bean
    public JwtTokenParser tokenParser() {
        return new JwtTokenParser(securityProperties.getSigningKey());
    }

}
