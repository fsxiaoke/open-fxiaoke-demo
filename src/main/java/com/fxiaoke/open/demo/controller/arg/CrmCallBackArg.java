package com.fxiaoke.open.demo.controller.arg;

import lombok.Data;

@Data
public class CrmCallBackArg {
    private String signature;
    private Long timestamp;
    private String nonce;
    private String messageId;
    private Integer retryTimes;
    private String enterpriseAccount;
    private String encryptedContent;
}
