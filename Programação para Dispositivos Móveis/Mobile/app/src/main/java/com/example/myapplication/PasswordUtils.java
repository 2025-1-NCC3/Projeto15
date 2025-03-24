package com.example.myapplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
  Classe utilitária para manipulação segura de senhas.
  Fornece funcionalidade de hash para proteção de credenciais.
 */
public class PasswordUtils {

    /*
      Gera um hash SHA-256 de uma senha textual.
      @param password Senha em texto claro a ser criptografada
      @return String hexadecimal de 64 caracteres do hash gerado,
              ou null em caso de erro no algoritmo
     */
    public static String hashPassword(String password) {
        try {
            // Obtém instância do algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplica o hash aos bytes da senha
            byte[] hash = digest.digest(password.getBytes());

            // Converte bytes do hash para representação hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                // Converte cada byte para dois dígitos hexadecimais
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0'); // Padding para bytes < 0x10
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // Exceção nunca deve ocorrer pois SHA-256 é padrão Java
            e.printStackTrace();
            return null;
        }
    }
}