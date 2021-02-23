package org.ratel.modules.demo.repository;

import org.ratel.framework.jpa.repository.BaseRepository;
import org.ratel.modules.demo.domain.TestDomain;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDomainRepository extends BaseRepository<TestDomain, String> {
}
