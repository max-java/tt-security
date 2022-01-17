package com.tutrit.tt.security.staff.repository;

import com.tutrit.tt.security.staff.bean.Person;
import com.tutrit.tt.security.staff.securityconfig.annotation.AdminRwAllUserRwOnlyFilteredByFullName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Modifying
    @Transactional
    @Query("update Person p set p.inOffice = :status where p.cardId = :cardId")
    void updateStatus(@Param(value = "cardId") long cardId, @Param(value = "status") Boolean status);

    @Override
    @AdminRwAllUserRwOnlyFilteredByFullName
    List<Person> findAll();
}
