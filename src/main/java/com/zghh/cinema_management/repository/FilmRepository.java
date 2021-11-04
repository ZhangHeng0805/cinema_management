package com.zghh.cinema_management.repository;

import com.zghh.cinema_management.bean.Administrator;
import com.zghh.cinema_management.bean.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FilmRepository extends JpaRepository<Film,Integer> {

    //根据电影名称查询影片
    @Query(value = "select * from t_film where film_name = ?1 " ,nativeQuery = true)
    List<Film> findFilmByFilmName(String film_name);
}
