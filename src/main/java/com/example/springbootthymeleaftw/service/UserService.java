package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optUser = userRepository.findByEmail(email);
        if (optUser.isPresent()) {
            UserEntity appUser = optUser.get();
            return new User(
                    appUser.getUsername(), appUser.getPassword(), true, true, true, true,
                    /* User Roles */
                    Objects.isNull(appUser.getRoles()) ?
                            new ArrayList(List.of(new SimpleGrantedAuthority("default")))
                            : appUser.getRoles()
                                .stream()
                                .map(RoleEntity::getName)
                                .map(SimpleGrantedAuthority::new)
                                .toList()
            );
        }
        throw new UsernameNotFoundException(email);
    }

    public List<UserEntity> getB2Cs(){
        List<UserEntity> b2cs = new ArrayList<UserEntity>();
        List<UserEntity> users = userRepository.findAll();

        for (UserEntity u:users) {
            List<RoleEntity> roles = u.getRoles().stream().toList();
            for (RoleEntity r:roles) {
                if(r.getName().equals(Roles.B2C.toString())){
                    b2cs.add(u);
                    break;
                }
            }
        }

        return b2cs;
    }
    public void save(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void login(String email, String password){
        UserDetails userDetails = this.loadUserByUsername(email);

        if(Objects.isNull(userDetails))
            return;

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    public  Optional<UserEntity> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
