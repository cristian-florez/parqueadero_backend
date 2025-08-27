package com.parqueadero.config;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Security;

@Configuration
public class QzKeyConfig {

    @Value("classpath:keys/private-key.pem")
    private Resource privateKeyPem;

    @Bean
    public PrivateKey qzPrivateKey() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        try (Reader reader = new InputStreamReader(privateKeyPem.getInputStream(), StandardCharsets.UTF_8);
             PEMParser pemParser = new PEMParser(reader)) {

            Object obj = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (obj instanceof PEMKeyPair pk) {
                // PKCS#1 sin cifrar
                return converter.getKeyPair(pk).getPrivate();
            } else if (obj instanceof PrivateKeyInfo pki) {
                // PKCS#8 sin cifrar
                return converter.getPrivateKey(pki);
            } else if (obj instanceof PEMEncryptedKeyPair) {
                // Si tu clave está con contraseña, aquí habría que descifrarla con el passphrase
                throw new IllegalStateException("La private-key.pem está cifrada. Quita la contraseña o implementa el descifrado.");
            } else {
                throw new IllegalArgumentException("Formato de clave no reconocido: " + obj.getClass());
            }
        }
    }
}
