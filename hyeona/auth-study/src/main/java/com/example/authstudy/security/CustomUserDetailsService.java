package com.example.authstudy.security;

import com.example.authstudy.domain.MemberStore;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberStore memberStore;

    public CustomUserDetailsService(MemberStore memberStore) {
        this.memberStore = memberStore;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encodedPassword = memberStore.findPassword(username);

        if (encodedPassword == null) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다: " + username);
        }

        // 별도 권한 체계 없이 기본 USER 권한만 부여
        return User.builder()
                .username(username)
                .password(encodedPassword) // 이미 암호화된 값 그대로 전달 (여기서 다시 인코딩하면 안 됨)
                .roles("USER")
                .build();
    }
}