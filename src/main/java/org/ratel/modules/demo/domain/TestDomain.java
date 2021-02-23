package org.ratel.modules.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Cache;
import org.hibernate.envers.Audited;
import org.ratel.framework.jpa.JpaConstant;
import org.ratel.framework.jpa.domain.BaseUuidEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Audited
@Table(name = "test_domain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Where(clause = "enable="+ GlobalConstant.STATUS_VALUE)
@SQLDelete(sql = "update test_domain set enable=" + JpaConstant.DEL_FLAG_VALUE + " where id=?")
@SQLDeleteAll(sql = "update test_domain set enable=" + JpaConstant.DEL_FLAG_VALUE + " where id=?")
public class TestDomain extends BaseUuidEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;
    private String username;
    private String password;

    @Column(name = "user_flag", nullable = true)
    private String userFlag;
}
