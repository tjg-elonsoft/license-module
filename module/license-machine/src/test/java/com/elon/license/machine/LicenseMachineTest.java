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
    private static String LICENSE_PATH = "./license-file/test_license";
    private static License license;

    @BeforeClass
    public static void setUp() {
        license = new License(Arrays.asList(PolicyUtil.getHostName()), Arrays.asList("A4-83-E7-C1-52-3A","F2-8A-B2-F6-08-4D","AC-DE-48-00-11-22","A4-83-E7-C1-52-3A"), LocalDate.now());
    }
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