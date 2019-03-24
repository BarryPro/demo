package com.barry.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author yuchenglong03
 * @since 2019-03-22 17:19
 */
@Slf4j
public class FileUtil {

    /**
     * 读取absolutePath所指的文件内容
     *
     * @param absolutePath
     */
    public static void readFile(String absolutePath){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)));
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>开始读取文件内容");
            String buffer;
            // -1是读文件流的结束标识想
            while((buffer = reader.readLine()) != null){
                System.out.println(buffer);
            }
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>文件内容读取完毕");
        } catch (Exception e) {
            log.error("读取文件失败",e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("FileUtil.readFile BufferedReader 文件流关闭失败 absolutePath {}",absolutePath,e);
                }
            }
        }
    }

    /**
     * 把context写到absolutePath的文件中
     *
     * @param absolutePath 文件的绝对路径
     * @param context 要写入文件的内容
     */
    public static void writeFile(String absolutePath,String context){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absolutePath)));
            writer.write(context);
            writer.flush();
        } catch (Exception e) {
            log.error("写文件失败",e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("FileUtil.writeFile BufferedWriter 文件流关闭失败 absolutePath {}",absolutePath,e);
                }
            }
        }
    }
}
