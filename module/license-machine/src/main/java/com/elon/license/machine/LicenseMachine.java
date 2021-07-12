package com.elon.license.machine;

import com.elon.license.machine.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * 라이센스 파일 발급 및 검증 기능 제공
 * @date 2021/07/12
 * @author kimtaehyun
*/
public class LicenseMachine {
    private static Log logger = LogFactory.getLog(LicenseMachine.class);
    /**
     * 공개키 파일로부터 공개키를 얻어 라이선스를 암호화한다.
     *
     * @param license 라이선스 정보가 담긴 객체
     * @param publicKeyFile 공개키가 들어있는 파일
     * @return byte[] 라이선스의 바이트 배열 값
     * @throws Exception
     */
    public static byte[] issue(License license, File publicKeyFile) throws Exception {

        logger.info("issue start..");
        PublicKey publicKey = KeyUtil.getPublicKeyByFile(publicKeyFile.getAbsolutePath()); // 파일로부터 공개키를 얻어옴
        return generateLicense(license, publicKey);
    }

    /**
     * 공개키를 이용하여 라이선스를 암호화한다.
     *
     * @param license 라이선스 정보가 담긴 객체
     * @param publicKey 공개키
     * @return byte[] 라이선스의 바이트 배열 값
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    private static byte[] generateLicense(License license, PublicKey publicKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {

        logger.info("generateLicense..");
        String plainText = license.toStringWithPipe(); // license를 만들기 전 license의 정보를 문자열로 변환함(Delimeter : "|").
        return CipherUtil.encryptRSA(plainText, publicKey);
    }


    /**
     * 라이선스 파일을 개인키 파일로부터 개인키를 얻어 복호화 후 검증한다.
     *
     * @param licensePath 라이선스 파일이 저장되어 있는 경로
     * @param privateKeyPath 개인키가 저장되어 있는 경로
     * @return boolean 검증 성공시 true, 실패시 false
     * @throws Exception
     */
    public static boolean verify(String licensePath, String privateKeyPath) throws Exception {

        logger.info("verify start..");
        PrivateKey privateKey = KeyUtil.getPrivateKeyByFilePath(privateKeyPath);
        return verifyLicense(FileUtil.readFile(licensePath), privateKey);
    }

    /**
     * 라이센스 파일 검증
     * @param encrypted
     * @param privateKey
     * @return
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
    private static boolean verifyLicense(byte[] encrypted, PrivateKey privateKey) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        String[] result = new String[0];
        try {
            // 암호화된 라이센스 데이터를 복호화 한다.
            String plainText = CipherUtil.decryptRSA(encrypted, privateKey);
            // 구분자 분리
            result = plainText.split("\\|");

            String hostName = result[0];
            String macAddress = result[1];
            String expiredDate = result[2];
            // 라이센트 파일을 읽었지만 해당 필요 정보가 없을때는 검증실패
            if (StringUtil.isNull(hostName) || StringUtil.isNull(macAddress) || StringUtil.isNull(expiredDate)) {
                return false;
            }

            String curHostName = PolicyUtil.getHostName();
            List<String> curMacAddress = PolicyUtil.getLocalMacAddresses();
            // 현재 접속한 시스템의 호스트 정보가 라이센스에 등록되어있지 않다면 검증 실패
            if (!hostName.contains(curHostName)) {
                return false;
            }
            boolean isMacAddr = true;
            for (String address : curMacAddress) {
                if (macAddress.contains(address)) {
                    isMacAddr = false;
                    break;
                }
            }
            // 맥어드레스 검증
            if (isMacAddr) {
                return false;
            }

            LocalDate expirationDate = LocalDate.parse(result[2], DateTimeFormatter.ISO_DATE);
            LocalDate today = LocalDate.now();
            Period period = expirationDate.until(today);
            // 유효기간 검증
            if (today.isAfter(expirationDate)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Arrays.stream(result).forEach(item -> System.out.println("item {} => " + item));
        }
        return true;
    }
}
