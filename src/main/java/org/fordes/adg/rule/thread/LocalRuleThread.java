package org.fordes.adg.rule.thread;

import cn.hutool.core.io.FileUtil;
import com.google.common.hash.BloomFilter;

import java.io.File;
import java.io.InputStream;

/**
 * 本地规则处理
 *
 * @author ChengFengsheng on 2022/7/7
 */
public class LocalRuleThread extends AbstractRuleThread {

    public LocalRuleThread(File allFile, File adghFile, File hostsFile, String ruleUrl, BloomFilter<String> filter) {
        super(allFile, adghFile, hostsFile, ruleUrl, filter);
    }

    @Override
    InputStream getContentStream() {
        return FileUtil.getInputStream(getRuleUrl());
    }
}
