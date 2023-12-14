package com.example.theater.repository;

import com.example.theater.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Query("select DISTINCT t from Theater as t where t.tid IN :tid")
    List<Theater> findByTidIn(List<Long> tid);
    @Query("select DISTINCT t from Theater as t where t.tid NOT IN :tid")
    List<Theater> findByTidNotIn(List<Long> tid);


}
