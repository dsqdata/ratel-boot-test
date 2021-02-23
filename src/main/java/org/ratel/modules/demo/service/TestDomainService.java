package org.ratel.modules.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.ratel.framework.jpa.service.BaseService;
import org.ratel.modules.demo.domain.TestDomain;
import org.ratel.modules.demo.repository.TestDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TestDomainService extends BaseService<TestDomain, String> {
    @Autowired
    private TestDomainRepository testDomainRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveList(List<TestDomain> testDomains) {
        log.error("0000");
        log.info("1000");
        log.warn("2000");

        testDomainRepository.save(testDomains.get(0));
        throw new RuntimeException("");
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveListNoException(List<TestDomain> testDomains) {
        testDomains.stream().map(
                item -> testDomainRepository.save(item)
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveListNoException2(List<TestDomain> testDomains) {
        for (TestDomain testDomain : testDomains) {
            testDomainRepository.save(testDomain);
        }
    }
}
