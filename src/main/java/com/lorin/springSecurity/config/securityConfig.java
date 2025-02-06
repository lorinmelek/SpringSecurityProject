package com.lorin.springSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());//csrf devre dışı bırkaıyorum.


        // Spring Security ile yetkilendirme yapıyoruz.
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("register", "login")
                .permitAll()
                .anyRequest().authenticated());//burada yetkilendirme yapılıyor login olmadan sayfalar açılmıyor.
        //http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//formu devre dışı bırakıyor.

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("vural")
//                .password("1234")
//                .roles("user")
//                .build();
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("hazal")
//                .password("1234")
//                .roles("admin")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
    //database olmadığı durumda bu şkelide yaptım.
//    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setPasswordEncoder(new BCryptPasswordEncoder(12));//passwordu encodeluyoruz.
      provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception//AuthenticationManager bir interface oldupundan onject oluşturup onu kullanıyoruz.
    {
        return config.getAuthenticationManager();
    }
}

