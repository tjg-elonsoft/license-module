package com.elon.license.app;

/**
 * 라이센스 체크 정보를 받는
 */
public class LicenseCheckResult {
    private boolean valid = true; // 라이센스 검증 결과
    private String message; // 라이센스 검증 결과 메세지
    private int lastPeriod = 0; // 유효기간이 만료 된 경우에는 만료된 일수

    public LicenseCheckResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public LicenseCheckResult(boolean valid, String message, int lastPeriod) {
        this.valid = valid;
        this.message = message;
        this.lastPeriod = lastPeriod;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLastPeriod() {
        return lastPeriod;
    }

    public void setLastPeriod(int lastPeriod) {
        this.lastPeriod = lastPeriod;
    }

    @Override
    public String toString() {
        return "LicenseCheckResult{" +
                "valid=" + valid +
                ", message='" + message + '\'' +
                ", lastPeriod=" + lastPeriod +
                '}';
    }
}
