package com.elon.license.machine.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * KeyUtil 테스트
 * @date 2021/07/12
 * @author kimtaehyun
*/
public class KeyUtilTest {
    private static Log logger = LogFactory.getLog(KeyUtilTest.class);

    /**
     * 키 쌍 발급 및 테스트
     * 암복화까지 확인
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void genRSAKeyPair() throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, UnsupportedEncodingException {
        String painText = "1234";
        KeyPair keyPair = KeyUtil.genRSAKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encrypted = CipherUtil.encryptRSA(painText, publicKey);
        String decrypted = CipherUtil.decryptRSA(encrypted, privateKey);
        logger.info("publicKey : " + KeyUtil.toStringByBase64(publicKey.getEncoded()));
        logger.info("privateKey : " + KeyUtil.toStringByBase64(privateKey.getEncoded()));
        Assert.assertTrue(painText.equals(decrypted));
    }
}