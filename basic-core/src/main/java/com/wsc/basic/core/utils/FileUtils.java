package com.wsc.basic.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件信息表创建实体
 *
 * @author 吴淑超
 * @since 2020-10-22
 */
@Slf4j
public class FileUtils {

    /**
     * 从src\main\resources\template目录下载模板文件
     *
     * @param templateName 模板名称
     * @param fileName     下载文件名称
     * @param response     请求Response
     */
    public static void exportFile(String templateName, String fileName, HttpServletResponse response) {
        InputStream inputStream = getTemplateStream(templateName);
        //转码防止乱码
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //定义每次读取字节的大小与保存字节的数据
            byte[] bs = new byte[1024];
            //定义每次读取的长度
            int len;
            //循环读取数据，如果读取的数据为-1，说明已经读取了末尾
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
        } catch (IOException e) {
            log.error("数据处理异常:", e);
            throw new RuntimeException("数据处理异常");
        } finally {
            IOUtils.closeQuietly(outputStream,
                    (e) -> log.error("OutputStream close failed:", e));
            IOUtils.closeQuietly(inputStream,
                    (e) -> log.error("InputStream close failed:", e));
        }
    }

    /**
     * 使用模板导出excel
     *
     * @param templateName 模板名称
     * @param fileName     导出文件名称
     * @param response     请求Response
     * @param consumer     数据填充消费者
     */
    public static void exportExcel(String templateName, String fileName, HttpServletResponse response, Consumer<ExcelWriter> consumer) {
        InputStream inputStream = getTemplateStream(templateName);
        //转码防止乱码
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 使用模板导出
            ExcelWriter excelWriter = EasyExcel
                    .write(outputStream)
                    .withTemplate(inputStream)
                    .build();
            // 消费者填充数据
            consumer.accept(excelWriter);
            // 填充完成
            excelWriter.finish();
        } catch (IOException e) {
            log.error("数据处理异常:", e);
            throw new RuntimeException("数据处理异常");
        } finally {
            IOUtils.closeQuietly(outputStream,
                    (e) -> log.error("OutputStream close failed:", e));
            IOUtils.closeQuietly(inputStream,
                    (e) -> log.error("InputStream close failed:", e));
        }
    }

    /**
     * 使用模板导出excel到本地临时文件
     *
     * @param templateName 模板名称
     * @param fileName     文件名称
     * @param consumer     数据填充消费者
     * @return 临时文件路径
     */
    public static String exportTempFile(String templateName, String fileName, Consumer<ExcelWriter> consumer) {
        InputStream inputStream = getTemplateStream(templateName);
        try {
            // 文件名称处理
            String prefix = fileName;
            String suffix = "";
            if (fileName.indexOf(".") > 0) {
                prefix = fileName.substring(0, fileName.indexOf("."));
                suffix = fileName.substring(fileName.indexOf("."));
            }
            // 创建临时文件
            File tempFile = File.createTempFile(prefix, suffix);
            log.info("Temp file created:{} " + tempFile.getAbsolutePath());
            // 使用模板导出
            ExcelWriter excelWriter = EasyExcel
                    .write(tempFile)
                    .withTemplate(inputStream)
                    .build();
            // 消费者填充数据
            consumer.accept(excelWriter);
            // 填充完成
            excelWriter.finish();
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            log.error("数据处理异常:", e);
            throw new RuntimeException("数据处理异常");
        } finally {
            IOUtils.closeQuietly(inputStream,
                    (e) -> log.error("InputStream close failed:", e));
        }
    }

    /**
     * 导出下载压缩包
     *
     * @param response 响应流
     * @param files    key为文件名，value为路径
     */
    public static void downZip(HttpServletResponse response, Map<String, String> files) {
        String exportName = "data.zip";
        response.setContentType("application/x-zip-compressed");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(exportName.getBytes(), StandardCharsets.ISO_8859_1));
        ServletOutputStream outputStream = null;
        ZipOutputStream zip = null;
        ZipEntry entry;
        try {
            outputStream = response.getOutputStream();
            zip = new ZipOutputStream(outputStream);
            for (String key : files.keySet()) {
                entry = new ZipEntry(key);
                zip.putNextEntry(entry);
                try {
                    zip.write(org.apache.commons.io.FileUtils.readFileToByteArray(new File(files.get(key))));
                } catch (IOException ignored) {
                }
            }
            // 关闭Zip流
            zip.closeEntry();
            zip.close();
        } catch (IOException e) {
            log.error("导出zip包失败：", e);
        } finally {
            IOUtils.closeQuietly(zip,
                    (e) -> log.error("ZipOutputStream close failed:", e));
            IOUtils.closeQuietly(outputStream,
                    (e) -> log.error("OutputStream close failed:", e));
        }
    }

    /**
     * 获取模板文件文件流
     *
     * @param templateName 模板文件名称
     * @return 文件流
     */
    private static InputStream getTemplateStream(String templateName) {
        InputStream inputStream;
        try {
            String filePath = String.format("template/%s", templateName);
            inputStream = new ClassPathResource(filePath).getInputStream();
        } catch (IOException e) {
            log.error("模板不存在:", e);
            throw new RuntimeException(templateName + "模板文件不存在");
        }
        return inputStream;
    }

}