package com.example.springbootthymeleaftw.config;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.ProductRepository;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
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
            RoleEntity b2c = new RoleEntity(Roles.BTOC.toString());
            RoleEntity b2b = new RoleEntity(Roles.BTOB.toString());
            RoleEntity client = new RoleEntity(Roles.Client.toString());

            roleRepository.saveAll(List.of(admin,b2c,b2b,client));

            UserEntity adminUser = new UserEntity();
            adminUser.setRoles(List.of(admin));
            adminUser.setPassword("Admin(100)#");
            adminUser.setEmail("admin@yahoo.com");
            adminUser.setUsername("admin");

            userRepository.saveAll(List.of(adminUser));

            }

        };
    }
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("blagamihai17@gmail.com");
//        mailSender.setPassword("crsyyqotsbkswenn");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//
//        return mailSender;
//    }
}
