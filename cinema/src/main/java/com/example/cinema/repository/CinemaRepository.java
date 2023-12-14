package com.example.cinema.repository;

import com.example.cinema.entity.CinemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CinemaRepository extends JpaRepository<CinemaEntity, Long> {

    @Query("select c.tid from CinemaEntity as c where c.cid IN :cidList")
    public List<Long> findByCidsIn(@Param("cidList") List<Long> cidList);

    @Query("select c.cid from CinemaEntity as c where c.tid = :tid")
    public List<Long> findByTids(@Param("tid") Long tid);
}
