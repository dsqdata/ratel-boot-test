package org.ratel.modules.test.repository;

import org.ratel.framework.jpa.repository.BaseRepository;
import org.ratel.modules.test.domain.TestDomain;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDomainRepository extends BaseRepository<TestDomain, String> {
}