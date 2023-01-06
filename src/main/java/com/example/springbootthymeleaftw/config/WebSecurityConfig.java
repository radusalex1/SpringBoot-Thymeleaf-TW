package com.example.springbootthymeleaftw.config;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.Product;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            if (userRepository.findAll().size()==0) {


            RoleEntity admin = new RoleEntity(Roles.Admin.toString());
            RoleEntity b2c = new RoleEntity(Roles.B2C.toString());
            RoleEntity b2b = new RoleEntity(Roles.B2B.toString());
            RoleEntity client = new RoleEntity(Roles.Client.toString());

            roleRepository.saveAll(List.of(admin,b2c,b2b,client));

            UserEntity adminUser = new UserEntity();
            adminUser.setRoles(List.of(admin));
            adminUser.setPassword("Focabob(100)#");
            adminUser.setEmail("admin@yahoo.com");
            adminUser.setUsername("admin");

            UserEntity clientUser = new UserEntity();
            clientUser.setRoles(List.of(client));
            clientUser.setPassword("Focabob(100)#");
            clientUser.setEmail("client@yahoo.com");
            clientUser.setUsername("client");

            UserEntity b2c1 = new UserEntity();
            b2c1.setRoles(List.of(b2c));
            b2c1.setPassword("Focabob(100)#");
            b2c1.setEmail("b2c1@yahoo.com");
            b2c1.setUsername("b2c1");

            UserEntity b2c2 = new UserEntity();
            b2c2.setRoles(List.of(b2c));
            b2c2.setPassword("Focabob(100)#");
            b2c2.setEmail("b2c2@yahoo.com");
            b2c2.setUsername("b2c2");

            UserEntity b2b1 = new UserEntity();
            b2b1.setRoles(List.of(b2b));
            b2b1.setPassword("Focabob(100)#");
            b2b1.setEmail("b2b1@yahoo.com");
            b2b1.setUsername("b2b1");

            UserEntity b2b2 = new UserEntity();
            b2b2.setRoles(List.of(b2b));
            b2b2.setPassword("Focabob(100)#");
            b2b2.setEmail("b2b2@yahoo.com");
            b2b2.setUsername("b2b2");

            userRepository.saveAll(List.of(adminUser,clientUser,b2c1,b2c2,b2b1,b2b2));

            }

        };
    }
}
