package com.elon.license.machine.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CipherUtilTest{
    private static Log logger = LogFactory.getLog(CipherUtilTest.class);

    private String plainText = "test1234"; // 암호화 테스트 평문
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    private static byte[] encrypted;

    /**
     * 테스트 실행전 항상 실행
     * @throws Exception
     */

    @BeforeClass
    public static void setUp() throws Exception {
        KeyPair keyPair = KeyUtil.genRSAKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    /**
     * public key 로 plain text 암호화 한 바이트 배열을 Base64로 인코딩 후 출력
     * @throws Exception
     */
    @Test
    public void encryptRSAByPublicKey() throws Exception {
        //
        encrypted = CipherUtil.encryptRSA(plainText, publicKey);
        logger.info(KeyUtil.toStringByBase64(encrypted));
        Assert.assertTrue(true);
        /**
         * ex
         * g5SaGDSzPcE6Tl8Fqftz4shhNKDFuY4WT+LFeoGiyF+AEXgwEvIdxsYFWXWM07Bko1pFxkgEyW3Qs1IToVRI7wbWioDn645qCvYuipFt94nn6/Vj+Ak7VT+wYdCsZt3NN/5SfYYvsp07HTtqZUiz4W2qIQ1DA4ueHrFtUJYojkU=
         */
    }

    /**
     * private key 로 복호화한 값이 암호화 테스트 평문과 일치하는지 비교 한다.
     * @throws Exception
     */
    @Test
    public void decryptRSAByPrivateKey() throws Exception{
        encryptRSAByPublicKey();
        String result = CipherUtil.decryptRSA(encrypted, privateKey);
        Assert.assertTrue("일치하지 않습니다.", plainText.equals(result));
    }

    /**
     * private key 로 plain text 암호화 한 바이트 배열을 Base64로 인코딩 후 출력
     * @throws Exception
     */
    @Test
    public void encryptRSAByPrivateKey() throws Exception{
        encrypted = CipherUtil.encryptRSA(plainText, privateKey);
        logger.info(KeyUtil.toStringByBase64(encrypted));
        Assert.assertTrue(true);
    }

    /**
     * private key 로 복호화한 값이 암호화 테스트 평문과 일치하는지 비교 한다.
     * @throws Exception
     */
    @Test
    public void decryptRSAByPublicKey() throws Exception{
        encryptRSAByPrivateKey();
        String result = CipherUtil.decryptRSA(encrypted, publicKey);
        Assert.assertTrue("일치하지 않습니다.", plainText.equals(result));
    }

    /**
     * private key 파일을 일어 Base64 로 인코딩
     * @throws Exception
     */
    @Ignore
    @Test
    public void getPrivateKeyByFile() throws Exception {
        PrivateKey privateKey = KeyUtil
                .getPrivateKeyByFilePath("./key-file/license-privkey.der");
        logger.info(KeyUtil.toStringByBase64(privateKey.getEncoded()));
    }

    /**
     * public key 파일을 일어 Base64 로 인코딩
     * @throws Exception
     */
    @Ignore
    @Test
    public void getPublicKeyByFile() throws Exception {
        PublicKey publicKey = KeyUtil.getPublicKeyByFile("./key-file/license-pubkey.der");
        logger.info(KeyUtil.toStringByBase64(publicKey.getEncoded()));
    }
}