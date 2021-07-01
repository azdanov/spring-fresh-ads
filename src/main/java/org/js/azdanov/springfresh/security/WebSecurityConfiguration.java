package org.js.azdanov.springfresh.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  @Value("${remember-me}")
  private String rememberMeKey;

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(
            c ->
                c.mvcMatchers(
                        HttpMethod.GET,
                        "/listings",
                        "/listings/visited",
                        "/listings/favorites",
                        "/listings/create",
                        "/listings/{listingId}/edit",
                        "/listings/{listingId}/payment")
                    .authenticated())
        .authorizeRequests(
            c ->
                c.mvcMatchers(
                        HttpMethod.POST,
                        "/listings",
                        "/{areaSlug}/categories/{categorySlug}/listings/{listingId}/favorites",
                        "/{areaSlug}/categories/{categorySlug}/listings/{listingId}/contact")
                    .authenticated())
        .authorizeRequests(
            c ->
                c.mvcMatchers(
                        HttpMethod.DELETE,
                        "/listings/{listingId}",
                        "/listings/{listingId}/favorites",
                        "/{areaSlug}/categories/{categorySlug}/listings/{listingId}/favorites")
                    .authenticated())
        .authorizeRequests(c -> c.mvcMatchers(HttpMethod.PUT, "/listings").authenticated())
        .authorizeRequests(c -> c.anyRequest().permitAll())
        .formLogin(c -> c.loginPage("/login").permitAll().usernameParameter("email"))
        .logout(c -> c.logoutSuccessUrl("/"))
        .rememberMe(c -> c.key(rememberMeKey))
        .csrf(c -> c.ignoringAntMatchers("/webhook/**"));
  }
}
