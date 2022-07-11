package org.fordes.adg.rule.thread;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.google.common.hash.BloomFilter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.fordes.adg.rule.FileUtils;
import org.fordes.adg.rule.RegexConstant;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 规则处理线程抽象
 *
 * @author ChengFengsheng on 2022/7/7
 */
@Slf4j
@Data
public abstract class AbstractRuleThread implements Runnable {

    private final String ruleUrl;

    private final File allFile;

    private final File adghFile;

    private final File hostsFile;

    private final BloomFilter<String> filter;

    public AbstractRuleThread(File allFile, File adghFile, File hostsFile,
                              String ruleUrl, BloomFilter<String> filter) {
        this.ruleUrl = ruleUrl;
        this.allFile = allFile;
        this.adghFile = adghFile;
        this.hostsFile = hostsFile;
        this.filter = filter;
    }

    private Charset charset = Charset.defaultCharset();

    abstract InputStream getContentStream();

    @Override
    public void run() {
        try {
//             log.debug("begin~ {}", Thread.currentThread().getName());
            log.info("begin~ {}", this.ruleUrl);
            List<String> hosts = new ArrayList<>();
            List<String> block = new ArrayList<>();
            List<String> all = new ArrayList<>();
            IoUtil.readLines(getContentStream(), charset, (LineHandler) line -> {
                if (StrUtil.isNotBlank(line)) {
                    line = StrUtil.trim(HtmlUtil.cleanHtmlTag(line));
                    if (!filter.mightContain(line)) {
                        filter.put(line);
                        if (!ReUtil.isMatch(RegexConstant.RULE, line)) {
                            all.add(line);
                            if (ReUtil.isMatch(RegexConstant.HOSTS, line)) {
                                hosts.add(line);
                            } else if (ReUtil.isMatch(RegexConstant.BLOCK, line)) {
                                block.add(line);
                            }
                        }
                    }
                }
            });
            FileUtils.write(hostsFile, hosts);
            FileUtils.write(adghFile, CollUtil.addAll(hosts, block));
            FileUtils.write(allFile, all);
        } catch (Exception e) {
            log.error(ExceptionUtil.stacktraceToString(e));
        }
    }
}
