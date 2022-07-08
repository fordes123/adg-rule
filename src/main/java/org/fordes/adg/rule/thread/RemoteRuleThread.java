package org.fordes.adg.rule.thread;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.google.common.hash.BloomFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

@Slf4j
public class RemoteRuleThread extends AbstractRuleThread {

    public RemoteRuleThread(File allFile, File adghFile, File hostsFile, String ruleUrl, BloomFilter<String> filter) {
        super(allFile, adghFile, hostsFile, ruleUrl, filter);
    }

    @Override
    InputStream getContentStream() {
        HttpResponse response = HttpRequest.get(getRuleUrl())
                .setFollowRedirects(true)
                .timeout(20000)
                .execute();
        if (response.isOk()) {
            setCharset(Charset.forName(response.charset()));
            return response.bodyStream();
        }
        return null;
    }

}
