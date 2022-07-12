package org.fordes.adg.rule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static cn.hutool.core.thread.ThreadUtil.sleep;

@Slf4j
public class FileUtils {

    public static final String ROOT_PATH = System.getProperty("user.dir");

    public static final String UPDATE = "# Update time: {}\r\n";
    public static final String REPO = "# Repo URL: https://github.com/fordes123/adg-rule\r\n\r\n";

    /**
     * 加锁将集合按行写入文件
     *
     * @param file    目标文件
     * @param content 内容集合
     */
    public static void write(File file, Collection<String> content) {
        if (CollUtil.isNotEmpty(content)) {
            try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
                 FileChannel channel = accessFile.getChannel()) {
                //加锁写入文件，如获取不到锁则休眠
                FileLock fileLock = null;
                while (true) {
                    try {
                        fileLock = channel.tryLock();
                        break;
                    } catch (Exception e) {
                        sleep(1000);
                    }
                }
                accessFile.seek(accessFile.length());
                accessFile.write((CollUtil.join(content, StrUtil.CRLF)).getBytes(StandardCharsets.UTF_8));
                accessFile.write(StrUtil.CRLF.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ioException) {
                log.error("写入文件出错，{} => {}", file.getPath(), ioException.getMessage());
            }
        }
    }

    /**
     * 按路径创建文件，如存在则删除重新创建
     *
     * @param path 路径
     * @return {@link File}
     */
    public static File createFile(String path) {
        File file = FileUtil.file(path);
        if (FileUtil.exist(file)) {
            FileUtil.del(file);
        }
        FileUtil.touch(file);
        FileUtil.appendUtf8String(StrUtil.format(UPDATE, DateTime.now().toString(DatePattern.NORM_DATETIME_PATTERN)), file);
        FileUtil.appendUtf8String(REPO, file);
        return file;
    }

}
