package com.example.springbootthymeleaftw.config;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.repository.ProductRepository;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {
    private static final String[] METHODS_ALLOWED = {
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name(),
    };


    @Bean
    SecurityFilterChain resources (HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers( "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                //.failureUrl("/login-error") //if you want a separate page for failed auth.
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(METHODS_ALLOWED)
                .allowedOrigins("*");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository, RoleRepository roleRepository, UserRepository userRepository){
        return args -> {
//            RoleEntity admin = new RoleEntity(Roles.Admin.toString());
//            RoleEntity b2c = new RoleEntity(Roles.B2C.toString());
//            RoleEntity b2b = new RoleEntity(Roles.B2B.toString());
//            RoleEntity client = new RoleEntity(Roles.Client.toString());
//
//            roleRepository.saveAll(List.of(admin,b2c,b2b,client));

//            Role admin1 = roleRepository.findRoleByName(Roles.Admin.toString());
//
//            User user = new User("radus_alexandru@yahoo.com",admin1);
//
//            userRepository.save(user);

        };
    }
}
