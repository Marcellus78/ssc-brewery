package guru.sfg.brewery.config;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactory;
import guru.sfg.brewery.security.UrlRestHeaderAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public UrlRestHeaderAuthFilter urlRestHeaderAuthFilter(AuthenticationManager authenticationManager) {

        UrlRestHeaderAuthFilter filter =
                new UrlRestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));

        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {

        RestHeaderAuthFilter filter =
                new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));

        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactory.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(urlRestHeaderAuthFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**").permitAll()
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                            .antMatchers("/beers/find","/beers*").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll()
                            .mvcMatchers("/brewery/breweries").hasRole("CUSTOMER")
                            .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries").hasRole("CUSTOMER");
                })
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                .and()
                    .httpBasic();

        //h2 console config
        http.headers().frameOptions().sameOrigin();
    }



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth
//                .inMemoryAuthentication()
//                .withUser("spring")
//                .password("{bcrypt}$2a$10$xUejVb0a2ubousH06QVxUe4A2msJkHFEWi8QldVbEgpu4GguEX4XC")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{sha256}c97a9e02d55e9ba54afa7960a02caaa9aee58ce9a4c5cab169e1e64a14fb0e84467295090f3007d1")
//                .roles("USER");
//
//        auth.inMemoryAuthentication()
//                .withUser("scott")
//                .password("{bcrypt12}$2a$12$m3XancjkVACV12NGCqq6E.kp09BHzgzneVkKWS6UxILo8QtO5qZ3G")
//                .roles("CUSTOMER");
//    }

    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}
