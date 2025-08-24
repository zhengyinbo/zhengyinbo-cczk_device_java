package com.bo.shirodemo.repository;

import com.bo.shirodemo.dto.OrderStatDTO;
import com.bo.shirodemo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {

    // 按天统计
    @Query("SELECT new com.bo.shirodemo.dto.OrderStatDTO(" +
            "FUNCTION('DATE_FORMAT', o.createTime, '%Y-%m-%d'), COUNT(o), SUM(o.amount)) " +
            "FROM Orders o " +
            "WHERE o.createTime BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.createTime, '%Y-%m-%d') " +
            "ORDER BY FUNCTION('DATE_FORMAT', o.createTime, '%Y-%m-%d')")
    List<OrderStatDTO> getDailyStats(@Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

    // 按月统计
    @Query("SELECT new com.bo.shirodemo.dto.OrderStatDTO(" +
            "FUNCTION('DATE_FORMAT', o.createTime, '%Y-%m'), COUNT(o), SUM(o.amount)) " +
            "FROM Orders o " +
            "WHERE o.createTime BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.createTime, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', o.createTime, '%Y-%m')")
    List<OrderStatDTO> getMonthlyStats(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

}
