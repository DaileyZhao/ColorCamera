package com.zcm.library.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    /**
     * 复制文件
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuffer = new BufferedInputStream(input);

        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuffer = new BufferedOutputStream(output);

        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuffer.read(b)) != -1) {
            outBuffer.write(b, 0, len);
        }

        outBuffer.flush();

        inBuffer.close();
        outBuffer.close();
        input.close();
        output.close();
    }
}
