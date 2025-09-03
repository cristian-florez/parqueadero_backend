package com.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Map;

//@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class QzSignatureController {

    private final PrivateKey qzPrivateKey;

    @PostMapping(value = "/sign", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sign(@RequestBody Map<String, String> body) throws Exception {
        String toSign = body.get("request");
        if (toSign == null) {
            return ResponseEntity.badRequest().body("missing 'request'");
        }

        // Firma con RSA-SHA256
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initSign(qzPrivateKey);
        sig.update(toSign.getBytes(StandardCharsets.UTF_8));

        String signed = Base64.getEncoder().encodeToString(sig.sign());
        return ResponseEntity.ok(signed);
    }
}
