package com.enjoygolf24.online.web.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.enjoygolf24.api.common.login.filter.CustomLoginBeforeFilter;
import com.enjoygolf24.api.service.impl.CommonLoginServiceImpl;

@Configuration
@EnableWebSecurity
public class CommonSecurityAdapter extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CommonSecurityAdapter.class);

    @Autowired
    CommonLoginSuccessHandler successHandler;

    @Autowired
    CommonLoginFailureHandler failureHandler;

    @Autowired
    CommonLogoutHandler logoutHandler;

    @Autowired
	CommonLoginServiceImpl loginServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

		http.addFilterBefore(new CustomLoginBeforeFilter(), BasicAuthenticationFilter.class);

        //logger.info("-----------------------Start configure----------------------------");

		successHandler.setDefaultTargetUrl("/menu");

        //http.sessionManagement()
        //        .sessionCreationPolicy(SessionCreationPolicy.NEVER);

		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/error/**", "/login/**", "/public/**").permitAll() // indexは全ユーザーアクセス許可
				.anyRequest().authenticated() // それ以外は全て認証無しの場合アクセス不許可
				.and().sessionManagement().maximumSessions(1).expiredUrl("/login");


        // Build the request matcher for CSFR protection
        RequestMatcher csrfRequestMatcher = new RequestMatcher() {

            // Disable CSFR protection on the following urls:
            private AntPathRequestMatcher[] requestMatchers = {
					// new AntPathRequestMatcher("/url**"),
					// new AntPathRequestMatcher("/url2**"),
            };

            @Override
            public boolean matches(HttpServletRequest request) {
                // If the request match one url the CSFR protection will be disabled
                for (AntPathRequestMatcher rm : requestMatchers) {
                    if (rm.matches(request)) {
                        return true;
                    }
                }
                return false;
            } // method matches

        }; // new RequestMatcher

        http.formLogin().loginProcessingUrl("/login/entry") // 認証処理のパス
                .loginPage("/login/") // ログインフォームのパス
                //.successForwardUrl("/main")
                .successHandler(successHandler) // 認証成功時に呼ばれるハンドラクラス
                .failureHandler(failureHandler) // 認証失敗時に呼ばれるハンドラクラス
                //.defaultSuccessUrl("/menu", true)
                .usernameParameter("userid").passwordParameter("password") // ユーザー名、パスワードのパラメータ名
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
                .logoutSuccessHandler(logoutHandler)
                //.deleteCookies("JSESSIONID")
                .and()
                .csrf().requireCsrfProtectionMatcher(csrfRequestMatcher);

        //logger.info("-----------------------End configure----------------------------");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        logger.info("start login ignore page set ");
		web.ignoring().antMatchers("/images/**", "/css/**", "/scripts/**", "/webjars/**", "/dummy-file/**", "/**.ico");
        logger.info("end login ignore page set ");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }
}
