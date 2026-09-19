package com.person.repository;

import com.person.entites.Personel;
import com.person.enums.EmploymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PersonelRepository extends JpaRepository<Personel, Long> {

    @Query("select p from Personel p order by p.createDate desc")
    List<Personel> findAllPersons();

    Page<Personel> findAllByOrderByCreateDateDesc(Pageable pageable);

    @Query("select p from Personel p where " +
            "(:q is null or :q = '' or lower(p.firstName) like lower(concat('%', :q, '%')) " +
            "or lower(p.lastName) like lower(concat('%', :q, '%')) " +
            "or lower(p.userName) like lower(concat('%', :q, '%')) " +
            "or lower(p.bolum) like lower(concat('%', :q, '%'))) " +
            "and (:cityId is null or p.city.id = :cityId) " +
            "and (:unitId is null or p.unit.id = :unitId) " +
            "and (:bolum is null or :bolum = '' or lower(p.bolum) like lower(concat('%', :bolum, '%'))) " +
            "and (:employmentStatus is null or p.employmentStatus = :employmentStatus " +
            "or (p.employmentStatus is null and :matchNullAsActive = true)) " +
            "order by p.createDate desc")
    Page<Personel> search(@Param("q") String q,
                          @Param("cityId") Long cityId,
                          @Param("unitId") Long unitId,
                          @Param("bolum") String bolum,
                          @Param("employmentStatus") EmploymentStatus employmentStatus,
                          @Param("matchNullAsActive") boolean matchNullAsActive,
                          Pageable pageable);

    List<Personel> findTop5ByOrderByCreateDateDesc();

    List<Personel> findByUnitIsNull();

    List<Personel> findByCreateDateAfterOrderByCreateDateDesc(Date date);

    long countByEmploymentStatus(EmploymentStatus employmentStatus);

    long countByEmploymentStatusIsNull();

    boolean existsByCity_Id(Long cityId);

    boolean existsByUnit_Id(Long unitId);
}
