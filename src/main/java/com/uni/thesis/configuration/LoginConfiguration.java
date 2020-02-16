package com.uni.thesis.configuration;

import com.uni.thesis.service.ConsultantDetailService;
import com.uni.thesis.service.StudentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class LoginConfiguration {
    @Autowired
    private ConsultantDetailService consultantDetailService;

    @Autowired
    private StudentDetailService studentDetailService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Configuration
    @Order(1)
    public class ConsultantSecurityConfigurationn extends WebSecurityConfigurerAdapter {


        @Autowired
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(consultantDetailService)
                    .passwordEncoder(passwordEncoder);
        }

        PersistentTokenRepository persistentTokenRepository(){
            JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
            tokenRepositoryImpl.setDataSource(dataSource);
            return tokenRepositoryImpl;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            String[] staticResources = {"/resources/**","/static/**","/css/**","/img/**","/js/**", "/webjars/**","/assets/**"};
            http    .antMatcher("/consultant*")
                    .authorizeRequests()
                    //permitall engedélyezi az elérést authentikáció nélkül!
                    .antMatchers(staticResources).permitAll()
                    .antMatchers("/").permitAll()
                    //itt az admin alatti utat csak ADMIN szerepkörű érheti el
                    .antMatchers("/consultant*").hasRole("CONSULTANT")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/consultantlogin")
                    .defaultSuccessUrl("/consultanthome")
                    //login?error -> itt füzi hozza az error uzenetet amit consultantlogin.html ben erzekel
                    .failureUrl("/consultantlogin?error")
                    .permitAll()
                    .and()
                    .logout()
                  //  .logoutUrl("/consultant_logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/consultant_logout"))
                    .logoutSuccessUrl("/?logout")
                   // .defaultLogoutSuccessHandlerFor()
                    .deleteCookies("JSESSIONID")
                    .deleteCookies("my-remember-me-cookie")
/*                    .and()
                    .rememberMe()
                    .rememberMeCookieName("my-remember-me-cookie")
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(24 * 60 * 60)*/
                    .and()
                    .exceptionHandling()
                  //  .and().csrf().disable()
            ;
        }
    }

    @Configuration
    public class StudentSecurityConfigurationn extends WebSecurityConfigurerAdapter {



        @Autowired
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(studentDetailService)
                    .passwordEncoder(passwordEncoder);
        }

        PersistentTokenRepository persistentTokenRepository(){
            JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
            tokenRepositoryImpl.setDataSource(dataSource);
            return tokenRepositoryImpl;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            String[] staticResources = {"/resources/**","/static/**","/css/**","/img/**","/js/**", "/webjars/**","/assets/**"};
            http    .antMatcher("/student*")
                    .authorizeRequests()
                    //permitall engedélyezi az elérést authentikáció nélkül!
                    .antMatchers(staticResources).permitAll()
                    .antMatchers("/").permitAll()
                    //itt az admin alatti utat csak ADMIN szerepkörű érheti el
                    .antMatchers("/student*").hasRole("STUDENT")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/studentlogin")
                  //  .loginProcessingUrl("student_login")
                    .defaultSuccessUrl("/studenthome")
                    //login?error -> itt füzi hozza az error uzenetet amit consultantlogin.html ben erzekel
                    .failureUrl("/studentlogin?error")
                    .permitAll()
                    .and()
                    .logout()
                //    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //    .logoutSuccessUrl("/studentlogin?logout")
                //    .deleteCookies("my-remember-me-cookie")
                    .permitAll()
                    .and()
                    .rememberMe()
                    .rememberMeCookieName("my-remember-me-cookie")
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(24 * 60 * 60)
                    .and()
                    .exceptionHandling()
            ;
        }
    }

}
