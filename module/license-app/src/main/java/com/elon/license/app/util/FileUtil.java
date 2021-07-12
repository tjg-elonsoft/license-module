package com.elon.license.app.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 파일 생성 및 읽기 기능을 제공
 */
public class FileUtil {
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
