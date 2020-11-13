package com.fxiaoke.open.demo.controller.result;

import lombok.Data;

@Data
public class CrmCallBackResult {
    private String signature;
    private Long timestamp;
    private String nonce;
    private String encryptedResult;
}
