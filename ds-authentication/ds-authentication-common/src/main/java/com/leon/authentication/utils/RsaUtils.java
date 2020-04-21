package com.leon.authentication.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author LeonMac
 * @description
 * @date 2020/4/9
 */

public class RsaUtils {

    public static byte[] readFile(String fileName) throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    public static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        boolean s = dest.exists();
        if (!s) {
            //当且仅当具有此名称的文件尚不存在时，原子地创建一个由此抽象路径名命名的新空文件。
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }

    /*
    * @description:
                根据公钥的保存路径(相对于classpath)，从文件中读取公钥
    * @date 2020/4/9 14:18
    * @param filename
    * @return java.security.PublicKey
    */
    public static PublicKey getPublicKey(String filename) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bytes = readFile(filename);
        //最终还是通过字节数组的方式拿到公钥
        return getPublicKey(bytes);
    }

    /*
    * @description:
                根据公钥的字节形式获取公钥
    * @date 2020/4/9 14:17
    * @param bytes
    * @return java.security.PublicKey
    */
    public static PublicKey getPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    /*
    * @description:
                根据私钥的保存路径(相对于classpath)，从文件中读取私钥
    * @date 2020/4/9 14:48
    * @param filename
    * @return java.security.PrivateKey
    */
    public static PrivateKey getPrivateKey(String filename) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        byte[] bytes = readFile(filename);
        return getPrivateKey(bytes);
    }

    /*
    * @description:
                根据私钥的字节形式获取私钥
    * @date 2020/4/9 14:48
    * @param bytes
    * @return java.security.PrivateKey
    */
    public static PrivateKey getPrivateKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        writeFile(publicKeyFilename, publicKeyBytes);
        //获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        writeFile(privateKeyFilename, privateKeyBytes);
    }











}
