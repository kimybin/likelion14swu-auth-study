package com.example.authstudy.domain;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemberStore {

    private final Map<String, String> members = new ConcurrentHashMap<>();

    public void save(String username, String encodedPassword) {
        members.put(username, encodedPassword);
    }

    public String findPassword(String username) {
        return members.get(username);
    }

    public boolean exists(String username) {
        return members.containsKey(username);
    }
}