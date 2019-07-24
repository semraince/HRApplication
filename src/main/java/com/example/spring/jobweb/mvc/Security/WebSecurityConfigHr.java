package com.example.spring.jobweb.mvc.Security;



import com.example.spring.jobweb.mvc.config.JwtAuthenticationEntryPoint;
import com.example.spring.jobweb.mvc.config.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Order(2)
@EnableWebSecurity

public class WebSecurityConfigHr extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //String secret = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("secret.key"), Charset.defaultCharset());
        /*DOĞRU YER*/
        /*httpSecurity.httpBasic().and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/hr/**").authenticated().and()
                .authorizeRequests().anyRequest().permitAll().and()
                .formLogin().defaultSuccessUrl("http://localhost:4200/login").failureUrl("/login?error").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/getAllJobs");*/

        httpSecurity.cors().and().csrf().disable()
                /*
                Filters are added just after the ExceptionTranslationFilter so that Exceptions are catch by the exceptionHandling()
                 Further information about the order of filters, see FilterComparator
                 */
                //.addFilterAfter(jwtTokenAuthenticationFilter("/**", secret), ExceptionTranslationFilter.class)
                //.addFilterAfter(corsFilter(), ExceptionTranslationFilter.class)
                /*
                 Exception management is handled by the authenticationEntryPoint (for exceptions related to authentications)
                 and by the AccessDeniedHandler (for exceptions related to access rights)
                */

                /*
                  anonymous() consider no authentication as being anonymous instead of null in the security context.
                 */

               .authorizeRequests()
                /* All access to the authentication service are permitted without authentication (actually as anonymous) */
               .antMatchers("/hr/**").authenticated()
                /* All the other requests need an authentication.
                 Role access is done on Methods using annotations like @PreAuthorize
                 */
                .and().authorizeRequests().anyRequest().permitAll().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        /*httpSecurity
                .antMatcher("/hr/**").authorizeRequests().anyRequest().permitAll().and()
                .formLogin().defaultSuccessUrl("/hr/getAllJobs").failureUrl("/login?error").and()
                .logout().logoutUrl("/hr/logout").logoutSuccessUrl("/login?logout");*/

      /*  httpSecurity.httpBasic().and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/hr/**").fullyAuthenticated().and()
                .authorizeRequests().anyRequest().permitAll().and()
                .formLogin().loginPage("http://192.168.1.29:4200/login").defaultSuccessUrl("http://localhost:4200/login").failureUrl("/login?error").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/getAllJobs");*/

    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource(contextSource())
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");*/
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource(contextSource())
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");
    }

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource(Arrays.asList("ldap://localhost:8389/"), "dc=springframework,dc=org");
    }
    /**YENİ*/
    @Bean
    public AuthenticationManager authenticationManagerBean1() throws Exception {
        return super.authenticationManagerBean();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }



    /**
     * yeni
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}