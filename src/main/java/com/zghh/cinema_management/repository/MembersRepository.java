package com.zghh.cinema_management.repository;

import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.bean.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MembersRepository extends JpaRepository<Members,Integer> {
    //根据手机号查询
    @Query(value = "select * from t_members where phone_num = ?1 " ,nativeQuery = true)
    List<Members> findMembersByPhoneNum(String phoneNum);
    //根据账号查询
    @Query(value = "select * from t_members where account = ?1 " ,nativeQuery = true)
    List<Members> findMembersByAccount(String account);

}
