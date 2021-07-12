package com.elon.license.machine.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;

import java.net.SocketException;
import java.net.UnknownHostException;

public class PolicyUtilTest {
    private static Log logger = LogFactory.getLog(PolicyUtilTest.class);

    /**
     * 시스템 호스트명 출력
     */
    @Ignore
    @Test
    public void getHostName() {
        logger.info(PolicyUtil.getHostName());
    }

    /**
     * 시스템 맥 어드레스 출력
     * @throws SocketException
     * @throws UnknownHostException
     */
    @Ignore
    @Test
    public void getLocalMacAddress() throws SocketException, UnknownHostException {
        logger.info(String. join(",", PolicyUtil.getLocalMacAddresses()));
    }
}