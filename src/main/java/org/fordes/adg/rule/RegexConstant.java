package org.fordes.adg.rule;

public class RegexConstant {

    //匹配所有非规则
    public static final String RULE = "^[#,\\[,!].*";

    //匹配hosts规则
    public static final String HOSTS = "^\\d+\\.\\d+\\.\\d+\\.\\d+\\s+.*";

    //匹配AdGuardHome规则（不包含正则）
    public static final String BLOCK = "(^\\|\\|.*[\\^,\\^\\$important]$)|(^@@.*[\\^,\\^\\$important]$)|(^/.*/$)";

}
