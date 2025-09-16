package com.jhj.hotel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링부트 환경 설정 파일이라는 것을 명시
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 annotation
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 모든 페이지의 요청을 허락한다.
		http
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//					.requestMatchers(new AntPathRequestMatcher("/user/login")).permitAll()
//                    .requestMatchers(new AntPathRequestMatcher("/user/signup")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			.formLogin((formLogin) -> formLogin // 스프링 시큐리티에서 로그인 설정
					.loginPage("/user/login") // 로그인 요청
					.defaultSuccessUrl("/")) // 로그인 성공 시 이동할 페이지 루트 지정
			.logout((logout) -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 요청
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true))			
         ;
      return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean // 스프링 시큐리티에서 인증을 처리하는 매니저 클래스
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
}
