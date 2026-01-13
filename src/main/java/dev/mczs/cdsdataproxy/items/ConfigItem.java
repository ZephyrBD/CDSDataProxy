package dev.mczs.cdsdataproxy.items;

import lombok.Data;

@Data
public class ConfigItem {
    private String cdsApiUrl;
    private Long contestId;
    private String adminID;
    private String adminPassword;
    private String teamsFile;
    private int serverPort;
    private boolean ignoreSSL;
    private int proxyPort;
}
