package com.elon.license.app.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 키 쌍 생성 및 키 파일 읽는 기능 제공
 * @date 2021/07/12
 * @author kimtaehyun
*/
public class KeyUtil {
    private static Log logger = LogFactory.getLog(KeyUtil.class);

    /**
     * SecureRandom 을 이용해 KeyPair 생성
     * @return KeyPair (쌍)
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {
        logger.info("generator rsa key pair start");

        SecureRandom secureRandom = new SecureRandom();

        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA"); // RSA 알고리즘 방식으로 생성
        gen.initialize(1024, secureRandom); // 1024 사이즈 설정

        return  gen.genKeyPair();
    }

    /**
     * private key 경로를 입력받아 PrivateKey 을 얻어옴
     * @param privateKeyFilePath
     * @return PrivateKey
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static PrivateKey getPrivateKeyByFilePath(String privateKeyFilePath) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        logger.info("get private key by file path");
        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyFilePath)); // PEM 파일이 아닐 경우
        return generatePrivateKey(keyBytes);
    }

    /**
     * 바이트 배열 형태에서 개인키 추출
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static PrivateKey generatePrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    /**
     * Base64로 인코딩된 개인키 문자열에서 개인키를 얻는다.
     *
     * @param privateKeyString Base64로 인코딩된 개인키 문자열
     * @return PrivateKey 개인키
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKeyByString(String privateKeyString)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        logger.info("getPrivateKey..");
        return generatePrivateKey(toByteByBase64(privateKeyString));
    }

    /**
     * 바이트 배열을 Base64로 인코딩된 문자열로 변환한다.
     *
     * @param encrypted 암호화한 결과로 만들어진 바이트 배열
     * @return String Base64로 인코딩된 바이트 배열
     */
    public static String toStringByBase64(byte[] encrypted) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encrypted);
    }

    /**
     * Base64로 인코딩된 문자열을 바이트 배열로 변환하는 메서드
     *
     * @param encrypted Base64로 인코딩된 문자열
     * @return byte[] 바이트 배열로 디코딩된 문자열
     */
    public static byte[] toByteByBase64(String encrypted) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(encrypted);
    }

    /**
     * 공개키가 들어있는 파일에서 공개키를 얻는다.
     *
     * @param publicKeyFilePath 공개키가 저장되어 있는 경로
     * @return PublicKey 공개키
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKeyByFile(String publicKeyFilePath)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        logger.info("getPublicKey..");
        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyFilePath));
        return generatePublicKey(keyBytes);
    }

    /**
     * 바이트 배열 형태의 공개키에서 공개키를 얻는다.
     *
     * @param keyBytes 공개키의 byte 배열
     * @return PublicKey 공개키
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static PublicKey generatePublicKey(byte[] keyBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * Base64로 인코딩된 공개키 문자열에서 공개키를 얻는다.
     *
     * @param publicKeyString Base64로 인코딩된 공개키 문자열
     * @return PublicKey 공개키
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKeyByString(String publicKeyString)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        logger.info("getPublicKeyByHexString..");
        return generatePublicKey(toByteByBase64(publicKeyString));
    }
}
