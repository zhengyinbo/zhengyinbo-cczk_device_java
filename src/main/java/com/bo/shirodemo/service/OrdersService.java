package com.bo.shirodemo.service;

import com.bo.shirodemo.dto.OrderStatDTO;
import com.bo.shirodemo.dto.OrdersDto;
import com.bo.shirodemo.entity.Orders;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.vo.OrdersVo;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

/**
 * @Description: 订单接口
 * @Author yb zheng
 * @Date 2025/7/11 15:09
 * @Version 1.0
 */

public interface OrdersService {

    Result<?> createOrders(OrdersVo vo);

    Page<OrdersDto> queryOrdersList(Integer page, Integer limit, OrdersVo vo);

    Orders findByOrdersId(Long ordersId);

    List<OrderStatDTO> getDailyStats(LocalDate start, LocalDate end);

    List<OrderStatDTO> getMonthlyStats(YearMonth startMonth, YearMonth endMonth);

}
