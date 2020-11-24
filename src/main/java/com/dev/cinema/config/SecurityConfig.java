package com.dev.cinema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationBuilder)
            throws Exception {
        authenticationBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/movies/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/movie-sessions/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/cinema-halls/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/cinema-halls/**").authenticated()
                .antMatchers(HttpMethod.GET,"/movies/**").authenticated()
                .antMatchers(HttpMethod.GET,"/movie-sessions/**").authenticated()
                .antMatchers("/shopping-carts/**").authenticated()
                .antMatchers("/orders/**").authenticated()
                .antMatchers(HttpMethod.POST,"/registration").permitAll()
                .antMatchers(HttpMethod.GET,"/").permitAll()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

}
