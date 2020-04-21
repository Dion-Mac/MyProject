package com.leon.cart.properties;

import com.leon.authentication.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @author LeonMac
 * @description
 */
@Configuration
@RefreshScope
public class JwtProperties {
    //公钥
    private PublicKey publicKey;

    //公钥地址
    @Value("S{leon.jwt.publicPath}")
    private String publicPath;

    //cookie名字
    @Value("${leon.jwt.cookieName}")
    private String cookieName;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicPath() {
        return publicPath;
    }

    public void setPublicPath(String publicPath) {
        this.publicPath = publicPath;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public static Logger getLogger() {
        return logger;
    }

    /**
    * @description: 在构造方法执行之后执行该方法：获取公钥
    * @param
    * @return void
    */
    @PostConstruct
    public void init() {
        try {
            this.publicKey = RsaUtils.getPublicKey(publicPath);
        } catch (Exception e) {
            logger.error("获取公钥失败");
            throw new RuntimeException();
        }
    }
}
