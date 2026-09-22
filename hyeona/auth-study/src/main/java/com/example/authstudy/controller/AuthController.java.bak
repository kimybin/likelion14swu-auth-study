package com.example.authstudy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AuthController {

    private final Map<String, String> memberStore = new ConcurrentHashMap<>();

    private static final String SESSION_KEY = "userId";

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("username, password는 필수입니다.");
        }
        if (memberStore.containsKey(username)) {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다.");
        }

        memberStore.put(username, password);
        return ResponseEntity.ok("회원가입 완료: " + username);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request,
                                        HttpServletRequest httpRequest) {
        String username = request.get("username");
        String password = request.get("password");

        String storedPassword = memberStore.get(username);
        if (storedPassword == null || !storedPassword.equals(password)) {
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 세션 생성
        HttpSession session = httpRequest.getSession();
        session.setAttribute(SESSION_KEY, username);

        return ResponseEntity.ok("로그인 성공: " + username);
    }

    // 로그인 상태 확인
        @GetMapping("/me")
        public ResponseEntity<String> me(HttpServletRequest httpRequest) {
            HttpSession session = httpRequest.getSession(false); // 세션이 없으면 새로 만들지 않음
            if (session == null || session.getAttribute(SESSION_KEY) == null) {
                return ResponseEntity.status(401).body("로그인이 필요합니다.");
            }

            String username = (String) session.getAttribute(SESSION_KEY);
            return ResponseEntity.ok("현재 로그인된 사용자: " + username);
        }

    // 인증이 필요한 API
    @GetMapping("/my-posts")
    public ResponseEntity<String> myPosts(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(SESSION_KEY) == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String username = (String) session.getAttribute(SESSION_KEY);
        return ResponseEntity.ok(username + "님의 게시글 목록입니다. ");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 완료");
    }
}