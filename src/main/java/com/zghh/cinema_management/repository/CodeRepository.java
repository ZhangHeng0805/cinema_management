package com.zghh.cinema_management.repository;

import com.zghh.cinema_management.bean.Order;
import com.zghh.cinema_management.bean.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CodeRepository extends JpaRepository<VerificationCode,String> {
}
