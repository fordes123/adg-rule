package org.fordes.adg.rule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Data
@Component
@ConfigurationProperties(prefix = "rule")
public class RuleConfig {

    private List<String> remote;

    private List<String> local;
}
