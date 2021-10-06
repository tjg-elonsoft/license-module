package com.elon.license.machine;

import com.elon.license.machine.util.FileUtil;
import com.elon.license.machine.util.KeyUtil;
import com.elon.license.machine.util.PolicyUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;

public class LicenseMachineTest{
    private static Log logger = LogFactory.getLog(LicenseMachineTest.class);
    private static String PUBLIC_KEY_PATH = "./key-file/license-pubkey.der";
    private static String PRIVATE_KEY_PATH = "./key-file/license-privkey.der";
    private static String LICENSE_PATH = "./license-file/tera/elon_license";
    private static License license;

    @BeforeClass
    public static void setUp() {
//        license = new License(Arrays.asList(PolicyUtil.getHostName()), Arrays.asList("00-15-5D-D3-35-5E"),
//                LocalDate.now().plusMonths(14));

        //Tera
        license = new License(Arrays.asList("ip-172-30-12-187"), Arrays.asList("02-DE-46-66-E0-6E"),
                LocalDate.of(2021, 8, 31).plusMonths(14));

        //90days 한국어음중개
//        license = new License(Arrays.asList("ip-10-28-3-202"), Arrays.asList("0A-49-13-C9-BB-66"),
//                LocalDate.of(2021, 10, 1).plusMonths(14));
    }

    /**
     * 라이센스 파일 발급
     * @throws Exception
     */
    @Ignore
    @Test
    public void testIssueLicenseFile() throws Exception {
        File file = new File(PUBLIC_KEY_PATH);
        byte[] licenseByte = LicenseMachine.issue(license, file);
        FileUtil.makeFile(LICENSE_PATH, licenseByte);
        logger.info(KeyUtil.toStringByBase64(licenseByte));
    }
    @Test
    public void verifyLicenseFile() throws Exception {
        boolean isVerify = LicenseMachine.verify(LICENSE_PATH, PRIVATE_KEY_PATH);
        Assert.assertTrue("검증 실패",isVerify == true);

    }
}