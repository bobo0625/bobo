package com.shubo.bis.util;

import com.shubo.bis.constants.ErrorCode;
import com.shubo.bis.util.cache.BaseLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/22,15:43
 */
public class FileUtiles {
    /**
     * 读取地址目录文件
     * @param filePath
     * @return
     * @throws Exception
     */
    public byte[] readFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            BaseLog.getDailyLog().info("输入参数为空");
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            BaseLog.getDailyLog().info("{}内文件不存在" + filePath);
            return null;
        }
        InputStream is = null;
        byte[] byteArray = null;
        try {
            is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            byteArray = bos.toByteArray();
        } catch (IOException e) {
            BaseLog.getDailyLog().info("文件读取失败", e);
        }
        return byteArray;
    }

    /**
     * 读取execl表格
     * @param filePath
     * @return
     */
    public Workbook readExcel(String filePath) {
        Workbook wb = null;
        File file = new File(filePath);
        String fileName = file.getName();
        byte[] bytes = this.readFile(filePath);
        InputStream is = new ByteArrayInputStream(bytes);
        try {
            if (fileName.endsWith(".xls")) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
        }
        return wb;
    }
    /**
     * byte转换为文件
     * @param bytes
     * @param excelPath
     * @throws Exception
     */
    private static void ByteToFile(byte[] bytes, String excelPath)throws Exception{
        InputStream inputStreams = new ByteArrayInputStream(bytes);
        Files.copy(inputStreams, Paths.get(excelPath));
    }
}
