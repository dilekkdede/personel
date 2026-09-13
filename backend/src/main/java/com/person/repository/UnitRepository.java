package com.person.repository;

import com.person.entites.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query(value = "select count (p) from Unit p")
    int unitListesiCount();



}
