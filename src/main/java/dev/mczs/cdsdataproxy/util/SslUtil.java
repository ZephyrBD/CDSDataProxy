package dev.mczs.cdsdataproxy.util;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

public class SslUtil {
    public static SSLContext createIgnoreVerifySSL() throws Exception {
        SSLContext sc = SSLContext.getInstance("SSL");
        // 信任所有证书
        X509TrustManager trustManager = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }
}