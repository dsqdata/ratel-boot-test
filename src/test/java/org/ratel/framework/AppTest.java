package org.ratel.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ratel.AppRun;
import org.ratel.modules.demo.domain.TestDomain;
import org.ratel.modules.demo.service.TestDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class)
public class AppTest {

    @Autowired
    TestDomainService testDomainService;

    @Test
    public void adminMenuTest() {
        List<TestDomain> list = new ArrayList();
        TestDomain testDomain01 = new TestDomain();
        testDomain01.setName("00000");

        TestDomain testDomain02 = new TestDomain();
        testDomain02.setName("00000");

        list.add(testDomain01);
        list.add(testDomain02);

        testDomainService.saveList(list);
    }

    @Test
    public void adminMenuTest2() {
        List<TestDomain> list = new ArrayList();
        TestDomain testDomain01 = new TestDomain();
        testDomain01.setName("00000");

        TestDomain testDomain02 = new TestDomain();
        testDomain02.setName("00000");

        list.add(testDomain01);
        list.add(testDomain02);
        testDomainService.saveListNoException(list);
    }


}