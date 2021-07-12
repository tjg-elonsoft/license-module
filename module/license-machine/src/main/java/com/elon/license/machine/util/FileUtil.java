package com.elon.license.machine.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 파일 생성 및 읽기 기능을 제공
 */
public class FileUtil {
    /**
     * 파일 생성
     * @param fileFullPath : 생성될 파일 경로 (파일명포함)
     * @param data : 파일에 기록될 데이터
     * @throws IOException
     * @throws NullPointerException
     */
    public static void makeFile(String fileFullPath, byte[] data) throws IOException, NullPointerException {
        FileUtils.writeByteArrayToFile(new File(fileFullPath), data);
    }

    /**
     * 파일 읽기
     * @param fileFullPath : 읽어야할 파일 경로 (파일명포함)
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    public static byte[] readFile(String fileFullPath) throws IOException, NullPointerException {
        File file = new File(fileFullPath);
        return FileUtils.readFileToByteArray(file);
    }
}
