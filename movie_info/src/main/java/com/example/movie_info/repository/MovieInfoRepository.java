package com.example.movie_info.repository;

import com.example.movie_info.MovieInfoApplication;
import com.example.movie_info.entity.MovieInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieInfoRepository extends JpaRepository<MovieInfoEntity, Long> {
    @Query("select info from MovieInfoEntity as info where info.miday > CURRENT_DATE and CAST(info.mistarttime AS java.util.Date) > CURRENT_TIME")
    public List<MovieInfoEntity> getInfo();

    @Query("SELECT DISTINCT e.cid FROM MovieInfoEntity e where info.miday > CURRENT_DATE and CAST(info.mistarttime AS java.util.Date) > CURRENT_TIME")
    List<Long> findDistinctCids();
}
