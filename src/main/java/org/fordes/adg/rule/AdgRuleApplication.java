package org.fordes.adg.rule;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fordes.adg.rule.config.RuleConfig;
import org.fordes.adg.rule.thread.LocalRuleThread;
import org.fordes.adg.rule.thread.RemoteRuleThread;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
@AllArgsConstructor
@SpringBootApplication
public class AdgRuleApplication implements ApplicationRunner {

    private final static int n = 2 * Runtime.getRuntime().availableProcessors();

    private final RuleConfig ruleConfig;

    private final ThreadPoolExecutor executor = ExecutorBuilder.create()
            .setCorePoolSize(n)
            .setMaxPoolSize(n)
            .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
            .build();



    @Override
    public void run(ApplicationArguments args) throws Exception {
        TimeInterval interval = DateUtil.timer();
        // 初始化
        String rulePath = FileUtils.ROOT_PATH + File.separator + "rule" + File.separator;
        File allRule = FileUtils.createFile(rulePath + "all.txt");
        File adghRule = FileUtils.createFile(rulePath + "adgh.txt");
        File hostsRule = FileUtils.createFile(rulePath + "hosts.txt");

        BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 100000, 0.001);
        //远程规则
        ruleConfig.getRemote().stream()
                .filter(StrUtil::isNotBlank)
                .map(URLUtil::normalize)
                .forEach(e -> executor.execute(new RemoteRuleThread(allRule, adghRule, hostsRule, e, filter)));
        //本地规则
        ruleConfig.getLocal().stream()
                .filter(StrUtil::isNotBlank)
                .map(e -> rulePath + e)
                .forEach(e -> executor.execute(new LocalRuleThread(allRule, adghRule, hostsRule, e, filter)));

        while (true) {
            if (executor.getActiveCount() > 0) {
                log.info("working...");
                ThreadUtil.safeSleep(1000);
            } else {
                log.info("Done! {} ms", interval.intervalMs());
                System.exit(0);
            }

        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AdgRuleApplication.class, args);
    }
}
