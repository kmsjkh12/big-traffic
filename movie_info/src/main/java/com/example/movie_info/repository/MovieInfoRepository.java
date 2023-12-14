package com.example.movie_info.repository;

import com.example.movie_info.entity.MovieInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MovieInfoRepository extends JpaRepository<MovieInfoEntity, Long> {
    @Query("select e from MovieInfoEntity as e where e.miday > CURRENT_DATE and CAST(e.mistarttime AS java.util.Date) > CURRENT_TIME")
    public List<MovieInfoEntity> getInfo();

    @Query("SELECT e FROM MovieInfoEntity e WHERE e.miday > CURRENT_DATE AND CAST(e.mistarttime AS java.util.Date) > CURRENT_TIME ORDER BY e.miday ASC")
    List<MovieInfoEntity> findReserverMiday();

    @Query("SELECT DISTINCT e.mid FROM MovieInfoEntity e WHERE " +
            "(:cidList IS NULL OR e.cid IN :cidList) AND " +
            "(COALESCE(:miday, CURRENT_DATE) IS NULL OR e.miday = COALESCE(:miday, CURRENT_DATE) OR (e.miday > COALESCE(:miday, CURRENT_DATE))) AND " +
            "(CAST(e.mistarttime AS java.util.Date) > CURRENT_TIME)")
    List<Long> findDistinctMidayAndCinIn(
            @Param("cidList") List<Long> cidList,
            @Param("miday") Date miday
    );
    @Query("SELECT DISTINCT e.cid FROM MovieInfoEntity e WHERE " +
            "(:mid IS NULL OR e.mid= :mid) AND " +
            "(COALESCE(:miday, CURRENT_DATE) IS NULL OR e.miday = COALESCE(:miday, CURRENT_DATE) OR (e.miday > COALESCE(:miday, CURRENT_DATE))) AND " +
            "(CAST(e.mistarttime AS java.util.Date) > CURRENT_TIME)")
    List<Long> findDistinctMidAndMiday(
            @Param("mid") Long mid,
            @Param("miday") Date miday
    );


    @Query("SELECT DISTINCT e FROM MovieInfoEntity e WHERE " +
            "(:mid IS NULL OR e.mid = :mid) AND " +
            "(:cidList IS NULL OR e.cid IN :cidList) AND " +
            "e.miday > CURRENT_DATE AND " +
            "CAST(e.mistarttime AS java.util.Date) > CURRENT_TIME")
    List<MovieInfoEntity> findMovieInfosMid(
            @Param("mid") Long mid,
            @Param("cidList") List<Long> cidList
    );
















}










