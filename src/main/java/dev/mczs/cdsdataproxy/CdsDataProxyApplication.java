package dev.mczs.cdsdataproxy;

import dev.mczs.cdsdataproxy.items.ConfigItem;
import dev.mczs.cdsdataproxy.util.SslUtil;
import dev.mczs.cdsdataproxy.util.initUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.util.Base64;
import java.util.logging.Logger;

import static dev.mczs.cdsdataproxy.util.FilesUtil.readConfig;

@SpringBootApplication
public class CdsDataProxyApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    public static ConfigItem CONFIG = readConfig();
    public static final Logger LOGGER = Logger.getLogger(CdsDataProxyApplication.class.getName());
    public static final RestTemplate REST_TEMPLATE;
    static {
        REST_TEMPLATE = new RestTemplate();
        // 添加SSL忽略配置
        if(CONFIG.isIgnoreSSL()) {
            try {
                SSLContext sslContext = SslUtil.createIgnoreVerifySSL();
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            } catch (Exception e) {
                LOGGER.warning(e.getMessage());
            }
        }
        // 保留原有的Basic认证拦截器
        REST_TEMPLATE.getInterceptors().add((request, body, execution) -> {
            String auth = CONFIG.getAdminID() + ":" + CONFIG.getAdminPassword();
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            request.getHeaders().set("Authorization", "Basic " + new String(encodedAuth));
            return execution.execute(request, body);
        });
    }
    public static void main(String[] args) {
        initUtil.printMyInfo();
        SpringApplication.run(CdsDataProxyApplication.class, args);
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(CONFIG.getProxyPort());
    }
}
