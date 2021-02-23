package org.ratel.framework.rmi.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ratel.AppRun;
import org.ratel.framework.rmi.bean.TxnRequest;
import org.ratel.framework.rmi.bean.TxnResponse;
import org.ratel.framework.rmi.service.ITxnService;
import org.ratel.framework.spring.holder.SpringContextHolder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class RmiClientTest {
    @Test
    public void adminMenuTest() {

        ITxnService txnService = SpringContextHolder.getBean("txnService");
        TxnRequest txnRequest = new TxnRequest();
        txnRequest.setTxn("testProcess");
        TxnResponse txnResponse = txnService.txnProcess(txnRequest);
        System.out.println(txnResponse);
    }
}