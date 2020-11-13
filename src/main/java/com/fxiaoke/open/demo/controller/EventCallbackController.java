package com.fxiaoke.open.demo.controller;

import com.fxiaoke.open.demo.controller.arg.CrmCallBackArg;
import com.fxiaoke.open.demo.controller.result.CrmCallBackResult;
import com.fxiaoke.open.demo.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/event/")
public class EventCallbackController extends BaseController {
    private static final String SUCCESS = "success";
    private String aesKey = "O0YjM0EDOygzNykzMzUjLwUTN3MjL5ADN1QjM5gTM0A=";

    @RequestMapping(value = "crmCallback", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public CrmCallBackResult crmCallBack(@RequestBody CrmCallBackArg arg) throws Exception {
        Integer retryTimes = arg.getRetryTimes();
        String signature = null;
        if (retryTimes != null) {
            signature = SignUtil.shaEncode(arg.getTimestamp() + arg.getNonce() + arg.getMessageId() + retryTimes + arg.getEnterpriseAccount() + arg.getEncryptedContent() + aesKey);
        } else {
            signature = SignUtil.shaEncode(arg.getTimestamp() + arg.getNonce() + arg.getMessageId() + arg.getEnterpriseAccount() + arg.getEncryptedContent() + aesKey);
        }
        //校验signature
        if (!arg.getSignature().equals(signature)) {
            Long returnTimestamp = System.currentTimeMillis();
            String returnEncryptedResult = SignUtil.encryptAes("sinature is wrong", aesKey);
            String returnNonce = (int) (Math.random() * 100000) + "";
            String returnSignature = SignUtil.shaEncode(returnTimestamp + returnNonce + returnEncryptedResult + aesKey);
            CrmCallBackResult crmCallBackResult = new CrmCallBackResult();
            crmCallBackResult.setTimestamp(returnTimestamp);
            crmCallBackResult.setEncryptedResult(returnEncryptedResult);
            crmCallBackResult.setNonce(returnNonce);
            crmCallBackResult.setSignature(returnSignature);
            log.warn("crmCallBack={}", crmCallBackResult);
            return crmCallBackResult;
        }
        //处理第三方业务逻辑
        String json = SignUtil.decryptAes(arg.getEncryptedContent(), aesKey);
        //....
        //返回结果
        Long returnTimestamp = System.currentTimeMillis();
        String returnEncryptedResult = SignUtil.encryptAes("success", aesKey);
        String returnNonce = (int) (Math.random() * 1000000) + "";
        String returnSignature = SignUtil.shaEncode(returnTimestamp + returnNonce + returnEncryptedResult + aesKey);
        CrmCallBackResult crmCallBackResult = new CrmCallBackResult();
        crmCallBackResult.setTimestamp(returnTimestamp);
        crmCallBackResult.setEncryptedResult(returnEncryptedResult);
        crmCallBackResult.setNonce(returnNonce);
        crmCallBackResult.setSignature(returnSignature);
        log.info("json={},crmCallBack={}", json, crmCallBackResult);
        return crmCallBackResult;
    }
}
