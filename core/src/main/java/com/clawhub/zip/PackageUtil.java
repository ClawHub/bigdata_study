package com.clawhub.zip;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * <Description> 打包工具 <br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年04月09日<br>
 */
public class PackageUtil {
    /**
     * The Logger.
     */
    private static Logger logger = LogManager.getLogger(PackageUtil.class);
    /**
     * The constant 5.
     */
    public static final int FIVE = 5;

    /**
     * The constant 1024.
     */
    public static final int ONE_ZORE_TWO_FOUR = 1024;

    /**
     * Description: 打包Zip文件 <br>
     *
     * @param map 文件或文件夹的集合
     * @param out 输出的zip文件
     * @throws IOException IOException
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public static void doZipPack(Map<String, File> map, File out) throws IOException {
        if (map == null || map.size() <= 0) {
            logger.error("待处理文件为空！");
            return;
        }
        logger.debug("包：{}", out.getPath());
        //zip流
        ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
        for (Map.Entry<String, File> entry : map.entrySet()) {
            File file = entry.getValue();
            logger.debug("文件：{}", file.getPath());
            ZipArchiveEntry zae = new ZipArchiveEntry(file, entry.getKey());
            zaos.putArchiveEntry(zae);
            InputStream is = new FileInputStream(file);
            byte[] b = new byte[ONE_ZORE_TWO_FOUR * FIVE];
            int i;
            while ((i = is.read(b)) != -1) {
                zaos.write(b, 0, i);
            }
            is.close();
            zaos.closeArchiveEntry();
        }
        //关流
        zaos.finish();
        zaos.close();
    }

}

