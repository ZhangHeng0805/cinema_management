package com.zghh.cinema_management.repository;

import com.zghh.cinema_management.bean.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AdministratorRepository extends JpaRepository<Administrator,Integer> {

    //根据手机号查询
    @Query(value = "select * from t_administrator where phone_num = ?1 " ,nativeQuery = true)
    List<Administrator> findAdministratorByPhoneNum(String phoneNum);
    //根据账号查询
    @Query(value = "select * from t_administrator where account = ?1 " ,nativeQuery = true)
    List<Administrator> findAdministratorByAccount(String account);
}
