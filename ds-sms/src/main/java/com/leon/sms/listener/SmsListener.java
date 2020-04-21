package com.leon.sms.listener;

import com.leon.sms.config.SmsProperties;
import com.leon.sms.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LeonMac
 * @description
 * @date 2020/4/9
 */

@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private SmsProperties smsProperties;

}
