package dac2dac.doctect.user.service;

import dac2dac.doctect.user.dto.CustomUserDetails;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new CustomUserDetails(user);
        }
        // 사용자 정보를 찾지 못한 경우 UsernameNotFoundException을 던집니다.
        throw new UsernameNotFoundException("User not found with username: " + username);

    }
}
