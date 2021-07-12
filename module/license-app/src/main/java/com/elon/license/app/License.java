package com.elon.license.app;

import java.time.LocalDate;
import java.util.List;

public class License {
    private List<String> hostName; // PC의 호스트 이름
    private List<String> macAddress; // PC의 물리 주소
    private LocalDate expirationDate; // 유효 기간은 파일을 생성한 날짜로부터 1년

    public License(List<String> hostName, List<String> macAddress, LocalDate expirationDate) {
        this.hostName = hostName;
        this.macAddress = macAddress;
        this.expirationDate = expirationDate;
    }

    public List<String> getHostName() {
        return hostName;
    }

    public List<String> getMacAddress() {
        return macAddress;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String toStringWithPipe() {
        return String.format("%s|%s|%s", hostName, macAddress, expirationDate.toString());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
