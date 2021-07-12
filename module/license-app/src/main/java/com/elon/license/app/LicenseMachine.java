package com.elon.license.app;

import com.elon.license.app.util.*;
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
    private static final String PRIVATE_KEY_PATH = "";
    /**
     * 라이선스 파일을 개인키 파일로부터 개인키를 얻어 복호화 후 검증한다.

     */
    public static LicenseCheckResult verify(String licensePath, String keyPath) throws Exception {
        logger.info("verify start..");
        PrivateKey privateKey = KeyUtil.getPrivateKeyByFilePath(keyPath);
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
    private static LicenseCheckResult verifyLicense(byte[] encrypted, PrivateKey privateKey) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

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
                return new LicenseCheckResult(false, "위 변조된 데이터 입니다.");
            }

            String curHostName = PolicyUtil.getHostName();
            List<String> curMacAddress = PolicyUtil.getLocalMacAddresses();
            // 현재 접속한 시스템의 호스트 정보가 라이센스에 등록되어있지 않다면 검증 실패
            if (!hostName.contains(curHostName)) {
                return new LicenseCheckResult(false, "라이센스에 등록되지 않는 하드웨어 정보 입니다.");
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
                return new LicenseCheckResult(false, "라이센스에 등록되지 않는 하드웨어 정보 입니다.");
            }

            LocalDate expirationDate = LocalDate.parse(result[2], DateTimeFormatter.ISO_DATE);
            LocalDate today = LocalDate.now();
            Period period = expirationDate.until(today);
            // 유효기간 검증
            if (today.isAfter(expirationDate)) {
                return new LicenseCheckResult(false, "사용기간이 만료 되었습니다.", period.getDays());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new LicenseCheckResult(false, "위 변조된 데이터 입니다.");
        } finally {
            Arrays.stream(result).forEach(item -> System.out.println("item {} => " + item));
        }
        return new LicenseCheckResult(true,"정상입니다.");
    }
}
