package com.example.jojolduprac.config.auth;

import com.example.jojolduprac.domain.user.Role;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 설정들을 활성화 시켜줌
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // cross site request forgery

                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 해줌

                .authorizeHttpRequests(auth -> auth
                        // URL별 권한 관리를 설정하는 옵션의 시작점임, 이게 선언되어야만, antPathMatcher를 사용할 수 있음
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile")
                        .permitAll()
                        // 위에 있는 리소스의 접근은 모두가 할 수 있다.

                        .requestMatchers("/api/v1/**")
                        .hasRole(Role.USER.name())
                        // /api/v1 에 관련된 요청은 ROLE.USER만 할 수 있다.

                        .anyRequest().authenticated()
                        // 나머지 설정들은 인증된 사람들만 가능하다! -> USER ROLE이겠죠
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/")
                )

                .oauth2Login((oauth2) -> oauth2
                        // oauth2 로그인 성공 이후
                        .userInfoEndpoint(userInfoEndpoint ->
                                // userService는 소셜로그인 성공 후에 후속으로 동작할 놈을 낌,
                                // OAuth2UserService<OAuth2UserRequest, OAuth2User> 인터페이스의 상속을 받아서 구현함
                                userInfoEndpoint.userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/", true));

        return http.build();
    }

}
