package com.bo.shirodemo.controller;

import com.bo.shirodemo.dto.OrderStatDTO;
import com.bo.shirodemo.dto.OrdersDto;
import com.bo.shirodemo.service.OrdersService;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import com.bo.shirodemo.vo.OrdersVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * @Description: 订单控制类
 * @Author yb zheng
 * @Date 2025/7/14 14:41
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

//    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequestMapping("/list")
    public Result<?> queryOrdersList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                     OrdersVo ordersVo) {
        Page<OrdersDto> ordersDtos = ordersService.queryOrdersList(page, limit, ordersVo);
        return ReturnResult.success(ordersDtos.getContent(), ordersDtos.getTotalElements());
    }

    @RequestMapping("/daily")
    public List<OrderStatDTO> getDailyStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ordersService.getDailyStats(start, end);
    }

    @RequestMapping("/monthly")
    public List<OrderStatDTO> getMonthlyStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth end) {
        return ordersService.getMonthlyStats(start, end);
    }

}
