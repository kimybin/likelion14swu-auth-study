package com.example.authstudy.controller;

import com.example.authstudy.domain.MemberStore;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MemberController {

    private final MemberStore memberStore;
    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberStore memberStore, PasswordEncoder passwordEncoder) {
        this.memberStore = memberStore;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입: 비밀번호를 BCrypt로 암호화해서 저장
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String rawPassword = request.get("password");

        if (username == null || rawPassword == null) {
            return ResponseEntity.badRequest().body("username, password는 필수입니다.");
        }
        if (memberStore.exists(username)) {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        memberStore.save(username, encodedPassword);

        return ResponseEntity.ok("회원가입 완료: " + username);
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(Authentication authentication) {
        return ResponseEntity.ok("현재 로그인된 사용자: " + authentication.getName());
    }
}