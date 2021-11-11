package com.zghh.cinema_management.repository;

import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.bean.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LoggerRepository extends JpaRepository<Logger,Integer> {

    //根据账号查询日志操作
    @Query(value = "select * from logger where account = ?1 " ,nativeQuery = true)
    List<Logger> findByAccount(String account);
    //根据账号和操作类型查询日志操作
    @Query(value = "select * from logger where account = ?1 and type =?2" ,nativeQuery = true)
    Optional<Logger> findByAccountAndType(String account, Integer type);
}
