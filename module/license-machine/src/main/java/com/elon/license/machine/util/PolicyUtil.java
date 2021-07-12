package com.elon.license.machine.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class PolicyUtil {
    private static Log logger = LogFactory.getLog(PolicyUtil.class);

    public static String getHostName(){
        // 시스템 환경변수 'HOSTNAME' 가져오기
        String hostName = System.getenv("HOSTNAME");
        if (hostName != null) {
            return hostName;
        }

        try{
            InetAddress ip = InetAddress.getLocalHost();
            hostName = ip.getHostName();
            if(hostName != null){
                return hostName;
            }
        }catch (UnknownHostException e){
            logger.error(e.getMessage());
            hostName = "";
        }

        String lineStr = "";
        try {
            // hostname 명령어 실행
            Process process = Runtime.getRuntime().exec("hostname");

            // hostname 결과 읽어들이는 과정
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((lineStr = bufferedReader.readLine()) != null) {
                hostName = lineStr;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            hostName = "";
        }
        return hostName;

    }

    /**
     * 로컬 맥 어드레스 가져오기
     * @return
     * @throws SocketException
     * @throws UnknownHostException
     */
    public static List<String> getLocalMacAddresses() throws SocketException, UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();

        List<String> macAddressList = new ArrayList<>();

        if(mac != null && mac.length > 0){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            macAddressList.add(sb.toString());
        }
        return macAddressList;
    }
}
