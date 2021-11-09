package com.zghh.cinema_management.repository;

import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order,Integer> {
    //根据用户id查询订单
    @Query(value = "select * from t_order where account_id = ?1 " ,nativeQuery = true)
    List<Order> findOrdersByAccountId(Integer account_id);
}
