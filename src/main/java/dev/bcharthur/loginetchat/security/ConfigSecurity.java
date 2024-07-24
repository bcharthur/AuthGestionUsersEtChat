package dev.bcharthur.loginetchat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "/").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/integration").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/integration").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/navbar").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/navbar").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/login/**").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/login/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/register/**").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/register/**").permitAll();

            auth.requestMatchers(HttpMethod.GET, "/chat/**").authenticated();
            auth.requestMatchers(HttpMethod.POST, "/chat/**").authenticated();

            auth.requestMatchers(HttpMethod.GET, "/api/chats/**").authenticated();
            auth.requestMatchers(HttpMethod.POST, "/api/chats/**").authenticated();

            auth.requestMatchers("/css/**").permitAll()
                    .requestMatchers("/images/**").permitAll()
                    .requestMatchers("/javascript/**").permitAll()
                    .requestMatchers("/error").permitAll()

                    .anyRequest().authenticated();
        });

        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/").permitAll();
            form.failureUrl("/login-error");

            form.successHandler((request, response, authentication) -> {
                if (authentication != null) {
                    MyUserDetail userDetails = (MyUserDetail) authentication.getPrincipal();
                    request.getSession().setAttribute("currentUser", userDetails.getUser());
                }
                response.sendRedirect("/");
            });
        });

        http.logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll());

        // Ajoutez cette ligne pour désactiver la persistance des sessions
//        http.sessionManagement().disable();

        return http.build();
    }
}
