package org.ratel.framework.rmi.server;

import org.ratel.framework.rmi.bean.TxnRequest;
import org.ratel.framework.rmi.bean.TxnResponse;
import org.ratel.framework.rmi.server.process.RmiServerProcess;

public class TestRmiServerProcess implements RmiServerProcess {

    public TestRmiServerProcess() {
    }

    public TxnResponse process(TxnRequest txnRequest) {
        TxnResponse txnResponse = new TxnResponse();
        txnResponse.setTxn(txnRequest.getTxn());
        txnResponse.setErrorCode("0000");
        txnResponse.setValue("test", "hello");
        return txnResponse;
    }
}