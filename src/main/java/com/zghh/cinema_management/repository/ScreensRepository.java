package com.zghh.cinema_management.repository;

import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.bean.Screens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScreensRepository extends JpaRepository<Screens,Integer> {

    //根据电影名称查询影片
    @Query(value = "select * from t_screens where screens_name = ?1 " ,nativeQuery = true)
    List<Screens> findScreensByScreensName(String screens_name);
}
