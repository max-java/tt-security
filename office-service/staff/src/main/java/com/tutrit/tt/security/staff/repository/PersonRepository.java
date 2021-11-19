package com.tutrit.tt.security.staff.repository;

import com.tutrit.tt.security.staff.bean.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Modifying
    @Transactional
    @Query("update Person p set p.inOffice = :status where p.cardId = :cardId")
    void updateStatus(@Param(value = "cardId") long cardId, @Param(value = "status") Boolean status);
}
