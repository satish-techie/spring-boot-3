package com.satish.techie.controller;

import com.satish.techie.records.TestData;
import com.satish.techie.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/token")
    public ResponseEntity<String> generateToken() {
        return ResponseEntity.ok(jwtService.generateToken());
    }

    @GetMapping("/payload")
    public ResponseEntity<TestData> getPayload(@RequestParam String token) {
        return ResponseEntity.ok(jwtService.getPayload(token));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        if (jwtService.verifyToken(token))
            return ResponseEntity.ok("Token verified");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Token verification failed");
    }

}
