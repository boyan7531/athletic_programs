package bg.softuni.athleticprogramapplication.config;

import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.impl.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@EnableWebSecurity
@Configuration
public class SecurityConfig{

    private final UserSession userSession;

    private final UserRepository userRepository;

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(UserSession userSession, UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.userSession = userSession;
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(
//                        authorizeRequest ->
//                            authorizeRequest
//                                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                    .antMatchers("/", "/login", "/register").permitAll()
//                                    .anyRequest().authenticated()
//
//                )
//                .formLogin(formLogin ->
//                    formLogin
//                            .loginPage("/login")
//                            .usernameParameter("username")
//                            .passwordParameter("password")
//                            .defaultSuccessUrl("/")
//                            .failureUrl("/login?error")
//                )
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers( "/","/users/login", "/users/register").anonymous()
                .antMatchers("/api/users/**").permitAll()
                .antMatchers("/users/profile").authenticated()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureForwardUrl("/users/login?error=true")
                .and()
                .rememberMe()
                .userDetailsService(customUserDetailsService)
                .tokenValiditySeconds(14 * 24 * 60 * 60)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");

        return http.build();
    }

    //
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(
//                        // Setup which URL-s are available to who
//                        authorizeRequests ->
//                                authorizeRequests
//                                        // all static resources to "common locations" (css, images, js) are available to anyone
//                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                        // some more resources for all users
//                                        .antMatchers("/", "/users/login", "/users/register", "/error", "/meals", "/about", "/programs").permitAll()
//                                        // all other URL-s should be authenticated.
//                                        .anyRequest()
//                                        .authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                // Where is our custom login form?
//                                .loginPage("/users/login")
//                                // what is the name of the username parameter in the Login POST request?
//                                .usernameParameter("username")
//                                // what is the name of the password parameter in the Login POST request?
//                                .passwordParameter("password")
//                                // What will happen if the login is successful
//                                .defaultSuccessUrl("/", true)
//                                // What will happen if the login fails
//                                .failureForwardUrl("/users/login?error")
//                )
//                .logout(
//                        logout ->
//                                logout
//                                        // what is the logout URL?
//                                        .logoutUrl("/users/logout")
//                                        // Where to go after successful logout?
//                                        .logoutSuccessUrl("/")
//                                        // invalidate the session after logout.
//                                        .invalidateHttpSession(true)
//                )
//                .build();
//    }



//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(
//                        authorizeRequests ->
//                                authorizeRequests
//                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                        .antMatchers("/", "/users/login", "/users/register", "/error", "/meals", "/about", "/programs").permitAll()
//                                        .anyRequest()
//                                        .authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/users/login")
//                                .usernameParameter("username")
//                                .passwordParameter("password")
//                                .defaultSuccessUrl("/", true)
//                                .failureUrl("/users/login?error")  // Use failureUrl instead of failureForwardUrl
//                )
//                .logout(
//                        logout ->
//                                logout
//                                        .logoutUrl("/users/logout")
//                                        .logoutSuccessUrl("/")
//                                        .invalidateHttpSession(true)
//                                        .deleteCookies("JSESSIONID")  // Optionally clear the JSESSIONID cookie to ensure clean logout
//                )
//                .csrf(csrf -> csrf
//                        .ignoringAntMatchers("/users/register", "/users/login") // Example, modify to suit your needs
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement
//                                .sessionFixation().migrateSession() // Ensures session remains consistent
//                                .maximumSessions(1)  // Ensures only one session per user
//                                .expiredUrl("/users/login?expired") // Redirect if session expires
//                )
//                .build();
//    }



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        authorizeRequests ->
//                                authorizeRequests
//                                        .antMatchers(
//                                                "/", "/users/login", "/users/register", "/about").permitAll()
//                                        .anyRequest().authenticated())
//                .formLogin(
//                        formLogin ->
//                                formLogin
//                                        .loginPage("/users/login")
//                                        .usernameParameter("username")
//                                        .passwordParameter("password")
//                                        .defaultSuccessUrl("/", true)
//                                        .failureForwardUrl("/about"))
//                .logout(
//                        logout ->
//                                logout
//                                        .logoutUrl("/users/logout")
//                                        .logoutSuccessUrl("/")
//                                        .invalidateHttpSession(true))
//                .build();
//    }
//


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserDetailsService userDetailsService() { return new CustomUserDetailsService(); }

}